import java.io.IOException;

public class MultiClientThread extends Thread{
	//-----------------------
	private int count = 0, count1 = 0;
    
	private MultiClient mc;
    public MultiClientThread(MultiClient mc){
        this.mc = mc;
    }
    public void run(){
    	
    	String votename = null;
    	
    	String message = null;
        String[] receivedMsg = null;
        boolean isStop = false;
        while(!isStop){
            try{
                message = (String)mc.getOis().readObject();
                receivedMsg = message.split("#");
            }catch(Exception e){
                e.printStackTrace();
                isStop = true;
            }
            
            System.out.println(receivedMsg.length);
            if(receivedMsg[1].equals("exit")){
                if(receivedMsg[0].equals(mc.getId())){
                    mc.exit();
                }else{
                    mc.getJta().append(
                    receivedMsg[0] +"님이 종료 하셨습니다."+
                    System.getProperty("line.separator"));
                    mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                }
            //_____________________ 추가 부분 :채팅창 비우기 start_________________________//    
            }else if(receivedMsg[1].equals("/clear")) {
            	mc.getJta().setText("  ");
            //_____________________ 추가 부분 :비속어 필터 end_________________________//	
            }
            
            //--------------------------------
            else if (receivedMsg[0].equals("vote")) { // 내용이 /vote 이면
				if(receivedMsg[2].equals("ag")) {
					count1 = Integer.parseInt(receivedMsg[4])+1;
				}else if(receivedMsg[2].equals("op")){
					count1 = Integer.parseInt(receivedMsg[4]);
				}
				count = Integer.parseInt(receivedMsg[3])+1;
				try {
					mc.getOos().writeObject("count#"+count+"#"+count1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mc.append(receivedMsg[1] + System.getProperty("line.separator"));
			} else if (receivedMsg[0].equals("voteresult")) { // 내용이 /voteresult 이면
				count = 0;
				if (receivedMsg[1].equals("voteag")) {
					if (votename.equals(mc.getId())) {
						mc.exit();
					}else {
						mc.append(votename + "님이 강퇴 당하였습니다." + System.getProperty("line.separator"));
						mc.getJtp().setCaretPosition(mc.getJtp().getDocument().getLength());
					}
				} else if (receivedMsg[1].equals("voteop")) {
					mc.append("강퇴가 반대 되었습니다." + System.getProperty("line.separator"));
					mc.getJtp().setCaretPosition(mc.getJtp().getDocument().getLength());
				}
            //--------------------------------------------------
            else{    
                mc.getJta().append(
                receivedMsg[0] +" : "+receivedMsg[1]+
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                
            }
        }
    }
   
}
}