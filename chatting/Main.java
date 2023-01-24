package chatting;

public class Main {

	public static void main(String[] args) {


		//유저테이블 생성 실행 
		UsersDAO userDAO = new UsersDAO();
		userDAO.createUserTable();
		System.out.println("USERS 테이블 생성됨");
		
		//채팅 테이블 생성 실행
		ChatDAO charDAO = new ChatDAO();
		charDAO.createChatTable();
		System.out.println("CHAT 테이블 생성됨");
		
		//공지 테이블 생성 실행
		NoticeDAO noticeDAO = new NoticeDAO();
		noticeDAO.createNoticeTable();
		System.out.println("NOTICE 테이블 생성됨");
	}

}
