
import java.net.*;
import java.awt.Toolkit;
import java.io.*;
public class MultiServerThread implements Runnable{
    private Socket socket;
    private MultiServer ms;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    //-----------------
    private int count = 0, count1 = 0;
    
    //(추가)
    public MultiServerThread(MultiServer ms){
        this.ms = ms;
    }
    public synchronized void run(){
        boolean isStop = false;
        try{
            socket = ms.getSocket();
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            String message = null;
            while(!isStop){
                message = (String)ois.readObject();
                String[] str = message.split("#");
                
                if(str[1].equals("exit")){
                    broadCasting(message);
                    isStop = true;
                //_____________________ 추가 부분 :비속어 필터 start_________________________//
                }else if(str[1].contains("심한욕")) {
                	message = str[0]+"#사용자가 비속어를 입력하였습니다.";
                	broadCasting(message);
                	
                }
                //_____________________ 추가 부분 :비속어 필터 end_________________________//
                
                //-------------
                else if(str[1].equals("voteag")) {
					broadCasting("vote"+"#"+(count+1)+"/"+ms.getList().size()+"투표완료"+"#"+"ag" +"#" + count + "#" + count1);
				}else if(str[1].equals("voteop")) {
					broadCasting("vote"+"#"+(count+1)+"/"+ms.getList().size()+"투표완료"+"#"+"op"+"#" + count + "#" + count1);
				}else if(str[0].equals("count")){
					count = Integer.parseInt(str[1]);
					count1 = Integer.parseInt(str[2]);
				}
                //-----------------------------
                
                else{
                    broadCasting(message);
                }
            }
            ms.getList().remove(this);
            System.out.println(socket.getInetAddress()+ "정상적으로 종료하셨습니다");
            System.out.println("list size : "+ms.getList().size());
        }catch(Exception e){
            ms.getList().remove(this);
            System.out.println(socket.getInetAddress()+ "비정상적으로 종료하셨습니다");
            System.out.println("list size : "+ms.getList().size());
        }
    }
    public void broadCasting(String message)throws IOException{
        for(MultiServerThread ct : ms.getList()){
           Toolkit.getDefaultToolkit().beep();//채팅이 올라오면 소리가 난다!?
        	ct.send(message);
        }
    }
    public void send(String message)throws IOException{
    	oos.writeObject(message);        
    }

    
}
