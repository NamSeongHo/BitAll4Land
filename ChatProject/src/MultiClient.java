// 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//생성자
public class MultiClient extends JFrame implements ActionListener,KeyListener,ItemListener,MouseListener {
	
	// GUI 구성 시작 -------------------------------------------------------------- 
	MultiServerThread mst;
	MultiServer ms;
	UsersDAO dao;
	
	private Socket socket;
	//다른 소켓으로부터 내용을 입력받는 통로역할
	private ObjectInputStream ois;
	//다른 소켓으로부터 내용을 보내는 통로역할
	private ObjectOutputStream oos;
	// 화면 전체 부분
	private JFrame jframe;
	// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)   
	private JTextField jtf;	         
	// 대화내용이 입력되는 텍스트에어리어 객체 선언(서버로부터 전달된 메시지 출력 컴포넌트)
	private JTextArea jta, jta2;	
	private String ip;
	// 로그아웃 버튼
	private JButton jbtn;
	// 공지사항이 등록되는 레이블
	private JLabel label_test;
	JLabel label1, label3;
	// 대화명 변경을 입력하기 위한 텍스트 필드 객체 선언
	JTextField t1;			
 
	// 채팅 보내기 버튼
	JButton b2;				
	// 유저명 변경 버튼
	JButton b1;				
	// 강퇴버튼
	JButton outButton; 		

	// 미구현 선언
	/*
	 * PopupMenu popup; MenuItem menu1; MenuItem menu2;
	 */
	
	// 리스트 아이템 클릭시 이벤트에 사용하기 위해 임시로 생성
	int state;
	// 서버로의 출력 객체
	PrintWriter out;
	// 서버로부터의 입력 객체
	BufferedReader in;                  
	Thread t;
	//List list;
	//JTextArea listOut;
	
	// 변경 전 대화명을 저장
	String agonick;		
	String sendmesg;
	// 대화중 각종 변경사항을 담고 있는 스트링 객체 선언	
	String notice1;				
	String notice2;
	// 리스트 박스에서 선택된 아이템의 내용을 저장하는 객체
	//String listSelectName; 
	String nickname;	
	// GUI 구성 끝 -------------------------------------------------------------

	public MultiClient(String id) {	
		// ip 선언
		ip = "127.0.0.1";	

		setTitle("채팅창");
		// id를 nickname으로 설정 
		nickname = id;

		// 컴포넌트 
		// 4개의 패널 객체 생성
		Panel p1 = new Panel();       
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();

		// 각 패널의 레이아웃을 설정
		// 프레임 레이아웃을 FlowLayout으로 설정
		p1.setLayout(new FlowLayout());
		// 프레임 레이아웃을 BorderLayout으로 설정
		p2.setLayout(new BorderLayout()); 
		// 프레임 레이아웃을 BorderLayout으로 설정
		p3.setLayout(new BorderLayout()); 
		// 프레임 레이아웃을 FlowLayout으로 설정
		p4.setLayout(new FlowLayout());

		//변경할 아이디를 입력받는 텍스트 필드
		t1 = new JTextField(15);		
		// 대화한 내용이 뜨는 영역
		jta = new JTextArea(" ",5,20);  
		
		jta2 = new JTextArea(5,10); //채팅참가자를 나타낼 영역
		jta2.setEditable(false);
		
		// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)	
		// 예전 로그인 없었을때 사용
		// 버튼이름
		jtf = new JTextField(60);		
		label1 = new JLabel("USER NAME : ");
		label3 = new JLabel("CHAT");

		b1 = new JButton("CHANGE");
		b2 = new JButton("SEND");
		outButton = new JButton("OUT");
		jbtn = new JButton("LOGOUT");
		JButton b4 = new JButton("NOTIFY");

		label_test = new JLabel("공지사항 올라오는곳");
		
		// 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b1.addActionListener(this);                    
		b2.addActionListener(this);                   
		jbtn.addActionListener(this);
		jtf.addKeyListener(this);

		// 패널에 각 컴포넌트를 붙인다.
		p1.add(label1);                              
		p1.add(t1);
		p1.add(b1);
		JScrollPane scrollPane = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p2.add(scrollPane);
		p1.add(b4);
		p1.add(outButton);
		p1.add(label_test);
		p3.add(jta2, BorderLayout.CENTER);
		//p2.add(jta);
		// 채팅창(p2)에 가로와 세로 스크롤바 추가.
		/*
		 * p2.add(new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		 * JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		 */
		p3.add(jta2);
		p4.add(label3);
		p4.add(jtf);
		p4.add(b2);
		p4.add(jbtn);
		
		// 패널을 프레임의 각 위치에 배치
		getContentPane().add(BorderLayout.NORTH,p1);                  
		getContentPane().add(BorderLayout.CENTER,p2);
		getContentPane().add(BorderLayout.EAST,p3);
		getContentPane().add(BorderLayout.SOUTH,p4);

		// 버튼 및 패널 배경 색깔 지정
		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);
		b1.setBackground(pointColor);              	
		b2.setBackground(pointColor);
		jbtn.setBackground(pointColor);				
		b4.setBackground(pointColor);
		outButton.setBackground(pointColor);
		p1.setBackground(mainColor);
		p3.setBackground(Color.green);
		jta2.setBackground(pointColor);
		p4.setBackground(mainColor);

