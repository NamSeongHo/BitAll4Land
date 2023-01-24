package Test2;

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
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiClient extends JFrame implements ActionListener,KeyListener,ItemListener,MouseListener {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame jframe;
    private JTextField jtf;
    private JTextArea jta;
    private String ip;
    private String id;
    private JButton jbtn;
    
	JLabel label1, label3;	//	label2,
	JTextField t1;			// 대화명 변경을 입력하기 위한 텍스트 필드 객체 선언
	String port;
	String agonick;			// 변경 전 대화명을 저장

	String sendmesg;
	String notice1;			// 대화중 각종 변경사항을 담고 있는 스트링 객체 선언					
	String notice2;
	String notice3;
	String notice4;

//	String secret = "";     // 귓속말
	String listSelectName;          // 리스트 박스에서 선택된 아이템의 내용을 저장하는 객체
	JButton b2;                  

	//팝업 메뉴
	PopupMenu popup;
	MenuItem menu1;
	MenuItem menu2;

	int state;
	int i;
	PrintWriter out;					// 서버로의 출력 객체
	BufferedReader in;                  // 서버로부터의 입력 객체
	Thread t;
	List list;
	String nickname;
	private JLabel label_test;

    //생성자
    //_______________생성자 -> 기존에서 ip는 매개변수로 안받도록 수정됨___________________
    //public MultiClient(String argIp, String argId) {
    public MultiClient(String id) {	
    	
        ip = "127.0.0.1";
        //매개변수로 받은 아이디를 전역변수 id값으로 넣어줌 -> getter / setter사용할 필요 x
        //label1.setText("ddd");
        //t1.append(id);
       jframe = new JFrame("Multi Chatting");
       nickname = id;
        
        //추가한 컴포넌트
        
        Panel p1 = new Panel();									// 4개의 패널 객체 생성
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();

		getContentPane().setLayout(new BorderLayout());			// 프레임 레이아웃을 BorderLayout으로 설정

		p1.setLayout(new FlowLayout());							// 각 패널의 레이아웃을 설정
		p2.setLayout(new BorderLayout());
		p3.setLayout(new BorderLayout());
		p4.setLayout(new FlowLayout());

		t1 = new JTextField(15);
		jta = new JTextArea(" ",5,20);  
		jtf = new JTextField(60);								// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)	
		label1 = new JLabel("USER NAME : ");
		label3 = new JLabel("CHAT");
		list = new List(15);
		JButton b1 = new JButton("CHANGE");
		b2 = new JButton("SEND");
		jbtn = new JButton("LOGOUT");
		JButton b4 = new JButton("NOTIFY");



		b1.addActionListener(this);								// 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b2.addActionListener(this);                   
		jbtn.addActionListener(this);
		jtf.addKeyListener(this);


		p1.add(label1);											// 패널에 각 컴포넌트를 붙인다.
		p1.add(t1);
		p1.add(b1);
		p2.add(jta);
		p3.add(list);
		p4.add(label3);
		p4.add(jtf);
		p4.add(b2);
		p4.add(jbtn);
		p1.add(b4);

		getContentPane().add(BorderLayout.NORTH,p1);			// 패널을 프레임의 각 위체에 붙인다.

		getContentPane().add(BorderLayout.CENTER,p2);
		getContentPane().add(BorderLayout.EAST,p3);
		getContentPane().add(BorderLayout.SOUTH,p4);

		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);

		b1.setBackground(pointColor);							// 버튼 및 패널 배경 색깔 지정
		b2.setBackground(pointColor);
		
		//jbtn => 로그아웃 버튼임!!!!
		jbtn.setBackground(pointColor);
		b4.setBackground(pointColor);

		p1.setBackground(mainColor);
		
		label_test = new JLabel("공지");
		p1.add(label_test);
		p3.setBackground(Color.green);
		list.setBackground(pointColor);
		p4.setBackground(mainColor);

		popup = new PopupMenu();								// 접속자목록에서 오른쪽 마우스 클릭시 귓속말,강퇴 기능의 팝업을 띄우기 위한 객체 생성
		menu1 = new MenuItem("귓속말");
		menu2 = new MenuItem("강 퇴");
		popup.add(menu1);										// 팝업에 메뉴 컴포넌트를 추가
		popup.add(menu2);
		list.add(popup);										// 리스트 컴포넌트에 팝업 컴포넌트를 추가. 

		//리스트에 팝업 메뉴를 위해 마우스 이벤트 등록
		list.addMouseListener(this);
		list.addItemListener(this);
		popup.addActionListener(this);
        
        
//_________________________________이벤트       
        jtf.addActionListener(this);
        jbtn.addActionListener(this);
        
        // 공지 다이얼로그
        b4.addActionListener(new ActionListener() {
			
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputValue = JOptionPane.showInputDialog("공지를 입력하세요.");
//				label_test.setText(inputValue);
				try {
//					oos.writeObject("공지#" + inputValue);
					oos.writeObject(nickname + "#notice#" + inputValue);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});


        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    oos.writeObject(id+"#exit");
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                System.exit(0);
            }
            public void windowOpened(WindowEvent e) {
                jtf.requestFocus();
            }
        });
    
        jta.setEditable(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        jframe.pack();
        jframe.setLocation(
                (screenWidth - jframe.getWidth()) / 2,
                (screenHeight - jframe.getHeight()) / 2);
        jframe.setResizable(false);
        jframe.setVisible(true);
    
    }
    
    public void setNotice(String notice) {
    	label_test.setText(notice);
    }

    public void actionPerformed(ActionEvent e) {
		
		  Object obj = e.getSource(); 
		  sendmesg = jtf.getText(); 
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
    public void exit(){
        System.exit(0);
    }
    
    private void send(String message) {
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
    
    
    
    public void init() throws IOException {
        socket = new Socket(ip, 5000);
        System.out.println("connected...");
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        MultiClientThread ct = new MultiClientThread(this);
        Thread t = new Thread(ct);
        t.start();
    }
    

    public static void main(String args[]) throws IOException {

        JFrame.setDefaultLookAndFeelDecorated(true);
        
    }
    
    
    public ObjectInputStream getOis(){
        return ois;
    }
    public JTextArea getJta(){
        return jta;
    }
    
    //_______________아이디 받아오기위해 추가된 부분 
    public void setId(String id) {
		this.id = id;
	}
    
    public String getId(){
        return id;
    }
    //_______________아이디 받아오기위해 추가된 부분 

	@Override
	/**마우스 클릭 이벤트를 처리하는 메소드 */
	public void mouseClicked(MouseEvent e) {
		// 마우스에서 가운데버튼, 오른쪽 버튼을 클릭했을때..
		if ((e.getButton() == MouseEvent.BUTTON2) || (e.getButton() == MouseEvent.BUTTON3))
		{
			popup.show(list, e.getX(), e.getY());
		}
	}

	/**list Item 이벤트, 선택한 값을 저장한다. */
	public void itemStateChanged(ItemEvent e) {
		int state = Integer.parseInt(e.getItem().toString());
		listSelectName = list.getItem(state);
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

			if(sendmesg.contains("비속어")) {
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
			}
			
			send(sendmesg);
			

			 
		}
	}
	
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
	
	public JLabel getLabel() {
		return label_test;
	}
	
}
