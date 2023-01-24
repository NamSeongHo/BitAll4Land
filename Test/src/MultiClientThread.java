//import java.time.LocalDate;

public class MultiClientThread extends Thread {
	private MultiClient mc;

	public MultiClientThread(MultiClient mc) {
		this.mc = mc;
	}

	public void run(){
		//LocalDate now = LocalDate.now();
        String message = null;
        String[] receivedMsg = null;
        boolean isStop = false;
        while(!isStop){
     
            try{
            	
            	// 7. 여기서 Server에서 보낸 Hash String 받음
                message = (String)mc.getOis().readObject();
                if(!message.contains("#")) {
                	mc.getJta2().setText("");
                	mc.getJta2().append(message);
                } else {
                	receivedMsg = message.split("#");
                	
                }               
            }catch(Exception e){
                e.printStackTrace();
                isStop = true;
            }
            
            System.out.println(receivedMsg[0]+","+receivedMsg[1]);
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

            }
            //입장-----------------------------------------
            else if(receivedMsg[1].equals("enter")) {
            	//mc.getJta().setText("");
            	mc.getJta().append(
            		
               receivedMsg[0] +"님이 입장 하셨습니다."+ 
               System.getProperty("line.separator"));
            
                     mc.getJta().setCaretPosition(
                     mc.getJta().getDocument().getLength());

                     //mc.getJta().setText("");
            }
            
            
            //----------------------------------------------------
            
            //모두 삭제하는 기능--------------------------------------
            else if(receivedMsg[1].equals("/clear")) {
            	//모두 공백으로 바꿔준다.
            	mc.getJta().setText(" ");
            }
            //--------------------------------------------------- 

      
            else{               
            	
                mc.getJta().append(
                receivedMsg[0] +" : "+receivedMsg[1]+
                //----
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                
                
            }

        }

    }
}