		/*
		 * //미구현된 부분 popup = new PopupMenu(); // 접속자목록에서 오른쪽 마우스 클릭시 귓속말,강퇴 기능의 팝업을 띄우기
		 * 위한 객체 생성 menu1 = new MenuItem("귓속말"); menu2 = new MenuItem("강 퇴");
		 * popup.add(menu1); // 팝업에 메뉴 컴포넌트를 추가 popup.add(menu2); jta2.add(popup); //
		 * 리스트 컴포넌트에 팝업 컴포넌트를 추가.
		 */
		
		//리스트에 팝업 메뉴를 위해 마우스 이벤트 등록
		//jta2.addMouseListener(this);
		//list.addItemListener(this);
		//popup.addActionListener(this);
		jtf.addActionListener(this);
		jbtn.addActionListener(this);
		
		jta.setEditable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		pack();
		setLocation(
				(screenWidth - getWidth()) / 2,
				(screenHeight - getHeight()) / 2);
		setResizable(false);
		setVisible(true);

		// 공지 다이어로그 ----------------------------------------------------------------------
		b4.addActionListener(new ActionListener() {

			//마우스로 동작하는 부분의 이벤트처리
			@Override
			public void actionPerformed(ActionEvent e) {
				// 공지를 입력하는 창을 띄움
				String inputValue = JOptionPane.showInputDialog("공지를 입력하세요");
				
				try {
					// MultiServerThread으로 보낸다.
					oos.writeObject(nickname + "#notice#" + inputValue);
				
					//공지 입력시 데이터 데이스에 반영 부분
					NoticeDAO dao = new NoticeDAO();
					NoticeVO vo = new NoticeVO();
					String userId = getNickname();
					String noticeContent = inputValue;
					
					vo.setUserId(userId);
					vo.setNoticecontents(noticeContent);
					dao.insertNotice(vo);
					
					/*
					 * //****************getter와 setter을 사용해서 공지를 보내주고 받음 NoticeVO vo = new
					 * NoticeVO(); NoticeDAO dao = new NoticeDAO(); String userId = getNickname();
					 * String noticeContent = inputValue;
					 * 
					 * vo.setUserId(userId); // vo의 userID 빈 깡통에 채워넣어줌
					 * vo.setNoticecontents(noticeContent); // vo의 Noticecontents 빈 깡통에 채워넣어줌
					 * dao.insertNotice(vo);
					 */		 			// 채워진 위 두개의 깡통을 (내가 NoticeDAO에 만들어 둔) insertNotice란 함수를 사용해 매개변수로 넣어준 것
				} 
				
				// 예외사항이 발생한다면
				catch (IOException e1) {
					// 예외사항을 출력 try catch의 기본문
					e1.printStackTrace();
				}		
			}
		});
		// 공지 다이어로그 끝----------------------------------------------------------------------

