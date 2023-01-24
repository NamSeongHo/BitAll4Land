import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
public class MultiServerThread implements Runnable{
	private Socket socket;
	private MultiServer ms;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public MultiServerThread(MultiServer ms){
		this.ms = ms;
	}
	public synchronized void run(){ 
		boolean isStop = false;
		try{
			socket = ms.getSocket();
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			String message = null;
			while(!isStop){
				
				// 4. Client에서 보낸 name을 여기서 message에 받음
				message = (String)ois.readObject(); 
				if(!message.contains("#")) {		// name -> name에는 #이없잖아요?      // id를 받았어요
					//Hash에 이름이 있다면
					if(ms.getHash().contains(message)) {
						//이름을 지운다
						ms.getHash().remove(message);
					} else {
						//5. name으로 분류해서 Hash에 추가
						ms.getHash().add(message);				// => 그래서 #이 없는 것 -> name으로 분류해서 Hash에 추가
					}
					
					// 6. HashSet을 Client로 보냄 
					broadCasting(ms.getHash());
				} else {
					String[] str = message.split("#");	// 근데 여기서는 #을 분기로 나누라네요
					if(str[1].equals("exit")){
						broadCasting(message);
						isStop = true;
					}

					//비속어 필터---------------------------------------------------------------
					else if(str[1].contains("비속어1")) {
						message = str[0]+"#사용자가 비속어를 입력하였습니다. \n";
						broadCasting(message);
						//10초간 채팅금지
						//Thread.sleep(10000);
					}
					//-----------------------------------------------------------------------
					else if(str[1].contains("비속어2")) {
						message = str[0]+"#사용자가 비속어를 입력하였습니다. \n";
						broadCasting(message);
						//10초간 채팅금지
						//Thread.sleep(10000);
					}

					else{
						broadCasting(message);
					}
				}
				
			}
			ms.getList().remove(this);
			
			System.out.println(socket.getInetAddress()+ "정상적으로 종료하셨습니다");
			System.out.println("list size : "+ms.getList().size());
		}catch(Exception e){
			ms.getList().remove(this);
			System.out.println(socket.getInetAddress()+ "비정상적으로 종료하셨습니다");
			System.out.println("list size : "+ms.getList().size());
		}
	}
	public void broadCasting(String message)throws IOException{
		for(MultiServerThread ct : ms.getList()){
			Toolkit.getDefaultToolkit().beep();//채팅이 올라오면 소리가 난다!?
			ct.send(message);
		}
	}
	
	// Hash를 Client로 보내기 위한 broadCasting 메소드 오버로딩
	public void broadCasting(HashSet<String> a) throws IOException {
		for(MultiServerThread ct : ms.getList()) {
			ct.send(a);
		}
	}
	
	public void send(String message)throws IOException{
		oos.writeObject(message);        
	}
	
	// Hash를 Client로 보내기 위한 send 메소드 오버로딩
	public void send(HashSet<String> a) throws IOException {
		String str = "";
		for(String tr : a) {
			str += tr + "\n";
		}
		oos.writeObject(str);
	}
}
