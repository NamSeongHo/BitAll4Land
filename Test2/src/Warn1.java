
import java.awt.*;       // 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.event.*;   // AWT 컴포넌트가 발생시키는 다양한 이벤트를 처리하는데 필요한 요소들을 포함하는 API를 import해준다.

public class Warn1 extends Frame implements ActionListener,KeyListener
{
	TextField t1;
	TextField t2;
	Login l;
		
	public Warn1() 
	{
		Panel p1 = new Panel();           // 패널 객체 생성
		Panel p2 = new Panel();
		
		setLayout(new BorderLayout());                 // 프레임의 레이아웃 설정
		p1.setLayout(new FlowLayout());                      // 패널의 레이아웃 설정
		p2.setLayout(new FlowLayout());                      // 패널의 레이아웃 설정

		Label label1 = new Label("대화명을 입력해야 합니다!!");              // 대화명 미입력시 대화명 입력요구를 띄우기위한 라벨 객체 생성
		
		Button b1 = new Button("확 인");


		b1.addActionListener(this);                    // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b1.addKeyListener(this);
		
		p1.add(label1);                    // 패널에 각 컴포넌트 배치
		p2.add(b1);

				
		add(BorderLayout.CENTER,p1);               // 프레임에 각 패널을 붙인다.
		add(BorderLayout.SOUTH,p2);

		b1.setBackground(Color.magenta);
		p1.setBackground(Color.yellow);
		p2.setBackground(Color.yellow);

// WindowEvent에 대한 리스너 등록
this.addWindowListener(new WindowAdapter(){                       // 창닫기를 위한 이벤트 발생
   public void windowClosing(WindowEvent e){
    System.exit(0);
   }
  });

	}

	
	public static void main(String args[])
	{
		Warn1 w1 = new Warn1();
		w1.setTitle("로그인 에러");
		w1.setSize(200,100);
		w1.setVisible(true);
	}

	// KeyEvent에 대한 리스너 등록 및 해당 메소드 선언
	public void keyPressed(KeyEvent e) {
		l = new Login();

		if(e.getKeyChar() == KeyEvent.VK_ENTER)                     // 경고창이 뜨고 엔터키를 눌렀을때
		{
			setVisible(false);                                 // 경고창을 없앤다.
		}
	}

	public void keyTyped(KeyEvent e) { }            // 키 리스너에 포함된 메소드
	public void keyReleased(KeyEvent e) { }

	public void actionPerformed(ActionEvent e) 
	{      // 액션이 발생할때 호출되는 메소드
	
		l = new Login();
		String str =String.valueOf(e.getActionCommand());    // 이벤트가 발생한 버튼의 명령을 문자로 변환
		if((str=="확 인")) {
			setVisible(false);
		}
	}

}