		// 강퇴 다이어로그 -----------------------------------------------------------------------
		outButton.addActionListener(new ActionListener() {
			
			//마우스로 동작하는 부분의 이벤트처리
			@Override
			public void actionPerformed(ActionEvent e) {
				// 강퇴할 사람을 입력하는 창을 띄움
				String inputValue = JOptionPane.showInputDialog("강퇴할 사람을 입력하세요");

				try {
					// MultiServerThread으로 보낸다.
					oos.writeObject(nickname + "#kick#" + inputValue);
					
					//강퇴 당했을 때 입장 여부를 false로 바꾸어주는 함수 실행
					UsersDAO userDAO = new UsersDAO();
					int kickNum = userDAO.getUserNum(inputValue);
					userDAO.updateKicked(kickNum);
				} 	
				// 예외사항이 발생한다면
				catch (IOException e1) {
					// 예외사항을 출력 try catch의 기본문
					e1.printStackTrace();
				} 
			}
		});
		// 강퇴 다이어로그 끝------------------------------------------------------------------------------

		// 이벤트 시작 ---------------------------------
		addWindowListener(new WindowAdapter() {
			
			//창을 닫을 시 동작하는 부분
			public void windowClosing(WindowEvent e) {
				try {
					// MultiServerThread으로 보낸다.
					oos.writeObject(id+"#exit");
				} 
				// 예외사항이 발생한다면
				catch (IOException ee) {
					// 예외사항을 출력 try catch의 기본문
					ee.printStackTrace();
				}
				// 프로그램 종료
				System.exit(0);
			}
			//입장 했을 때 보여지는 부분
			public void windowOpened(WindowEvent e) {
				try {
					// MultiServerThread으로 보낸다.
					oos.writeObject(nickname+" #Hello!");
					oos.flush();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				try {
					oos.writeObject(id);	
					jta2.setColumns(10);
					}	
					// 예외사항이 발생한다면
					catch (IOException ee) {
						// 예외사항을 출력 try catch의 기본문
						ee.printStackTrace();
				}
				//jta2 = new JTextArea();
				//jta2.setColumns(10);
				// 채팅창에 커서를 둔다.
				jtf.requestFocus();
			}
		});
		// 이벤트 끝 ----------------------------------------------------
	}
		
	// 공지 세팅 메서드 -----------------
	public void setNotice(String notice) {
		label_test.setText(notice);	
	}
	// 공지 세팅 메서드 끝 ---------------

	// 스레드 연결 끊는 메서드 ------------------------------------------
//	public void disconnect(MultiServerThread mst, String id) {
//
//		try {
//			// 채팅 창을 닫는다. 
//			getSocket().close();
//		} 		
//		// 예외사항이 발생한다면
//		catch (IOException e) {
//			// 예외사항을 출력 try catch의 기본문
//			e.printStackTrace();
//		}
//	}
	// 스레드 연결 끊는 메서드 끝 ------------------------------------------

	// 마우스로 클릭 동작하는 부분의 이벤트처리-----------------------------------------------------------
	public void actionPerformed(ActionEvent e) {

		// str로 버튼 선언, 텍스트 값을 받기 때문에 getActionCommand
		String str = String.valueOf(e.getActionCommand());
		// obj로 버튼 선언, 바꿀 변수명을 받기 때문에 getSource
		Object obj = e.getSource(); 

		// CHANGE 버튼 눌렀을 때
		if(str=="CHANGE") {	
			//null값을 아이디로 넣었을 때에대한 예외처리
			if(t1.getText().equals("")) {
				// 이부분 로그인화면 말고 그냥 경고창만 닫히게
				Warn1 w1 = new Warn1();	
				w1.setSize(200, 100);
				w1.setVisible(true);
			}
			else {
				UsersDAO dao = new UsersDAO();
				Boolean check =	dao.ID_Check(t1.getText());
				//아이디 변경시 중복된 아이디일때 예외처리 ---> 나중에 변경
				if(check.equals(false)) {
					// 이부분 로그인화면 말고 그냥 경고창만 닫히게
					Warn2 w2 = new Warn2();	
					w2.setSize(200, 100);
					w2.setVisible(true);
				}
				else {
					// DB에 반영되는 부분
					changeName(t1.getText());
					// 화면상에 표시
					noticeChangeNick(t1.getText());
					// nickname을 agonick로 선언
					agonick = nickname;
					//입력받은 내용으로 전역변수 닉네임 변경해줌
					nickname = t1.getText();
				}
			}
		}
		// SEND버튼을 누른다면
		if (obj == b2) { 
			// 메세지 보내기
			send(sendmesg);
		} 	
		// LOGOUT버튼을 누른다면
		else if (obj == jbtn) { 
			try {
				// MultiServerThread으로 보낸다.
				oos.writeObject(nickname + "#exit"); 
			} 
			// 예외사항이 발생한다면
			catch (IOException ee) {
				// 예외사항을 출력 try catch의 기본문
				ee.printStackTrace();
			} 
			// 프로그램 종료
			System.exit(0); 
		} 
	}
	// 마우스로 동작하는 부분의 이벤트처리 끝-----------------------------------------------------------
	
