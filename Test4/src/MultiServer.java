
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class MultiServer {
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	private HashSet<String> hashname;
	public MultiServer()throws IOException{

		//1. hash 선언
		hashname = new HashSet<String>();
		
		list = new ArrayList<MultiServerThread>();
		ServerSocket serverSocket = new ServerSocket(5000);
		MultiServerThread mst = null;
		boolean isStop = false;
		
		while(!isStop){
			System.out.println("Server ready...");
			socket = serverSocket.accept();
			
			mst = new MultiServerThread(this);
			list.add(mst);
			Thread t = new Thread(mst);
			t.start();
		}
	}
	public ArrayList<MultiServerThread>getList(){
		return list;
	}
	// hash값 반환
		public HashSet<String> getHash() {
			return hashname;
		}
	
	public Socket getSocket()
	{
		return socket;
	}
	public static void main(String arg[])throws IOException{
		new MultiServer();
	}
}
