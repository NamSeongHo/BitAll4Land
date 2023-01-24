// CHATTING DB CHAT테이블의 데이터 클래스
public class ChatVO {
	//테이블 칼럼명 변수
	int chatNum;
	String userId;
	String contents;
	String chatTime;
	
	//getter setter 함수 등록
	public int getChatNum() {
		return chatNum;
	}
	public void setChatNum(int chatNum) {
		this.chatNum = chatNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
	
	//호출용 
	@Override
	public String toString() {
		return "ChatVO [chatNum=" + chatNum + ", userId=" + userId + ", contents=" + contents + ", chatTime=" + chatTime
				+ "]";
	}
}