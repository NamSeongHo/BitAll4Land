package chatting;

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
	private HashSet<String> hashname; 				//접속 유저 정보를 담을 리스트 객체 생성
	//유저 이름을 KEY로, 스레드를 VALUE로 가지고있는 해시맵 생성

	//생성자-------------------------------- 
	public MultiServer()throws IOException{
		//스레드 리스트
		list = new ArrayList<MultiServerThread>();	
		//5000번 포트 서버 소켓 생성

		//1. hash 선언
		hashname = new HashSet<String>();

		serverSocket = new ServerSocket(5000);
		boolean isStop = false;
		while(!isStop){
			System.out.println("Server ready...");
			socket = serverSocket.accept();

			MultiServerThread mst  =  new MultiServerThread(socket);
			mst = new MultiServerThread(this);
			list.add(mst);	

			Thread t = new Thread(mst); 
			t.start();
		}
	} 

	//메서드-------------------------------- 
	public ArrayList<MultiServerThread>getList(){
		return list;
	}

	public Socket getSocket()
	{
		return socket;
	}

	// 접속 유저정보를 담은 hash값 반환
	public HashSet<String> getHash() {
		return hashname;
	}

	//메인-------------------------------- 
	public static void main(String arg[])throws IOException{
		new MultiServer();
	}

}
