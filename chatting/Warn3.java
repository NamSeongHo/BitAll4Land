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

public class Warn3 extends Frame implements ActionListener,KeyListener
{
	TextField t1;
	TextField t2;
	Login l;
	
	public Warn3() 
	{
		//setLayout(new BorderLayout());
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		
		setLayout(new BorderLayout());
		p1.setLayout(new FlowLayout());        
		p2.setLayout(new FlowLayout());        

		Label label1 = new Label("강퇴당한 사용자입니다");

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

this.addWindowListener(new WindowAdapter(){//창닫기 기능
   public void windowClosing(WindowEvent e){
    System.exit(0);
   }
  });

	}

	
	public static void main(String args[])
	{
		Warn3 w2 = new Warn3();
		w2.setTitle("로그인 에러");
		w2.setSize(200,100);
		w2.setVisible(true);
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
	
	// 액션이 발생할때 호출되는 메소드
	public void actionPerformed(ActionEvent e) 
	{     
	
		
		// 이벤트가 발생한 버튼의 명령을 문자로 변환
		String str =String.valueOf(e.getActionCommand());    
		if((str=="확 인")) {
			setVisible(false);
			l = new Login();
			l.setSize(300,200);
			l.setVisible(true);
		}
	}

}