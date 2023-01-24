import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

//implements 뒤에 이벤트 추가
public class ActionEventEx extends Frame implements ActionListener, WindowListener {
	Panel p;
	Button input, exit;
	TextArea ta;

	public ActionEventEx() {

		super("ActionEvent Test");

		p=new Panel();

		input = new Button("1");
		exit = new Button("2");
		ta = new TextArea();

		input.addActionListener(this);
		exit.addActionListener(this);

		p.add(input);
		p.add(exit);

		//자신에게 이벤트를 상속했기 때문에 this를 사용, 앞의 this는 생략가능
		this.addWindowListener(this);
		

		add(p, BorderLayout.NORTH);
		add(ta, BorderLayout.CENTER);

		setBounds(300,300,300,200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String name;
		name = ae.getActionCommand();

		if(name.equals("1"))
			ta.append("append1.\n");
		else {
			ta.append("append2.\n");
			try {
				Thread.sleep(2000);
			} catch(Exception e) {}

			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new ActionEventEx();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//x를 누른다면 창을 닫는다
	public void windowClosing(WindowEvent e) {

		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
