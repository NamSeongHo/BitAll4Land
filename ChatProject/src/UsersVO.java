
public class UsersVO {
	
	int userNum;
	String userId;
	String userPw;
	boolean usable;
	
	//getter & setter
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public boolean isUsable() {
		return usable;
	}
	public void setUsable(boolean usable) {
		this.usable = usable;
	}
	
	//출력용
	@Override
	public String toString() {
		return "UsersVO [userNum=" + userNum + ", userId=" + userId + ", userPw=" + userPw + ", usable=" + usable
				+ "]";
	}
}
