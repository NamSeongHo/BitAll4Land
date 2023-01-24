import java.io.*; 
import java.net.*; 

public class EchoClient {
	
	private String ip; 
	private int port; 
	private String str; 
	BufferedReader file; 
	
	public EchoClient(String ip, int port) throws IOException { 
		this.ip = ip; 
		this.port = port; 
		
		
		// 3. 소켓 생성
		Socket tcpSocket = getSocket(); 
		
		// 4. 스트림 생성
		
		// 서버로 보내주는 스트림 생성
		OutputStream os_socket = tcpSocket.getOutputStream(); 
		BufferedWriter bufferW = new BufferedWriter( new OutputStreamWriter(os_socket));
		
		// 서버로 읽는 스트림 생성
		InputStream is_socket = tcpSocket.getInputStream(); 
		BufferedReader bufferR = new BufferedReader( new InputStreamReader(is_socket));
		
		// 5. 메시지 전송
		System.out.print("메시지 : ");
		file = new BufferedReader( new InputStreamReader(System.in)); 
		str = file.readLine(); 
		str += System.getProperty("line.separator"); 	// \n기능, 문장의 끝을 알려준다.
		bufferW.write(str); 		// 전송 => 버퍼에 담아줌
		
		bufferW.flush(); 			// 보내고 비워줌
		
		// 8. 메시지 읽음
		str = bufferR.readLine(); 
		System.out.println("Echo Result : " + str); 

		// 9. 소켓 종료
		file.close(); 
		bufferW.close(); 

		bufferR.close(); 
		tcpSocket.close(); 
	} 
	
	
	public Socket getSocket() { 
		Socket tcpSocket = null; 
		try { 
			tcpSocket = new Socket(ip, port); // 소켓 생성
		} catch (IOException ioe) { 
			ioe.printStackTrace(); 
			System.exit(0); 
		} 
		return tcpSocket; 
	} 
	public static void main(String[] args) throws IOException { 
		new EchoClient("localhost", 3000); 
	} 
}