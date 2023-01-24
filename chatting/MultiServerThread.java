package chatting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

//public class MultiServerThread extends Thread{
public class MultiServerThread implements Runnable{
	
    public MultiServerThread(Socket socket) { 
		//통신소켓을 닫기 위해서 스레드 생성할 때 생성자 매개변수로 소켓을 받아서 멤버변수에 대입
		this.socket = socket; 
	}
	
    Socket socket;
    private MultiServer ms;
    private MultiServerThread mst;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    private String name = null;

	public String getName() {
		return name;
	}
	
	public MultiServerThread(MultiServer ms){
        this.ms = ms;
    }

	private PrintWriter outMsg = null;
	
	private boolean isStop = false;
	
	
    public synchronized void run(){
    	
        try{
        	
            setSocket(ms.getSocket());
            ois = new ObjectInputStream(getSocket().getInputStream());
            oos = new ObjectOutputStream(getSocket().getOutputStream());
            outMsg = new PrintWriter(socket.getOutputStream(), true);
            String message = null;                
            
            while(!isStop){
                message = (String)ois.readObject();
                
                /*
                if(!message.contains("#")) {		// name -> name에는 #이없잖아요?      // id를 받았어요
					ms.getHash().add(message);
					// 6. HashSet을 Client로 보냄 
					String usernames = getUserNames();
					System.out.println(usernames);
					broadCasting(usernames);
					continue;
				}
				*/
                
                if (!message.contains("#")) {
                	System.out.println(message);
                	continue;
                }

                String[] str = message.split("#"); //구분자로 #사용
                
                if (str.length < 2) {
                	System.out.println(message);
                	continue;
                }
                
                String senderName = str[0];
                String command = str[1];
                
                
                if (name == null) {
                	this.name = senderName.trim();
                }
                
				/**
				 * 사용자가 새로 입장할때 처리하는 로직
				 * 
				 * 
				 * << 전체 로직(서버) >>
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 */
                if (command.equals("enter")) {
                	String welcomeMessage = String.format("%s#SYSTEM: %s 님 환영합니다.", senderName, senderName);
                	broadCasting(welcomeMessage);
                	sendAllUsers();
                }
                //로그아웃
                else if (command.equals("exit")){
                    broadCasting(message);
                    isStop = true;
                
                //비속어 필터링 기능
                } else if(command.contains("심한욕")) {
                	message = name+"#사용자가 비속어를 입력하였습니다.";
                	broadCasting(message);
                } else if(command.equals("notice")) {
                	String noticeMessage = str[2];
                	broadCasting(name + "#notice#" + noticeMessage);
                //강퇴기능
                //(구현시,getId로 받아온 id가 'admin'인 경우에만 강퇴가 가능하도록? ---> 미구현
                } else if(command.equals("kick")) {
                	String target = str[2];
                	broadCastingAfterKick(name +"#" +"[알림]"+ str[2] + "님이 강제퇴장하셨습니다.", target);
                } else if(command.equals("enterRoom")) {
                	broadCasting(name + "#enterRoom#");
                } else{
                	broadCasting(message);
                }   
                
                   
            }
            ms.getList().remove(this);
            System.out.println(getSocket().getInetAddress()+ "정상적으로 종료하셨습니다");
            System.out.println("list size : "+ms.getList().size());
        }catch(Exception e){
            ms.getList().remove(this);
            e.printStackTrace();
            System.out.println(getSocket().getInetAddress()+ "비정상적으로 종료하셨습니다");
            System.out.println("list size : "+ms.getList().size());
        }
    }
    public void broadCasting(String message)throws IOException{
        for(MultiServerThread ct : ms.getList()){
           //Toolkit.getDefaultToolkit().beep(); //채팅이 올라오면 소리 들리도록 
        	ct.send(message);
        }
    }
    
    public void broadCastingAfterKick(String message, String target) throws IOException {
    	for (MultiServerThread ct : ms.getList()) {
    		if (ct.getName().equals(target)) {
    			ct.send(String.format("%s#kick", target)); // target + "#kick"
    			System.out.println(target);
    			continue;
    		}
    		ct.send(message);
    	}
    }
    
 // Hash를 Client로 보내기 위한 broadCasting 메소드 오버로딩
 	public void broadCasting(HashSet<String> a) throws IOException {
 		for(MultiServerThread ct : ms.getList()) {
 			ct.send(a);
 		}
 	}
 	
 // Hash를 Client로 보내기 위한 send 메소드 오버로딩
 	public void send(HashSet<String> a) throws IOException {
 		String str = "";
 		for(String tr : a) {
 			str += tr + "\n";
 		}
 		oos.writeObject(str);
 		System.out.println(str);
 	}
 	
 	/**
 	 * 전체 사용자 클라이언트에게 전달하는 메소드
 	 *  
 	 * @throws IOException
 	 */
 	private void sendAllUsers() throws IOException {
 		// 전체 이름 받아오는 메소드
 		String usernames = getUserNames();
 		for (MultiServerThread ct : ms.getList()) {
 			// 혜원#users#A,B,C,D,혜원 -> 메세지 형태
 
 			ct.send(ct.getName() + "#users#" + usernames);
 		}
 	}
 	
 	/**
 	 * 전체 사용자 구분자로 합쳐 반환하는 메소드
 	 * @return 구분자로 합쳐진 사용자 이름 ex) A,B,C,D,혜원
 	 */
 	private String getUserNames() {
 		String result = "";
 		for (MultiServerThread ct : ms.getList()) {
 			result += ct.getName();
 			result += ",";
 		}
 		return result; // A,B,C,D
 	}
 	
    
    public void send(String message)throws IOException{
    	oos.writeObject(message);        
    }

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}
}
