
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
import java.io.IOException;
import java.awt.event.*;

public class Login extends Frame implements ActionListener, KeyListener {
	TextField t1;
	TextField t2;
	TextField t3;

	public Login() {
		Panel p1 = new Panel(); // 패널 객체 생성
		Panel p2 = new Panel();
		Panel p3 = new Panel();

		setLayout(new BorderLayout()); // 프레임 레이아웃 설정

		p1.setLayout(new FlowLayout()); // 패널 레이아웃 설정
		p2.setLayout(new FlowLayout());

		Label label1 = new Label("USER NAME : "); // 로그인창에 대한 UI설정
		t1 = new TextField(15);
		Label label2 = new Label("IP ADDRESS : ");

		t2 = new TextField(15);
		Label label3 = new Label("PORT NUMBER : ");
		t3 = new TextField(7);

		Button b1 = new Button("확 인");
		Button b2 = new Button("취 소");

		b1.addActionListener(this); // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b2.addActionListener(this); // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		t1.addKeyListener(this);
		t2.addKeyListener(this);
		t3.addKeyListener(this);

		p1.add(label1); // 패널에 각 컴포넌트 배치
		p1.add(t1);
		p1.add(b1);
		p2.add(label2);
		p2.add(t2);
		p2.add(b2);
		p3.add(label3);
		p3.add(t3);

		add(BorderLayout.NORTH, p1); // 프레임에 각 패널 배치
		add(BorderLayout.CENTER, p2);
		add(BorderLayout.SOUTH, p3);

		// Color(R, G, B);
		// mainColor - 연파랑 / pointColor - 연분홍
		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);

		b1.setBackground(mainColor); // 버튼 및 패널의 배경색 설정
		b2.setBackground(mainColor);
		p1.setBackground(pointColor);
		p2.setBackground(pointColor);
		p3.setBackground(pointColor);

// WindowEvent에 대한 리스너 등록
		this.addWindowListener(new WindowAdapter() { // 창닫기 기능
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	public static void main(String args[]) {
		Login f = new Login();
		f.setTitle("채팅 로그인");
		f.setSize(300, 200);
		f.setVisible(true);
	}

	/** 키보드 키를 눌렀을때 이벤트를 발생하는 메소드 */
	public void keyPressed(KeyEvent e) {

		if (e.getKeyChar() == KeyEvent.VK_ENTER) // 로그인 정보 입력후 엔터키가 입력되었을때
		{
//
			if ("".equals(t1.getText())) // 대화명 미입력시 대화명 입력명령 경고창 띄움
			{
				Warn1 w1 = new Warn1();
				w1.setSize(200, 100);
				w1.setVisible(true);
			} else if ("".equals(t2.getText())) // 서버IP 미입력시 서버IP 입력명령 경고창 띄움
			{
//				w2.setSize(200,100);
//				w2.setVisible(true);
			} else if ("".equals(t3.getText())) // PORT 번호 미입력시 PORT 입력명령 경고창 띄움
			{
				System.out.println("3");
//				w3.setSize(200,100);
//				w3.setVisible(true);
			} else // 모든 입력이 정상일때
				System.out.println("4");
			{
				
				 MultiClient cc =new MultiClient(t1.getText()); try { 
					 cc.setSize(1000,600);
					 cc.setVisible(true);
					 cc.label1.setText(t1.getText());
					 cc.init();
					 } catch (IOException e1) { 
				e1.printStackTrace(); }
				 

//				client.setId(t1.getText());                       // 입력된 대화명을 채팅창 상단의 텍스트필드에 입력
//				client.ip = t2.getText();
//				chat.port = t3.getText();
				setVisible(false); // 로그인창을 없애고
//				chat.setSize(700,600);
//				client.setVisible(true);                       // 채팅창을 띄운다.
//				chat.print(chat.nickname,chat.ip,chat.port);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

//마우스로 동작하는 부분임 아래 주석처리함
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

//일단 마우스대신 엔터로만 되게해보자 ..	
	/*
	 * public void actionPerformed(ActionEvent e) { // 액션이 발생할때 호출되는 메소드 // 키이벤트와
	 * 동일하게 버튼을 클릭시 수행되는 이벤트 처리 부분으로 내용은 키 이벤트와 같음.↓
	 * 
	 * chat = new Chat(); w1 = new Warn1(); w2 = new Warn2(); w3 = new Warn3();
	 * 
	 * String str =String.valueOf(e.getActionCommand()); // 이벤트가 발생한 버튼의 명령을 문자로 변환
	 * if((str=="확 인")) { if("".equals(t1.getText())) { w1.setSize(200,100);
	 * w1.setVisible(true); } else if("".equals(t2.getText())) {
	 * w2.setSize(200,100); w2.setVisible(true); } else if("".equals(t3.getText()))
	 * { w3.setSize(200,100); w3.setVisible(true); } else { chat.nickname =
	 * t1.getText(); chat.ip = t2.getText(); chat.port = t3.getText();
	 * 
	 * setVisible(false); chat.setSize(700,500); chat.setVisible(true);
	 * chat.print(chat.nickname,chat.ip,chat.port); } }else if((str=="취 소")) {
	 * System.exit(0); } }
	 */
}