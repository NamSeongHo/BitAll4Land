
public class NoticeVO {
	
	int noticeNum;
	String userId;
	String noticecontents;
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNoticecontents() {
		return noticecontents;
	}
	public void setNoticecontents(String noticecontents) {
		this.noticecontents = noticecontents;
	}
	
	//
	@Override
	public String toString() {
		return "NoticeVO [noticeNum=" + noticeNum + ", userId=" + userId + ", noticecontents=" + noticecontents + "]";
	}

}
