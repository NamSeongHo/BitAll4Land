// 예외사항 import
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

//생성자
public class MultiServer {
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	private ServerSocket serverSocket;
	//접속 유저 정보를 담을 리스트 객체 생성
	private HashSet<String> hashname; 				
	//유저 이름을 KEY로, 스레드를 VALUE로 가지고있는 해시맵 생성
	
	public MultiServer()throws IOException {
		// 스레드 리스트
		list = new ArrayList<MultiServerThread>();	
		// 5000번 포트 서버 소켓 생성
		
		//1. hash 선언
		hashname = new HashSet<String>();
		
		serverSocket = new ServerSocket(5000);
		// isStop 기본을 false로 선언
		boolean isStop = false;
		
		// 프로그램이 멈추지 않는다면
		while(!isStop) {
			// 콘솔창에 Server ready... 출력
			System.out.println("Server ready...");
			// 소켓 서버를 socket에 선언
			socket = serverSocket.accept();
			// 소켓을 mst로 선언
			MultiServerThread mst  =  new MultiServerThread(socket);
			// mst를 MultiServerThread에 사용하기 위해 선언
			mst = new MultiServerThread(this);
			// mst를 리스트에 추가
			list.add(mst);	
			// mst스래드를 t로 선언
			Thread t = new Thread(mst); 
			// 소켓 시작
			t.start();
		}
	} 

	// 메서드	
	// 소켓 연결 끊는 메서드
//	void disconnect(MultiServerThread thread) {
//		thread.kick();
//	}
	// 
	public ArrayList<MultiServerThread>getList() {
		return list;
	}
	// 소켓을 받아오는 메서드
	public Socket getSocket()
	{
		return socket;
	}

	// 접속 유저정보를 담은 hash값 반환
	public HashSet<String> getHash() {
		return hashname;
	}
	
	// 메인
	public static void main(String arg[])throws IOException{
		new MultiServer();
	}
}
