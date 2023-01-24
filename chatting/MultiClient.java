package chatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.DropMode;


public class MultiClient extends JFrame implements ActionListener,KeyListener,ItemListener,MouseListener {

	private String ip;
//	private String id;
	
	UsersDAO dao;
	MultiServerThread mst;
	MultiServer ms;
	
	private Socket socket;
	private ObjectInputStream ois;	//다른 소켓으로부터 내용을 입력받는 통로역할
	private ObjectOutputStream oos;	//다른 소켓으로부터 내용을 보내는 통로역할
	
	private JFrame jframe;			// 화면 전체 부분
	private JTextField jtf;			// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)            
	private JTextArea jta, jta2 ;	// 대화내용이 입력되는 텍스트에어리어 객체 선언(서버로부터 전달된 메시지 출력 컴포넌트)
	
	private JButton jbtn;			// 로그아웃 버튼
	private JLabel label_test;		// 공지사항이 등록되는 레이블
	JLabel label1, label3;
	JTextField t1;					// 대화명 변경을 입력하기 위한 텍스트 필드 객체 선언

	String listSelectName;  		// 리스트 박스에서 선택된 아이템의 내용을 저장하는 객체
	JButton b2;						// 채팅 보내기 버튼
	JButton b1;						// 유저명 변경 버튼
	JButton outButton; 				// 강퇴버튼

	int state;						// 리스트 아이템 클릭시 이벤트에 사용하기 위해 임시로 생성
	PrintWriter out;				// 서버로의 출력 객체
	BufferedReader in;              // 서버로부터의 입력 객체
	Thread t;
	
	String sendmesg;
	String notice1;		
	String notice2;
	String nickname;
	String agonick;			

	//생성자
	public MultiClient(String id) {	
		ip = "127.0.0.1";
		//this.id = id;
		setTitle("채팅창");
		
		nickname = id;
		
		
		
		//컴포넌트 
		Panel p1 = new Panel();        			// 4개의 패널 객체 생성
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();

		p1.setLayout(new FlowLayout());        // 각 패널의 레이아웃을 설정
		p2.setLayout(new BorderLayout());
		p3.setLayout(new BorderLayout());
		p4.setLayout(new FlowLayout());

		
		t1 = new JTextField(15);				//변경할 아이디를 입력받는 텍스트 필드
		jta = new JTextArea("",5,20);  			// 대화한 내용이 뜨는 영역

		jta2 = new JTextArea(5,10);  
		jta2.setEditable(false);
		
		jtf = new JTextField(60);				// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)	
		label1 = new JLabel("USER NAME : ");
		label3 = new JLabel("CHAT");
		
		b1 = new JButton("CHANGE");
		b2 = new JButton("SEND");
		outButton = new JButton("OUT");
		jbtn = new JButton("LOGOUT");
		JButton b4 = new JButton("NOTIFY");

		label_test = new JLabel("공지사항 올라오는곳");

		b1.addActionListener(this);				// 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b2.addActionListener(this);                   
		jbtn.addActionListener(this);
		jtf.addKeyListener(this);


		p1.add(label1);							// 패널에 각 컴포넌트를 붙인다.
		p1.add(t1);
		p1.add(b1);
		JScrollPane scrollPane = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p2.add(scrollPane);
		p4.add(label3);
		p4.add(jtf);
		p4.add(b2);
		p4.add(jbtn);
		p1.add(b4);
		p1.add(outButton);
		p1.add(label_test);
		p3.add(jta2, BorderLayout.CENTER);

		getContentPane().add(BorderLayout.NORTH,p1);                  // 패널을 프레임의 각 위치에 배치
		getContentPane().add(BorderLayout.CENTER,p2);
		getContentPane().add(BorderLayout.EAST,p3);
		getContentPane().add(BorderLayout.SOUTH,p4);

		// 버튼 및 패널 배경 색깔 지정
		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);
		b1.setBackground(pointColor);              	
		b2.setBackground(pointColor);
		jta2.setBackground(pointColor);
		jbtn.setBackground(pointColor);				
		b4.setBackground(pointColor);
		outButton.setBackground(pointColor);
		p1.setBackground(mainColor);
		p4.setBackground(mainColor);

