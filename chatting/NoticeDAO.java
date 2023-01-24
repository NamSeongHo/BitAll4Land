package chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NoticeDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//생성자->반복해서 사용할 부분을 줄이기위해 생성자로 빼놓음
	public NoticeDAO() {
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

	//공지 테이블 생성 함수
	public int createNoticeTable() {
		String SQL = "CREATE TABLE NOTICE ( noticeNum SERIAL primary key, "
				+ "userId VARCHAR(10) REFERENCES USERS(userId), noticecontents TEXT )";
		try {
			pstmt = conn.prepareStatement(SQL);
			return pstmt.executeUpdate();		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	

	//공지 테이블에 추가 메서드
	public int insertNotice(NoticeVO notice) {
		String SQL = "INSERT INTO NOTICE (userId, noticecontents) VALUES(?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, notice.getUserId());					//채팅 입력한 유저 아이디 받아옴
			pstmt.setString(2, notice.getNoticecontents());			//채팅 내용을 받아옴
			return pstmt.executeUpdate();		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;													//데이터 추가 실패시 -1반환 
	}


}
