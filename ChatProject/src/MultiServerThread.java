
import java.net.*;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.io.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

public class MultiServerThread implements Runnable {
	
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

	
	public MultiServerThread(MultiServer ms) {
        this.ms = ms;
    }

	private PrintWriter outMsg = null;	
	private boolean isStop = false;
	
    public synchronized void run() {
    	
        try {
        	// 소켓 서버연결
            setSocket(ms.getSocket());
            
            ois = new ObjectInputStream(getSocket().getInputStream());
            oos = new ObjectOutputStream(getSocket().getOutputStream());
            outMsg = new PrintWriter(socket.getOutputStream(), true);
            // message를 null로 선언
            String message = null;                
            
            // 프로그램이 멈추지 않는다면
            while(!isStop) {
            	// MultClient에서 받은 값을(Ois) message로 선언
                message = (String)ois.readObject();
                // #을 기준으로 문자열을 나눠서 String[] str로 선언
                String[] str = message.split("#"); 
                
                // 강퇴기능에 필요
                // 만약 이름이 NULL이라면 
                if(name == null) {
                	// 이름(str[0])에 양쪽의 공백을 제거해 준다(trim())
                	this.name = str[0].trim();
                }
                
                //String senderName = str[0];
                //String command = str[1];
                
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
                if (str[1].equals("enter")) {
                	String welcomeMessage = String.format("%s#SYSTEM: %s 님 환영합니다.", str[0], str[0]);
                	broadCasting(welcomeMessage);
                	sendAllUsers();
                }
                
                // 로그아웃
                // 문자열의 첫번째(메시지)가 exit라면
                if(str[1].equals("exit")) {
                	// message를 broadCasting해준다
                    broadCasting(message);
                    // 프로그램 종료
                    isStop = true;    
                }
                // 비속어 필터링 기능
                // 문자열의 첫번째(메시지)가 "심한욕"이라면
                else if(str[1].contains("심한욕")) {
                	// message에 닉네임 + 사용자가... 삽입
                	message = str[0]+"#사용자가 비속어를 입력하였습니다.";
                	// message를 brodCasting해준다.
                	broadCasting(message);
                } 
                // 공지사항 기능
                // 문자열의 첫번째(메시지)가 notice라면
                else if(str[1].equals("notice")) {
                	// 닉네임 + notice + 메시지를 broadCasting해준다
                	broadCasting(str[0] + "#notice#" + str[2]);
                } 
                // 강퇴 기능
                // 문자열의 첫번째(메시지)가 out라면
                else if(str[1].equals("kick")) {
                	// a # kick # b a가 b를 강퇴 즉 str문자열의 2번이 강퇴당하는 닉네임
                	// 강퇴당하는 닉네임을 target에 저장
                	String target = str[2];
                	// 닉네임 # [알림] # 강퇴당하는 닉네임 # 님이.... 와 target을 broadCastingAfterKick에 넣어준다   
                	broadCastingAfterKick(name +"#" +"[알림]"+ str[2] + "님이 강제퇴장하셨습니다.", target);
                }
				/*
				 * else if(str[1].equals("enterRoom")) { broadCasting(name + "#enterRoom#"); }
				 */
                // 위의 모든 키워드가 아니라면 메시지를 broadCasting해준다.
                else {
                	broadCasting(message);
                } 
            }
            // List에 남아있는 서버 스레드를 지워준다 / 통신이 끝나서 불필요
            ms.getList().remove(this);
        }
     // 예외사항이 발생한다면
        catch(Exception e) {
        	// List에 남아있는 서버 스레드를 지워준다
            ms.getList().remove(this);
        }
    }
    
    // 전체 소켓에 강퇴메시지를 출력한다 / throws IOException는 만약 예외사항이 있다면 오류 출력
    public void broadCastingAfterKick(String message, String target) throws IOException {
    	// 콜론문 ms.getList()에 들어있는 크기 만큼 반복
    	for (MultiServerThread ct : ms.getList()) {
    		// 
    		if (ct.getName().equals(target)) {
    			// 
    			ct.send(String.format("%s#kick", target));
    			continue;
    		}
    		// ms.getList()에 들어있는 개수 만큼 message를 send
    		ct.send(message);
    	}
    }
    
    // 전체 소켓에 메시지를 출력한다 / throws IOException는 만약 예외사항이 있다면 오류 출력
    public void broadCasting(String message)throws IOException {
    	// 콜론문 ms.getList()에 들어있는 개수 만큼 MultiServerThread ct에 넣어준다.
        for(MultiServerThread ct : ms.getList()) {
        	// ms.getList()에 들어있는 개수 만큼 message를 send
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
  	
    public void send(String message)throws IOException {
    	oos.writeObject(message);        
    }

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	// 소켓을 받아오는 메서드
	public Socket getSocket() {
		return socket;
	}
}