		//미구현된 부분
//		popup = new PopupMenu();            // 접속자목록에서 오른쪽 마우스 클릭시 귓속말,강퇴 기능의 팝업을 띄우기 위한 객체 생성
//		menu1 = new MenuItem("귓속말");
//		menu2 = new MenuItem("강 퇴");
//		popup.add(menu1);                   // 팝업에 메뉴 컴포넌트를 추가
//		popup.add(menu2);
//		jta2.add(popup);                    // 리스트 컴포넌트에 팝업 컴포넌트를 추가. 

		//리스트에 팝업 메뉴를 위해 마우스 이벤트 등록
		//jta2.addMouseListener(this);
		//jlist.addItemListener(this);
//		popup.addActionListener(this);
		jtf.addActionListener(this);
		jbtn.addActionListener(this);

		//공지다이어로그
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inputValue = JOptionPane.showInputDialog("공지를 입력하세요");
				//label_test.setText(inputValue);
				try {
					
					//공지 입력시 데이터 데이스에 반영 부분
					NoticeDAO dao = new NoticeDAO();
					NoticeVO vo = new NoticeVO();
					String userId = getNickname();
					String noticeContent = inputValue;
					
					vo.setUserId(userId);
					vo.setNoticecontents(noticeContent);
					dao.insertNotice(vo);
					
					oos.writeObject(nickname + "#notice#" + inputValue);
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}
		});


		//강퇴 다이어로그
		outButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputValue = JOptionPane.showInputDialog("강퇴할 사람을 입력하세요");

				try {
					oos.writeObject(nickname + "#kick#" + inputValue);
					//강퇴 당했을 때 입장 여부를 false로 바꾸어주는 함수 실행
					UsersDAO userDAO = new UsersDAO();
					int kickNum = userDAO.getUserNum(inputValue);
					userDAO.updateKicked(kickNum);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}

		});

		//-------------------------이벤트---------------------------------

		addWindowListener(new WindowAdapter() {
			//윈도우 x 클릭시 동작하는 부분
			public void windowClosing(WindowEvent e) {
				try {
					oos.writeObject(id+"#exit");
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				System.exit(0);
			}

			//입장 했을 때 보여지는 부분
			public void windowOpened(WindowEvent e) {
				try {
//					oos.writeObject(nickname+" #WELCOME!!!");
					oos.writeObject(nickname + "#enter");
					oos.flush();
				} catch (IOException ee) {
					ee.printStackTrace();
				}	
				//
				try {
					oos.writeObject(id);	
					jta2.setColumns(10);
					}catch (IOException ee) {
						ee.printStackTrace();
					}
				jtf.requestFocus();
			}
		});

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

	}
	
	//마우스 클릭에 대한 이벤트처리
	public void actionPerformed(ActionEvent e) {

		String str = String.valueOf(e.getActionCommand());
		Object obj = e.getSource(); 

	
		
		//CHANGE 버튼 눌렀을 때 
		if(str=="CHANGE") {	
			
			//null값을 아이디로 넣었을 때에대한 예외처리
			if(t1.getText().equals("")) {
				Warn1 w1 = new Warn1();	//--->이부분 로그인화면 말고 그냥 경고창만 닫히게
				w1.setSize(200, 100);
				w1.setVisible(true);
				
			}else {
				UsersDAO dao = new UsersDAO();
				Boolean check =	dao.ID_Check(t1.getText());
				//아이디 변경시 중복된 아이디일때 예외처리 ---> 나중에 변경
				if(check.equals(false)) {
					Warn2 w2 = new Warn2();	//--->이부분 로그인화면 말고 그냥 경고창만 닫히게
					w2.setSize(200, 100);
					w2.setVisible(true);
				}else {
					//-------DB에 반영되는 부분
					changeName(t1.getText());
					
					//----화면상에 표시
					noticeChangeNick(t1.getText());
					agonick = nickname;
					//입력받은 내용으로 전역변수 닉네임 변경해줌
					nickname = t1.getText();
				}
				
			}
			
		}

		if (obj == b2) { 
			send(sendmesg);
		} else if (obj == jbtn) { 
			try {
				oos.writeObject(nickname + "#exit"); 
			} catch (IOException ee) {
				ee.printStackTrace();
			} 
			System.exit(0); 
		} 
	}
	
	//전체 채팅창에 채팅 배치 메서드
	private void send(String message) {
		//아무것도 입력하지않고 보내는 경우의 예외처리
		if (message == null || message.length() == 0) {
			JOptionPane.showMessageDialog(jframe,"글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE); 
		}
		else { 
			try {

				Calendar calendar = Calendar.getInstance();
				java.util.Date date = calendar.getTime();
				String today = (new SimpleDateFormat("H:mm:ss").format(date));
				oos.writeObject(nickname+"#"+sendmesg+"#"+"["+today+"]"); 
			} catch (IOException ee) { 
				ee.printStackTrace();
			} 
			jtf.setText(""); 
		} 
	}

	//대화명 변경 메서드 --> DB에 반영
	private void changeName(String nickname) {
		
		UsersDAO userDAO = new UsersDAO();
		int userNum = userDAO.getUserNum(getNickname());
		System.out.println(userNum);
		userDAO.updateUser(nickname, userNum);
		
	}

	//대화명 변경 메서드 --> 화면에 반영 
	private void noticeChangeNick(String changeName) {
		try {
			notice2 = changeName+"#" + "으로 대화명을 변경하셨습니다." + '\n';	
			oos.writeObject(notice2);
			label1.setText(changeName);

		} catch (IOException ee) { 
			ee.printStackTrace();
		} 
		jtf.setText(""); 
		//setUserList();
	}
	
	//공지 세팅 메서드
	public void setNotice(String notice) {
		label_test.setText(notice);	
	}

	//프로그램 종료 메서드
	public void exit(){
		System.exit(0);
	}

	/**마우스 클릭 이벤트를 처리하는 메소드 --> jlist가 아닌 list함수라 못씀*/ 
	@Override
	public void mouseClicked(MouseEvent e) {
		// 마우스 클릭시
		if ((e.getButton() == MouseEvent.BUTTON2) || (e.getButton() == MouseEvent.BUTTON3))
		{
			//popup.show(jta2, e.getX(), e.getY());
		}
	}

	/**list Item 이벤트, 선택한 값을 저장한다. */
	public void itemStateChanged(ItemEvent e) {
		int state = Integer.parseInt(e.getItem().toString());
		//listSelectName = jlist.getItem(state);
	}

	/** 키가 눌러졌을때 발생하는 이벤트  */
	public void keyPressed(KeyEvent e) {                                     
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			if (jtf.getText() == null || jtf.getText().length() == 0) {
				JOptionPane.showMessageDialog(jframe,"글을쓰세요", "경고", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String today = (new SimpleDateFormat("H:mm:ss").format(date));
			sendmesg = jtf.getText() +"["+ today + "]" + '\n';

			jtf.setText("");

			if(sendmesg.contains("/clear")){
				this.getJta().setText("  ");
				return;
			}

			if(sendmesg.contains("비속어")) {
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
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
			
			send(sendmesg);
		}
	}

	//implements로 인해 강제로 추가한부분----------------------------------------
	//가능하다면 익명클래스로 구현해서 이부분을 없앨것
	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) { }
	

	
	//------------------------getter/setter--------------------------
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}
	
	
	
	public void addUser(String user) {
		jta2.append(user + "\n");
	}
	
	public void clearUsers() {
		jta2.setText("");
	}
	
	// ------------------------소켓 연결 통로--------------------------
	public ObjectInputStream getOis(){
		return ois;
	}
	public JTextArea getJta(){
		return jta;
	}
	
	public JTextArea getJta2(){
		return jta2;
	}
	
	//---------------------채팅창 초기세팅--------------------------------
	public void init() throws IOException {
		socket = new Socket(ip, 5000);
		System.out.println("connected...");
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		MultiClientThread ct = new MultiClientThread(this);
		
		Thread t = new Thread(ct);
		t.start();
	}
	
	
	//---------------------------main---------------------------------
	public static void main(String args[]) throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);		
		
	}

}
