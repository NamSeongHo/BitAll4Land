
import java.awt.*;       // 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.event.*;   // AWT 컴포넌트가 발생시키는 다양한 이벤트를 처리하는데 필요한 요소들을 포함하는 API를 import해준다.

public class Warn3 extends Frame implements ActionListener,KeyListener
{
	TextField t1;       // 
	TextField t2;
	Login l;
	int flagmsg = 0;
	
	public Warn3() 
	{
	//	setLayout(new BorderLayout());

		Panel p1 = new Panel();
		Panel p2 = new Panel();
		
		setLayout(new BorderLayout());
		p1.setLayout(new FlowLayout());        
		p2.setLayout(new FlowLayout());        

		Label label1 = new Label("PORT 번호를 입력해야 합니다.!!");
		Button b1 = new Button("확 인");


		b1.addActionListener(this);                    // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b1.addKeyListener(this);
		
		p1.add(label1);
		p2.add(b1);

				
		add(BorderLayout.CENTER,p1);
		add(BorderLayout.SOUTH,p2);

		b1.setBackground(Color.magenta);
		p1.setBackground(Color.yellow);
		p2.setBackground(Color.yellow);

this.addWindowListener(new WindowAdapter(){//창닫기 기능
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

	
	public void keyPressed(KeyEvent e) {
		l = new Login();

		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			setVisible(false);
		}
	}

	public void keyTyped(KeyEvent e) { }
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