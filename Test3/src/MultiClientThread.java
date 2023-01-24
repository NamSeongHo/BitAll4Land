
public class MultiClientThread extends Thread{
    private MultiClient mc;
    public MultiClientThread(MultiClient mc){
        this.mc = mc;
    }
    public void run(){
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