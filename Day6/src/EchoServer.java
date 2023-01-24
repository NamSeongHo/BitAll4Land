import java.io.*; 
import java.net.*; 

public class EchoServer {
	
	private BufferedReader bufferR; 
	private BufferedWriter bufferW; 
	private InputStream is; 
	private OutputStream os; 
	private ServerSocket serverS;
	
	public EchoServer(int port){ 
		try{ 
			// 1. 서버소켓 생성
			serverS = new ServerSocket(port); 
		} catch(IOException ioe){ 
			ioe.printStackTrace(); 
			System.exit(0); 
		} 
		while(true){ 
			try{ 
				System.out.println("클라이언트의 요청을 기다리는 중..."); 
				
				// 2. accept()로 대기
				// 3. 클라이언트의 정보를 가진 소켓 생성
				Socket tcpSocket = serverS.accept(); 
				
				// 위에서 생성한 소켓으로 클라이언트 정보 출력
				System.out.println("클라이언트의 IP 주소 : "+ tcpSocket.getInetAddress().getHostAddress()); 
				
				// 4. 스트림생성
				
				is = tcpSocket.getInputStream(); 	// 읽음
				bufferR = new BufferedReader(new InputStreamReader(is));
				
				os = tcpSocket.getOutputStream(); 	// 클라이언트로 쓰기
				bufferW = new BufferedWriter(new OutputStreamWriter(os)); 
				
				
				// 6. 메시지 읽음
				String message = bufferR.readLine(); 
				System.out.println("수신메시지 : "+ message); 
				
				
				
				// 7. 메시지 전송
				message += System.getProperty("line.separator"); 	// ex) abc\n, 문장의 끝을 알려준다.
				bufferW.write(message); 		// 버퍼에 담아줌
				
				// 클라이언트로 보내고 버퍼 비워줌
				bufferW.flush(); 
				
				// 9. 소켓 종료
				bufferR.close(); 
				bufferW.close(); 
				tcpSocket.close();
				
			}catch(IOException ioe){ 
				ioe.printStackTrace(); 
			} 
		} 
	} 
	public static void main(String[] args){ 
		new EchoServer(3000); 
	} 
} 