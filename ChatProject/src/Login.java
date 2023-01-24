// 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

// AWT 컴포넌트가 발생시키는 다양한 이벤트를 처리하는데 필요한 요소들을 포함하는 API를 import해준다.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

// 생성자
public class Login extends Frame implements ActionListener, KeyListener {

	// GUI 구성 시작 -------------------------------------------------------------- 
	MultiServer ms;
	MultiServerThread mst;
	TextField t1;
	TextField t2;
	TextField t3;

	//Button b2 = new Button("취 소");
	//Button b1 = new Button("확 인");
	Button loginButton = new Button("로그인");
	Button joinButton = new Button("회원가입");

	public Login() {
		// 패널 객체 생성
		Panel p1 = new Panel(); 
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		
		// 프레임 레이아웃 설정
		setLayout(new BorderLayout()); 

		// 패널 레이아웃 설정
		p1.setLayout(new FlowLayout()); 
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		
		// 로그인창에 대한 UI설정
		Label label1 = new Label("아이디 : ");
		t1 = new TextField(15);
		Label label2 = new Label("비밀번호 : ");
		t2 = new TextField(15);
		t2.setEchoChar('*');

		// 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		//b1.addActionListener(this); 
		//b2.addActionListener(this); 
		t1.addKeyListener(this);
		t2.addKeyListener(this);
		loginButton.addActionListener(this); 
		joinButton.addActionListener(this);

		// 패널에 각 컴포넌트 배치
		p1.add(label1); 
		p1.add(t1);
		//p1.add(b1);
		p2.add(label2);
		p2.add(t2);
		//p2.add(b2);
		p3.add(loginButton);
		p3.add(joinButton);
		
		// 프레임에 각 패널 배치
		add(BorderLayout.NORTH, p1); 
		add(BorderLayout.CENTER, p2);
		add(BorderLayout.SOUTH, p3);
		
		// Color(R, G, B);
		// mainColor - 연파랑
		Color mainColor = new Color(180, 195, 220);
		// pointColor - 연분홍
		Color pointColor = new Color(240, 230, 230);

		// 버튼 및 패널의 배경색 설정
		//b1.setBackground(mainColor); 
		//b2.setBackground(mainColor);
		p1.setBackground(pointColor);
		p2.setBackground(pointColor);	
		// GUI 구성 끝 -------------------------------------------------------------

		// WindowEvent에 대한 리스너 등록
		// 창닫기 기능 -----------------------------------
		this.addWindowListener(new WindowAdapter() { 
			//창을 닫을 시 동작하는 부분
			public void windowClosing(WindowEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
		// 창닫기 기능 끝 ----------------------------------
	}

	// 이벤트
	// 키보드 키 입력시 발생하는 이벤트 정의 -------------------------------------------------
	public void keyPressed(KeyEvent e) {
		
		UsersVO userVO = new UsersVO();
		UsersDAO userDAO = new UsersDAO();

		String userId= t1.getText();
		String userPw = t2.getText();

		userVO.setUserId(t1.getText());
		userVO.setUserPw(t2.getText());
		
		// 로그인 정보 입력후 엔터키가 입력되었을때
		if (e.getKeyChar() == KeyEvent.VK_ENTER) { 
			// 아이디를 입력하지 않는다면 오류 창 띄우기
			if (t1.getText().equals("")) {
				// Warn1을 w1으로 선언
				Warn1 w1 = new Warn1();
				// 오류 창 사이즈
				w1.setSize(200, 100);
				// 오류 창 출력
				w1.setVisible(true);
			} 
			// 비밀번호를 입력하지 않는다면 오류 창 띄우기
			else if (t2.getText().equals("")) {
				// Worn2을 w2로 선언
				Warn2 w2 = new Warn2();
				// 오류 창 사이즈
				w2.setSize(200,100);
				//오류 창 출력
				w2.setVisible(true);
			} 
			// 아이디와 비밀번호를 입력하여 모든 입력이 정상일때
			else {								
				boolean idUse =  userDAO.ID_Check(userId);
				int kickedCheck = userDAO.checkKicked(userId);
				
				//입장 가능한 사람인지 여부 테스트
				if(kickedCheck == 1) {
					Warn3 w3 = new Warn3();
					w3.setSize(200,100);
					w3.setVisible(true);
				}
				else {
				// 모든 입력이 정상일때 
				if(idUse = true) {
					System.out.println("가입된 아이디");

					try { 
						// 로그인
						int result = userDAO.login(userId, userPw);
						// 
						if(result == 1) {
							// getText()에 넣어진 아이디 값으로 채팅 창을 만든다. Login -> MultiClient
							// t1은 아이디 입력받는 TextField, 입력 받은 값은 MultiClient의 id로 사용된다.
							MultiClient cc =new MultiClient(t1.getText()); 
							// 채팅창 이름
							cc.setTitle("채팅 프로그램");
							// 채팅창 크기
							cc.setSize(1000,600);
							// 채팅창 출력
							cc.setVisible(true);
							// 채팅창에 아이디 표시
							cc.label1.setText(t1.getText());
							// 소켓을 만들기위한 초기 세팅
							cc.init();	
						}
						// 비밀번호 불일치 오류
						else if(result == 0) {
							// Warn2을 w2으로 선언
							Warn2 w2 = new Warn2();
							// 오류 창 사이즈
							w2.setSize(200,100);
							// 오류 창 출력
							w2.setVisible(true);
						}
						else {
							// 
							System.out.println("기타 오류");
						}
					} 
					// 예외사항이 발생한다면
					catch (IOException e1){
						// 예외사항을 출력 try catch의 기본문
						e1.printStackTrace(); 
					}
					// 로그인 창 닫기
					setVisible(false); 
					}
				}
			} 
		}
	}

	//마우스로 동작하는 부분의 이벤트처리
	@Override
	public void actionPerformed(ActionEvent e) {
		// 클릭한 대상을 가져오는 함수 obj로 선언
		Object obj = e.getSource();
		/*
		 * // b2(취소버튼)을 누른다면 if(obj == b2) { // 프로그램 종료 System.exit(0); }
		 */
		// b1(확인버튼)을 누른다면
		//if(obj == b1) {
			// 유저이름 미입력시 경고창 출력
			if (t1.getText().equals("")) {
				// Warn1을 w1으로 선언
				Warn1 w1 = new Warn1();
				// 오류 창 사이즈
				w1.setSize(200, 100);
				// 오류 창 출력
				w1.setVisible(true);
			}
			// 비밀번호 미입력시 경고창 출력
			else if (t2.getText().equals("")) {	
				// Warn2을 w2으로 선언
				Warn2 w2 = new Warn2();
				// 오류 창 사이즈
				w2.setSize(200,100);
				// 오류 창 출력
				w2.setVisible(true);
			} 
			else {
				// getText()에 넣어진 아이디 값으로 채팅 창을 만든다. Login -> MultiClient
				// t1은 아이디 입력받는 TextField, 입력 받은 값은 MultiClient의 id로 사용된다.
				MultiClient cc =new MultiClient(t1.getText()); 
				try { 
					// 채팅창 크기
					cc.setSize(1000,600);
					// 채팅창 출력
					cc.setVisible(true);
					// 채팅창에 아이디 표시
					cc.label1.setText(t1.getText());
					// 소켓을 만들기위한 초기 세팅
					cc.init();
				} 
				// 예외사항이 발생한다면
				catch (IOException e1) { 
					// 예외사항을 출력 try catch의 기본문
					e1.printStackTrace(); 
				}
				// 로그인 창 닫기
				setVisible(false);
			}
		//}
		//회원가입 버튼 눌렀을 때
		if(obj == joinButton) {
			System.out.println("회원가입 버튼 눌림");
			Join join = new Join();
			join.setTitle("회원가입 창");
			join.setSize(300,150);
			join.setVisible(true);
			setVisible(false); // 로그인창을 없앤다.
		}
	}
	
	// 사용안하는 기능 익명클래스를 사용하여 지우자
	public void keyTyped(KeyEvent e) {}
	// 사용안하는 기능 익명클래스를 사용하여 지우자
	public void keyReleased(KeyEvent e) {}

	// 메인문 로그인 창 ------------------------------
	public static void main(String args[]) {
		// 로그인 창 생성
		Login f = new Login();
		// 로그인 창 이름
		f.setTitle("채팅 로그인");
		// 로그인 창 크기
		f.setSize(300, 200);
		// 로그인 창 열기
		f.setVisible(true);
	}
	// 메인문 로그인 창 끝 -----------------------------
}