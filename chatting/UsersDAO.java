package chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	//생성자->반복해서 사용할 부분을 줄이기위해 생성자로 빼놓음
	public UsersDAO() {
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
	
	
	//유저테이블 생성 함수
		public int createUserTable() {
			String SQL = "CREATE TABLE USERS ( userNum SERIAL primary key, "
					+ "userId VARCHAR(10) not null unique, userPw VARCHAR(20) not null, usable boolean )";
			try {
				pstmt = conn.prepareStatement(SQL);
				return pstmt.executeUpdate();		
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
	
	//유저 정보를 list에 담음  --> 관리자 페이지에서 확인 가능하도록 구현
	public List<UsersVO> getUserInfo() {
		List<UsersVO> result = new ArrayList<>();
		String SQL = "SELECT userNum, userId, userPw, usable FROM USERS";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				UsersVO userVO = new UsersVO();
				userVO.userNum = rs.getInt("userNum");
				userVO.userId = rs.getString("userId");
				userVO.userPw = rs.getString("userPw");
				userVO.usable = rs.getBoolean("usable");
				result.add(userVO);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	
	//USERS 테이블의 유저 아이디를 list에 담음
	public ArrayList<String> getUserList() {
		ArrayList<String> result = new ArrayList<>();
		String SQL = "SELECT userId FROM USERS";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(rs.getString(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}		


	//회원가입
	public int join(UsersVO user) {
		String SQL = "INSERT INTO USERS (userId, userPw) VALUES(?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			return pstmt.executeUpdate();			//이후 강퇴 메서드 실행시 false로 변환되도록 한다.
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //가입실패
	}
	
	//유저의 userNum을 가져오는 함수
		public int getUserNum(String userId) {
			String SQL = "SELECT userNum FROM USERS WHERE UserId = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getInt(1);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1;		//해당되는 아이디가 없는 경우
		}


	//회원정보 수정
	public int updateUser(String userId, int userNum) {
		String SQL = "UPDATE USERS SET userId = ? WHERE userNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			pstmt.setInt(2, userNum);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//강퇴시 usable 속성 변경 함수
	public int updateKicked(int userNum) {
		String SQL = "UPDATE USERS SET usable = false WHERE userNum = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, userNum);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//강퇴 여부(usable) 확인 함수 --> 적용이 안됨 내일 질문
	public int checkKicked(String userId) {
		String SQL = "SELECT usable FROM USERS WHERE userId = ? and usable = false";
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()) { 		//강퇴당한 사용자가 있는지 검색하는부분
				if(!rs.getBoolean(1)) {
					result = 1; //강퇴당한 아이디임
				}else {
					result = 0; //입장가능한 아이디임
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = 2;		//데이터베이스 오류
		}
		return result;
	}

	
	//로그인
	public int login(String userId, String userPw) {
		String SQL= "SELECT userPw FROM USERS WHERE userId = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPw)) {
					return 1; //로그인 성공
				}
				else {
					return 0; //비밀번호 불일치
				}
			}
			return -1; //아이디가 없음
		}catch(Exception e) {
			e.printStackTrace();
			return -2; //데이터베이스 오류
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}	


	//아이디 중복 체크 --> true이면 중복된 아이디가 존재 / false이면 사용가능한 아이디
	public boolean ID_Check(String userId) {
		try {
			pstmt = conn.prepareStatement("SELECT * FROM USERS WHERE userId = ?");
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}



}
