import java.io.IOException;

// 생성자
public class MultiClientThread extends Thread {
	private MultiClient mc;
	
	public static final String COMMAND_KICK = "kick";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_NOTICE = "notice";

	// MultiClient의 메서드 선언
	public MultiClientThread(MultiClient mc) {
		// MultiClient의 메서드는 앞에 mc.으로 선언
		this.mc = mc;
	}
	
	public void run() {
		// message, receivedMsg를 null로 선언
		String message = null;
		String[] receivedMsg = null;
		// isStop 기본을 false로 선언
		boolean isStop = false;
		
		// 프로그램이 멈추지 않는다면
		while(!isStop) {
			// MultClient에서 문자열을 만든다.
			try {
				// MultClient에서 받은 값을(getOis()) message로 선언
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
				
				// MultiClient에서 첫번째 문자열이 exit라면
				if(receivedMsg[1].equals("exit")) {
					// exit명령한 사용자가 종료되야하기 때문에
					// MultiClient에서 받은 0번째 문자열과 MultiCient의 getNickname과 같다면
					if(receivedMsg[0].equals(mc.getNickname())) {
						// 프로그램을 종료한다.
						mc.exit();
					}
					// exit명령하지 않은 사용자는 채팅창에 상대방이 종료했다는 메시지 출력
					else {
						// 채팅창에 exit명령한 사용자의 닉네임 + 님이 종료.. 를 출력
						mc.getJta().append(
								receivedMsg[0] +"님이 종료 하셨습니다."+
										System.getProperty("line.separator"));
						// 
						mc.getJta().setCaretPosition(
								mc.getJta().getDocument().getLength());
					}
				}
				/**
				 * 클라이언트 기능
				 * 채팅창을 지운다 
				 */
				// 채팅창 비우기 키워드
				// MultiClient에서 받은 1번째 문자열이 /clear라면
				else if(receivedMsg[1].equals("/clear")) {
					// 채팅창을 공백으로 만듬
					mc.getJta().setText("  ");
				}
				/**
				 * 공지사항 기능
				 * 클라이언트 -> 서버: nickname#notice#공지사항내용
				 * 서버 -> 클라이언트: nickname#notice#공지사항내용
				 * 공지사항 내용 => receivedMsg[2]
				 */
				// 공지사항 키워드
				// MultiClient에서 받은 1번째 문자열이 notice라면
				else if(receivedMsg[1].equals("notice")) {
					// MultiClient에서 받은 1번째 문자열을 공지사항 부분에 추가한다.
	            	mc.setNotice(receivedMsg[2]);
	            }
				 /**
	             * 강퇴 기능
	             * 클라이언트 -> 서버: nickname#kick#target_nickname
	             * 서버 -> 클라이언트: nickname#kick
	             */
				// 강퇴 키워드
				// MultiClient에서 받은 1번째 문자열이 kick라면
				else if (receivedMsg[1].equals("kick")) {
					// MultiClient의 exit()로 보내서 프로그램을 종료한다.
	            	mc.exit();
	            }
				
				else if (receivedMsg[1].equals("users")) {
	            	String joinedNames = receivedMsg[2];
	            	String[] names = joinedNames.split(",");
	            	
	            	System.out.println("Current " + receivedMsg[0] + " => " + joinedNames);
	            	mc.getJta2().setText("");
	            	for (String name : names) {
	            		// 배열에 들어있는 이름값 하나씩 추가하기
//	            		mc.getJta().append(name + "\n");
	            		mc.getJta2().append(name + "\n");
//	            		mc.addUser(name);
//	            		System.out.println(name);
	            	}
	            }
				
				// 위의 모든 키워드가 아니라면 채팅창에 메시지 전송 키워드 실행
				else {
					// 채팅창에 닉네임 : 메시지를 출력함
					mc.getJta().append(
							receivedMsg[0] +" : "+receivedMsg[1]+
							System.getProperty("line.separator"));
					// 출력이 끝나고 채팅 위치 선정
					mc.getJta().setCaretPosition(
							mc.getJta().getDocument().getLength());
				}
				
				
				// #을 기준으로 문자열을 나눠서 receivedMsg로 선언
				receivedMsg = message.split("#");
			}
			// 예외사항이 발생한다면
			catch(Exception e) {
				// 예외사항을 출력 try catch의 기본문
				e.printStackTrace();
				// 프로그램을 멈춘다.
				isStop = true;
			}

			// #으로 나눈 닉네임과 메시지 이름을 선언
			// String command = receivedMsg[1];
			// String senderName = receivedMsg[0];
			
			
			
		}
	}
}