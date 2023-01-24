package chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//생성자->반복해서 사용할 부분을 줄이기위해 생성자로 빼놓음
	public ChatDAO() {
		try {
			String connurl = "jdbc:postgresql://localhost:5432/CHATTING";
			String user = "postgres";
			String password = "1234";
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(connurl, user, password);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("연결실패");
		}
	}


	//채팅 테이블 생성 함수
	public int createChatTable() {
		String SQL = "CREATE TABLE CHAT ( chatNum SERIAL primary key, "
				+ "userId VARCHAR(10), contents TEXT, "
				+ "chatTime TIMESTAMP default now() )";
		try {
			pstmt = conn.prepareStatement(SQL);
			return pstmt.executeUpdate();		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}


	//채팅을 입력할 때 테이블에 추가
	public int insertChat(ChatVO chat) {
		String SQL = "INSERT INTO CHAT (userId, contents) VALUES(?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, chat.getUserId());			//채팅 입력한 유저 아이디 받아옴
			pstmt.setString(2, chat.getContents());			//채팅 내용을 받아옴
			return pstmt.executeUpdate();		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;											//데이터 추가 실패시 -1반환 
	}


}
