//로그인창에서 예외상황 발생시 경고 프레임

// 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.*;       
//AWT 컴포넌트가 발생시키는 다양한 이벤트를 처리하는데 필요한 요소들을 포함하는 API를 import해준다.
import java.awt.event.*;   

public class Warn1 extends Frame implements ActionListener,KeyListener {
	TextField t1;
	TextField t2;
	Login l;
	
	public Warn1() {
		Panel p1 = new Panel();
		Panel p2 = new Panel();

		setLayout(new BorderLayout());
		p1.setLayout(new FlowLayout());        
		p2.setLayout(new FlowLayout());        

		//경고창 메시지
		Label label1 = new Label("아이디릅 입력해야 합니다!!");

		Button b1 = new Button("확 인");

		// 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b1.addActionListener(this);                    
		b1.addKeyListener(this);

		p1.add(label1);
		p2.add(b1);

		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);

		add(BorderLayout.CENTER,p1);
		add(BorderLayout.SOUTH,p2);

		b1.setBackground(mainColor);
		p1.setBackground(pointColor);
		p2.setBackground(pointColor);
		
		//창닫기 기능
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
	}

	public void keyPressed(KeyEvent e) {
		l = new Login();
		
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			setVisible(false);
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	// 액션이 발생할때 호출되는 메소드
	public void actionPerformed(ActionEvent e) {    
		// 이벤트가 발생한 버튼의 명령을 문자로 변환
		l = new Login();
		String str =String.valueOf(e.getActionCommand());    
		if((str=="확 인")) {
			setVisible(false);
		}
	}
}