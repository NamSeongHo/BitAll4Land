
// 여기가 Thread에 해당 > ClientThread인듯? 아닐수도
// ServerSocket과 Socket클래스를 가지고 TCP베이스의 소켓프로그램을 구현
import java.io.*;
import java.net.*;
import java.util.Vector;

/** 클라이언트로부터의 입력값을 가지고 서버에 접속을 시도하는 클래스 */
class ChatConnection extends Thread{
	BufferedReader In;            // 클라이언트로부터의 입력받는 객체
	PrintWriter Out;              // 클라이언트로의 출력 객체    
	ChatServer C;           
	String Name = "???";                // 연결해온 클라이언트의 이름 "=== name"
	Login l = new Login();

	public ChatConnection(BufferedReader in, PrintWriter out, ChatServer c){
		C=c;
		In=in;
		Out=out;
	}

	public void run(){
		try{
			while(true){
				String s = In.readLine();
				System.out.println(s);
				if(s==null) break;
				if(s.startsWith("===")){
					Name = s.substring(3);             // 클라이언트의 이름을 얻어낸다.
					if(C.hasName(Name,this)){
						// 동일한 이름이 이미 존재하므로 다른 이름을 선댁
						Out.println("동일한 대화명이 존재합니다! 다시 로그인해 주세요!!");
						Out.flush();
						In.close();
						C.removeConnection(this);
						l.setSize(300,200);
						l.setVisible(true);
						return;
					}
					//새로운 대화명에 대해 서버에 알림

					C.println("=== " +Name+" Connected");
				}
				else{             // 일반적인 input
					if(Name.equals("???")) 
						break;
					C.println(s);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		C.println("=== " +Name+" Disconnected");
		C.removeConnection(this);
		try{
			In.close();
			Out.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**ChatServer가 클라이언트에 메시지 출력시 호출*/
	public void println(String s){
		Out.println(s);
		Out.flush();
	}
	/** 입력된 대화명을 리턴하는 함수 */
	public String name(){
		return Name;
	}
}