	// MultiClientThread에서 강퇴가 인식되면 여기서 프로그램 종료
	public void exit() {
		// 프로그램 종료
		System.exit(0);
	}
	//------------------------------------------------
	
	// 전체 채팅창에 채팅 배치 메서드 ------------------------------------------------------------------
	private void send(String message) {
		// 만약 채팅 입력란에 메시지가 없거나 메시지 길이가 0이라면
		if (message == null || message.length() == 0) {
			// 경고창을 띄운다.
			JOptionPane.showMessageDialog(jframe,"글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE); 
		}
		// 채팅 입력란에 채팅을 입력 했다면
		else { 
			try {
				// Calendar를 사용하기 위해 calendar
				Calendar calendar = Calendar.getInstance();
				// 현재 시간을 가져온다(getTime())
				java.util.Date date = calendar.getTime();
				// getTime()에서 SimpleDateFormat사용 내가 필요한 부분을 포맷
				String today = (new SimpleDateFormat("H:mm:ss").format(date));
				// 이름 + 메시지 + 시간을 출력해준다.
				oos.writeObject(nickname+"#"+sendmesg+"#"+"["+today+"]"); 
			} 
			// 예외사항이 발생한다면
			catch (IOException ee) { 
				// 예외사항을 출력 try catch의 기본문
				ee.printStackTrace();
			} 
			// 채팅창을 공백으로 둔다.
			jtf.setText(""); 
		} 
	}
	// 전체 채팅창에 채팅 배치 메서드 끝 ------------------------------------------------------------------

	//대화명 변경 메서드 --> DB에 반영
		private void changeName(String nickname) {
			
			UsersDAO userDAO = new UsersDAO();
			int userNum = userDAO.getUserNum(getNickname());
			System.out.println(userNum);
			userDAO.updateUser(nickname, userNum);
			
		}
	
	// 대화명 변경 메서드 화면에 반영된다. --------------------------------------------------
	private void noticeChangeNick(String changeName) {
		try {
			// 바꾼 이름 + # + 으로 대화명을... 을 	notice2에 저장
			notice2 = changeName+"#" + "으로 대화명을 변경하셨습니다." + '\n';	
			// notice2를 채팅창에 출력
			oos.writeObject(notice2);
			// 채팅 이름은 배꾼이름으로 변경
			label1.setText(changeName);
		} 
		// 예외사항이 발생한다면
		catch (IOException ee) { 
			// 예외사항을 출력 try catch의 기본문
			ee.printStackTrace();
		} 
		// 채팅창을 공백으로 둔다.
		jtf.setText("");
		//setUserList();
	}
	// 대화명 변경 메서드 끝 --------------------------------------------------

	public void init() throws IOException {
		// 소켓 서버연결
		socket = new Socket(ip, 5000);
		System.out.println("connected...");
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		
		// MultiClientThread에서 사용하기 위한 선언(초기화)
		MultiClientThread ct = new MultiClientThread(this);

		// MultiClientThread 시작
		Thread t = new Thread(ct);
		// 즉 MultiClientThread의 run() 시작
		t.start();
	}

	// 마우스 클릭 이벤트를 처리하는 메소드 --> jlist가 아닌 list함수라 못씀
	@Override
	public void mouseClicked(MouseEvent e) {
		// 마우스 클릭시
		if ((e.getButton() == MouseEvent.BUTTON2) || (e.getButton() == MouseEvent.BUTTON3))
		{
			// popup.show(jta2, e.getX(), e.getY());
		}
	}
	// ------------------------------------
	
	// list Item 이벤트, 선택한 값을 저장한다.
	public void itemStateChanged(ItemEvent e) {
		int state = Integer.parseInt(e.getItem().toString());
		// list여서 사용x
		// listSelectName = list.getItem(state);
	}
	// ------------------------------------------

	// 키가 눌러졌을때 발생하는 이벤트 -------------------------------------------------------------------
	public void keyPressed(KeyEvent e) {    
		// 엔터키가 입력되었을때
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			// 만약 채팅 입력란에 메시지가 없거나 메시지 길이가 0이라면
			if (jtf.getText() == null || jtf.getText().length() == 0) {
				// 경고창을 띄운다.
				JOptionPane.showMessageDialog(jframe,"글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			//
			Calendar calendar = Calendar.getInstance();
			//
			java.util.Date date = calendar.getTime();
			// today로 시간 포멧 선언
			String today = (new SimpleDateFormat("H:mm:ss").format(date));
			// 이름 + 메시지 + 시간을 출력해준다.
			sendmesg = jtf.getText() +"["+ today + "]" + '\n';
			// 채팅창을 공백으로 둔다.
			jtf.setText("");

			// 만약 채팅창에 \clear를 입력한다면
			if(sendmesg.contains("/clear")) {
				//채팅창을 공백으로 만든다.
				this.getJta().setText("  ");
				return;
			}
			// 만약 채팅창에 "비속어"를 입력한다면
			if(sendmesg.contains("비속어")) {
				// 채팅창에 이름 + 님이 비속어를... 을 출력해준다.
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
				// use Wrong Word 출력
				System.out.println("use Wrong Word");
			}
			
			//입력받은 채팅을 DB에 저장하는 부분
			ChatVO chatVO = new ChatVO();
			ChatDAO chatDAO = new ChatDAO();
			
			String userId = nickname;
			String contents = sendmesg;
			
			chatVO.setUserId(userId);
			chatVO.setContents(contents);
			
			chatDAO.insertChat(chatVO);
			
			// 문자를 채팅창에 출력해준다. 
			send(sendmesg);
		}
	}
	// 키가 눌러졌을때 발생하는 이벤트 끝 ------------------------------------------------------------------------

	/*
	 * public void setUserList() {
	 * 
	 * listOut.setText(""); ArrayList<String> arr = new ArrayList<String>(); dao =
	 * new UsersDAO(); arr = dao.getUserList(); // 전체 출력 listOut.append("[유저목록]"+
	 * "\n"); for (int i = 0; i < arr.size(); i++) { listOut.append(arr.get(i) +
	 * "\n"); } }
	 */
	
	// main문
	// 예외사항이 발생한다면
	public static void main(String args[]) throws IOException {
		//
		JFrame.setDefaultLookAndFeelDecorated(true);
	}
	// ---------------
	
	//implements로 인해 강제로 추가한부분
	//가능하다면 익명클래스로 구현해서 이부분을 없앨것
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	// nickname의 getter setter 선언 --------
	public String getNickname() {
		return nickname;
	}	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	// nickname의 getter setter 선언 끝 ------
	
	public JTextArea getJta2(){
		return jta2;
	}
	
	public void addUser(String user) {
		jta2.append(user + "\n");
	}
	
	public void clearUsers() {
		jta2.setText("");
	}
	
	// 소켓 연결 통로 ois,jta getter 설정 ---------------
	public ObjectInputStream getOis() {
		return ois;
	}
	public JTextArea getJta() {
		return jta;
	}
	// ois,jta getter 설정 끝 -------------
	
	// socket의 gutter,setter설정
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Socket getSocket() {
		return socket;
	}
	// ---------------------------
}
