package chatting;

import java.io.IOException;

public class MultiClientThread extends Thread{
	public static final String COMMAND_KICK = "kick";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_NOTICE = "notice";
	
	
	
	private MultiClient mc;
	public MultiClientThread(MultiClient mc){
		this.mc = mc;
	}

	public void run() {
		String message = null;
		String[] receivedMsg = null;
		boolean isStop = false;
		while(!isStop){
			try{
				message = (String)mc.getOis().readObject();
				
				/**
				 * 사용자 이름이 넘어올 떄 처리하는 로직
				 * 메세지 형태: A,B,C,D
				 * 
				 * 
				 * << 전체 로직(클라이언트) >>
				 * 1. 클라이언트가 입장 (enter) 했을 때
				 * 2. 서버가 welcome 보내고 사용자 리스트 보낸다.
				 * ============= 아래 if 부터 시작 ===========
				 * 3. 약속된 구분자(',')를 이용하여 풀어준다.
				 * 4. 사용자 리스트를 초기화하고 (jta2)
				 * 5. 반복문을 돌려서 리스트에 추가한다. (jta2.append())
				 * 6. 끝
				 * 
				 */
				if(!message.contains("#")) {
					// 구분자로 묶여있는 메세지 풀어주기
					String[] names = message.split(","); // A B C D
					
					// 리스트에 들어있는 사용자 목록 전체 초기화
	            	//mc.getJta2().setText("");  //**********************
	            	for (String name : names) {
	            		 // 배열에 들어있는 이름값 하나씩 추가하기
	            		mc.getJta2().append(name + "\n");
	            	}
	            	
	            	/**
	            	 * 만약 '#'으로 구분하면 2 ~ 끝까지 이름 판단
	            	 * (혜원#users#A#B#C#D#혜원)
	            	 * String[] names = message.split("#");
	     			 * int startIndex = 2;
	     			 *
	     			 * for (int i = startIndex; i < names.length; i++) {
	     			 *	String name = names[i];
	     			 *	mc.getJta2().append(name + "\n");
	     			 * }
	            	 *  
	            	 */
	            	
	            	// 아래 코드는 처리하지 않음
	            	continue;
	            }

				receivedMsg = message.split("#");
				
				String command = receivedMsg[1];
    			String senderName = receivedMsg[0];

    			if(command.equals("exit")){
    				if(senderName.equals(mc.getNickname())){
    					mc.exit();
    				}
    				
    				else{
    					mc.getJta().append(
    							senderName +"님이 종료 하셨습니다."+
    									System.getProperty("line.separator"));
    					mc.getJta().setCaretPosition(
    							mc.getJta().getDocument().getLength());
    				}
    			/**
    			 * 클라이언트 기능
    			 * 채팅창을 지운다 
    			 */
    			}else if(command.equals("/clear")) {
    				mc.getJta().setText("  ");
    				System.out.println("use clear");
    			/**
    			 * 공지사항 기능
    			 * 클라이언트 -> 서버: nickname#notice#공지사항내용
    			 * 서버 -> 클라이언트: nickname#notice#공지사항내용
    			 * 공지사항 내용 => receivedMsg[2]
    			 */
    			}else if(command.equals("notice")) {
                	mc.setNotice(receivedMsg[2]);
                	System.out.println(receivedMsg[2]);
                	System.out.println("register notice");
                /**
                 * 강퇴 기능
                 * 클라이언트 -> 서버: nickname#kick#target_nickname
                 * 서버 -> 클라이언트: nickname#kick
                 */
                } else if (command.equals("kick")) {
                	System.out.println(mc.getName() + "Kicked");
                	mc.exit();
                }
    			
    			/**
    			 * 
    			 */
                else if (command.equals("users")) {
                	String joinedNames = receivedMsg[2];
                	String[] names = joinedNames.split(",");
                	
                	System.out.println("Current " + senderName + " => " + joinedNames);
                	mc.getJta2().setText("");
                	for (String name : names) {
	            		// 배열에 들어있는 이름값 하나씩 추가하기
//	            		mc.getJta().append(name + "\n");
	            		mc.getJta2().append(name + "\n");
//                		mc.addUser(name);
//                		System.out.println(name);
	            	}
                }
    			
    			//입장
    			
    			else{    
    				mc.getJta().append(
    						senderName+" : "+command+
    						System.getProperty("line.separator"));
    				mc.getJta().setCaretPosition(
    						mc.getJta().getDocument().getLength());

    			}	
				
			}catch(Exception e){
				e.printStackTrace();
				isStop = true;
			}
					
		}
	}
}