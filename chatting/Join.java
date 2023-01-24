package chatting;
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

//생성자
public class Join extends Frame implements ActionListener, KeyListener {

	//------------------------------GUI 구성-------------------------------
	MultiServer ms;
	MultiServerThread mst;
	TextField t1;
	TextField t2;
	TextField t3;
	Button joinButton = new Button("가입하기");

	public Join() {

		setLayout(new BorderLayout()); // 프레임 레이아웃 설정

		Panel p1 = new Panel(); // 패널 객체 생성
		Panel p2 = new Panel();
		Panel p3 = new Panel();

		p1.setLayout(new FlowLayout()); // 패널 레이아웃 설정
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		
		Label label1 = new Label("아이디 : "); // 로그인창에 대한 UI설정
		Label label2 = new Label("비밀번호 : ");
		t1 = new TextField(15);
		t2 = new TextField(15);
		t2.setEchoChar('*');

		joinButton.addActionListener(this); // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		t1.addKeyListener(this);
		t2.addKeyListener(this);

		// 패널에 각 컴포넌트 배치
		p1.add(label1); 
		p1.add(t1);
		p2.add(label2);
		p2.add(t2);
		p3.add(joinButton);
		
		add(BorderLayout.NORTH, p1); // 프레임에 각 패널 배치
		add(BorderLayout.CENTER, p2);
		add(BorderLayout.SOUTH, p3); 

		// 버튼 및 패널의 배경색 설정
		// Color(R, G, B);
		// mainColor - 연파랑 / pointColor - 연분홍
		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);
		joinButton.setBackground(mainColor); 
		p1.setBackground(pointColor);
		p2.setBackground(pointColor);	

		// WindowEvent에 대한 리스너 등록
		this.addWindowListener(new WindowAdapter() { // 창닫기 기능
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	//------------------------------이벤트------------------------------------ 
	//키보드 키 입력시 발생하는 이벤트 정의
	public void keyPressed(KeyEvent e) {

		if (e.getKeyChar() == KeyEvent.VK_ENTER) { // 로그인 정보 입력후 엔터키가 입력되었을때

			if ("".equals(t1.getText())) {

				Warn1 w1 = new Warn1();
				w1.setSize(200, 100);
				w1.setVisible(true);
			} else if ("".equals(t2.getText())) {
				Warn2 w2 = new Warn2();
				w2.setSize(200,100);
				w2.setVisible(true);
			} else {								// 모든 입력이 정상일때 

				MultiClient cc =new MultiClient(t1.getText()); 
				UsersVO userVO = new UsersVO();
				UsersDAO userDAO = new UsersDAO();

				String userId= t1.getText();
				String userPw = t2.getText();

				userVO.setUserId(t1.getText());
				userVO.setUserPw(t2.getText());

				boolean idUse =  userDAO.ID_Check(userId);
				if(idUse = true) {
					System.out.println("가입된 아이디");

					try { 
						//로그인
						int result = userDAO.login(userId, userPw);

						if(result == 1) {
							cc.setTitle("채팅 프로그램");
							cc.setSize(1000,600);
							cc.setVisible(true);
							cc.label1.setText(t1.getText());
							cc.init();				
							System.out.println("로그인 성공");
						}else if(result == 0) {
							System.out.println("비밀번호 불일치");
							Warn2 w2 = new Warn2();
							w2.setSize(200,100);
							w2.setVisible(true);
						}else {
							System.out.println("기타 오류");
						}

					} catch (IOException e1){ 
						e1.printStackTrace(); 
					}
					setVisible(false); // 로그인창을 없앤다.
				
				}else {		//false인 경우
					userDAO.join(userVO);
					System.out.println("회원가입됨");
					cc.setTitle("채팅 프로그램");
					cc.setSize(1000,600);
					cc.setVisible(true);
					cc.label1.setText(t1.getText());
					try {
						cc.init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}				
					System.out.println("로그인 성공");
					
				}
			} 
		}
	}

	//마우스로 동작하는 부분의 이벤트처리
	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		
		if(obj == joinButton) {
			if (t1.getText().equals("")) 			// 유저이름 미입력시 경고창 출력
			{
				Warn1 w1 = new Warn1();
				w1.setSize(200, 100);
				w1.setVisible(true);
			}else if (t2.getText().equals("")) {		// 비밀번호 미입력시 경고창 출력
				Warn2 w2 = new Warn2();
				w2.setSize(200,100);
				w2.setVisible(true);
			} else {
				
				
					//여기 로그인함수 추가***************************
				UsersVO userVO = new UsersVO();
				UsersDAO userDAO = new UsersDAO();

				
				
				String userId= t1.getText();
				String userPw = t2.getText();
				//로그인 객체불러와서 닫힐때열리게
				Login login = new Login();

				boolean idUse =  userDAO.ID_Check(userId);
				if(idUse = true) {
					
					userVO.setUserId(userId);
					userVO.setUserPw(userPw);

					userDAO.join(userVO);
					System.out.println("회원가입됨");
					
					login.setSize(getPreferredSize());
					login.setVisible(true);
					setVisible(false);
					
				}
				
				
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	//------------------------------메인------------------------------------ 
	public static void main(String args[]) {
		Join f = new Join();
		f.setTitle("채팅 로그인");
		f.setSize(300, 200);
		f.setVisible(true);
	}
}