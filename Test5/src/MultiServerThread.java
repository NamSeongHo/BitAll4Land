package Test2;

import java.net.*;
import java.awt.Toolkit;
import java.io.*;
public class MultiServerThread implements Runnable{
    private Socket socket;
    private MultiServer ms;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
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
                //_____________________ 추가 부분 :비속어 필터
                }else if(str[1].contains("심한욕")) {
                	message = str[0]+"#사용자가 비속어를 입력하였습니다.";
                	broadCasting(message);
                	
                } else if(str[0].equals("notice")) {
                	broadCasting(str[0] + "#notice#" + str[1]);
                }
                //_____________________ 추가 부분 :비속어 필터
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
           Toolkit.getDefaultToolkit().beep();//채팅이 올라오면 소리
        	ct.send(message);
        }
    }
    public void send(String message)throws IOException{
    	oos.writeObject(message);        
    }

    
}
