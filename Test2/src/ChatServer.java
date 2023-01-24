
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

// ServerSocket과 Socket클래스를 가지고 TCP베이스의 소켓프로그램을 구현한 예제
public class ChatServer{

	//각 클라이언트에 대한 정보를 Vector로 관리
	Vector V;
	ServerSocket server;
	
	public static void main (String args[]){
		/* new ChatServer(Integer.parseInt(args[0])); */
		new ChatServer(5000);
	}

	public ChatServer(int port){
		V = new Vector();
		try{
			server = new ServerSocket(port);
			System.out.println("Start Server \n");

			System.out.println("Wait Client...");

		}catch(Exception e){
			System.out.println(e);
		}

		try{
			while(true){
				Socket socket = server.accept();
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// 연결해온 클라이언트에 환영 메시지 출력
				printNames(out);
				//클라이언트마다 ChatConnection 객체 생성
				ChatConnection c = new ChatConnection(in,out,this);
				add(c);
				c.start();
			}
		}catch(Exception e){
			System.err.println(e);
			System.err.println("Usage : java ChatServer port");
		}
	}
	
	/**클라이언트에 대한 ChatConnection을 Vector에 추가하는 함수 */
	public void add (ChatConnection c){
		V.addElement(c);
	}
	
	/**모든 클라이언트에 메시지 출력하는 함수 */
	synchronized public void println (String s){
		for(int i=0;i<V.size();i++){
			ChatConnection c = (ChatConnection)V.elementAt(i);
			c.println(s);
		}
	}
	
	// 동일 이름에 대한 점검
	synchronized public boolean hasName (String name, ChatConnection cname){
		for(int i = 0; i<V.size(); i++){
			ChatConnection c = (ChatConnection)V.elementAt(i);
			if(c!=cname&&c.name().equals(name))
				return true;
		}
		return false;
	}
	
	// 모든 클라이언트에 환영 메시지 출력
	synchronized public void printNames (PrintWriter out){
		if(V.size() == 0){
			out.println("....Nobody connected");
		}
		else{
			Date now = new Date();
			out.println("Connected:");
			for(int i=0;i<V.size();i++){
				ChatConnection c = (ChatConnection)V.elementAt(i);
				out.println(now+c.name() +" Connected");
			}
		}
		out.println("Welcome!\n");
		out.flush();
	}

	/** 클라이언트에 대한 정보 삭제하는 메소드 */
	synchronized public void removeConnection(ChatConnection c){
		V.removeElement(c);
	}
}