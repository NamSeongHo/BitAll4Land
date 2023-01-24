import java.io.*;
import java.net.*;
public class  UnicastServer{
    private ServerSocket serverS;
  
    public UnicastServer(int port){
        try{
            serverS = new ServerSocket(port);
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.exit(0);
        }
        UnicastServerThread ust=null;
        while(true){
            System.out.println("클라이언트 대기중");
    		Socket s=null;
    		try{
    			s = serverS.accept();
    		}catch(IOException ioe){
    			ioe.printStackTrace();
    		}
            System.out.println("client ip : "+s.getInetAddress().getHostAddress());
    		
    		ust = new UnicastServerThread(s);			
    		Thread t = new Thread(ust);
    		t.start();			
    	}
    }
    public static void main(String[] args){
        new UnicastServer(3000);
    }
}