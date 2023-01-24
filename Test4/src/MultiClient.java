
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
//---------------------------------------
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.JFrame;

public class MultiClient extends JFrame implements ActionListener,KeyListener,ItemListener,MouseListener {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame jframe,votf;
    private JTextField jtf;
    private JTextArea jta;
//    private JLabel jlb1, jlb2;
//    private JPanel jp1, jp2;
    private String ip;
    private String id;
    private JButton jbtn, bt1, bt2;
    //---------------------------
    private JTextPane jtp;
	//private JFrame jframe,votf;
    
//	JLabel label1, label2, label3;
	JLabel label1, label3;
	JTextField t1;			// 대화명 변경을 입력하기 위한 텍스트 필드 객체 선언
//	JTextField t2;			// 접속자수를 보여주는 텍스트 필드 객체 선언
//	JTextArea jta;            // 대화내용이 입력되는 텍스트에어리어 객체 선언(서버로부터 전달된 메시지 출력 컴포넌트)
//	JTextField jtf;			// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)            
//	String nickname;
	String port;
	String agonick;			// 변경 전 대화명을 저장

	String sendmesg;
	String notice1;			// 대화중 각종 변경사항을 담고 있는 스트링 객체 선언					
	String notice2;
	String notice3;
	String notice4;
	private JLabel label_test;

//	String secret = "";     // 귓속말
	String listSelectName;          // 리스트 박스에서 선택된 아이템의 내용을 저장하는 객체
	JButton b2;
	JButton b1;

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
 //       jtf = new JTextField(30);
 //       jta = new JTextArea("", 10, 50);
 //       jlb1 = new JLabel("Usage ID : [[ " + id + "]]");
 //       jlb2 = new JLabel("IP : " + ip);
 //       jbtn = new JButton("종료");
 //       jp1 = new JPanel();
 //       jp2 = new JPanel();
 //       jlb1.setBackground(Color.yellow);
 //       jlb2.setBackground(Color.green);
 //       jta.setBackground(Color.pink);
 //       jp1.setLayout(new BorderLayout());
 //       jp2.setLayout(new BorderLayout());

 //       jp1.add(jbtn, BorderLayout.EAST);
 //       jp1.add(jtf, BorderLayout.CENTER);
 //       jp2.add(jlb1, BorderLayout.CENTER);
 //       jp2.add(jlb2, BorderLayout.EAST);

 //       jframe.add(jp1, BorderLayout.SOUTH);
 //       jframe.add(jp2, BorderLayout.NORTH);
 //       JScrollPane jsp = new JScrollPane(jta,
 //              JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
 //               JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
 //       jframe.add(jsp, BorderLayout.CENTER);
        
        
        //내가 추가한 컴포넌트
        
        Panel p1 = new Panel();        // 4개의 패널 객체 생성
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();

		setLayout(new BorderLayout());       // 프레임 레이아웃을 BorderLayout으로 설정

		p1.setLayout(new FlowLayout());        // 각 패널의 레이아웃을 설정
		p2.setLayout(new BorderLayout());
		p3.setLayout(new BorderLayout());
		p4.setLayout(new FlowLayout());

		t1 = new JTextField(15);
//		t2 = new JTextField(5);
		jta = new JTextArea(" ",5,20);  
		jtf = new JTextField(60);		// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)	
		label1 = new JLabel("USER NAME : ");
//		label2 = new JLabel("USER COUNT : ");
		label3 = new JLabel("CHAT");
		list = new List(15);
		b1 = new JButton("CHANGE");
		b2 = new JButton("SEND");
		jbtn = new JButton("LOGOUT");
		JButton b4 = new JButton("NOTIFY");

		label_test = new JLabel("공지사항 올라오는곳");
		


		b1.addActionListener(this);                    // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b2.addActionListener(this);                   
		jbtn.addActionListener(this);
		jtf.addKeyListener(this);


		p1.add(label1);                              // 패널에 각 컴포넌트를 붙인다.
		p1.add(t1);
		p1.add(b1);
//		p1.add(label2);
//		p1.add(t2);
		p2.add(jta);
		p3.add(list);
		p4.add(label3);
		p4.add(jtf);
		p4.add(b2);
		p4.add(jbtn);
		p1.add(b4);
		p1.add(label_test);

		add(BorderLayout.NORTH,p1);                  // 패널을 프레임의 각 위체에 붙인다.

		add(BorderLayout.CENTER,p2);
		add(BorderLayout.EAST,p3);
		add(BorderLayout.SOUTH,p4);

		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);

		b1.setBackground(pointColor);              // 버튼 및 패널 배경 색깔 지정
		b2.setBackground(pointColor);
		
		//jbtn => 로그아웃 버튼임!!!!
		jbtn.setBackground(pointColor);
		b4.setBackground(pointColor);

		p1.setBackground(mainColor);
		p3.setBackground(Color.green);
		list.setBackground(pointColor);
		p4.setBackground(mainColor);

		popup = new PopupMenu();                        // 접속자목록에서 오른쪽 마우스 클릭시 귓속말,강퇴 기능의 팝업을 띄우기 위한 객체 생성
		menu1 = new MenuItem("귓속말");
		menu2 = new MenuItem("강 퇴");
		popup.add(menu1);                   // 팝업에 메뉴 컴포넌트를 추가
		popup.add(menu2);
		list.add(popup);                    // 리스트 컴포넌트에 팝업 컴포넌트를 추가. 

		//리스트에 팝업 메뉴를 위해 마우스 이벤트 등록
		list.addMouseListener(this);
		list.addItemListener(this);
		popup.addActionListener(this);
        
        
//_________________________________이벤트___________________________________        
        jtf.addActionListener(this);
        jbtn.addActionListener(this);

        //공지다이어로그
        b4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputValue = JOptionPane.showInputDialog("공지를 입력하세요");
				label_test.setText(inputValue);
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

            	
            	try {
					//oos.writeObject(id);			//입장
					oos.writeObject(nickname+" #WELCOME!!!");
					
				} catch (IOException ee) {
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
        jframe.pack();
        jframe.setLocation(
                (screenWidth - jframe.getWidth()) / 2,
                (screenHeight - jframe.getHeight()) / 2);
        jframe.setResizable(false);
        jframe.setVisible(true);
    
    }

    public void actionPerformed(ActionEvent e) {
		
		  Object obj = e.getSource(); 
		  if (obj == b2) { 
			  send(sendmesg);
		} else if (obj == jbtn) { 
			try {
				oos.writeObject(nickname + "#exit"); 
			} catch (IOException ee) {
					  ee.printStackTrace();
			} 
			System.exit(0); 
			
		} else if(obj == b1) {
			try {
				oos.writeObject("<" + nickname + ">님께서 <" + t1.getText() + ">으로 대화명을 변경하셨습니다." + '\n');
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			//-------------------------------------------
		}
			else if(obj == bt1) {
				System.out.println(bt1.getText());
				try {
					oos.writeObject(id + "#" + "voteag");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				votf.dispose();
			}else if(obj == bt2){
				System.out.println(bt2.getText());
				try {
					oos.writeObject(id + "#" + "voteop");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				votf.dispose();
			//---------------------------------------------
			//out.println(notice2);
			out.flush();
			agonick = nickname;
			nickname = t1.getText();

			for(int i=0;i<list.getItemCount();i++);          // 리스트 아이템 개수만큼 루프 수행
			{ 
				if(agonick.equals(list.getItem(i)))           // 변경전 대화명과 리스트내의 아이템을 비교
				{
					list.add(nickname,i);                    // 해당위치에 변경된 대화명을 입력
					list.remove(agonick);                    // 이전 대화명 삭제
				}
			}
			
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
//    	Scanner sc = new Scanner(System.in);
        JFrame.setDefaultLookAndFeelDecorated(true);
        
//        System.out.println("user name input :");
//        String name = sc.next();
//        MultiClient cc = new MultiClient();
//        cc.init();
    }
    
    
    public ObjectInputStream getOis(){
        return ois;
    }
    //------------------------------------------
    public ObjectOutputStream getOos() {
		return oos;
	}
    
    public void append(String s) {
		try {
			Document doc = jtp.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}
    
    public void vote() {
		votf = new JFrame("강퇴투표");
		votf.setBounds(900, 500, 100, 100);;
		bt1 = new JButton("찬성");
		bt2 = new JButton("반대");
		votf.add(bt1,BorderLayout.WEST);
		votf.add(bt2,BorderLayout.EAST);
		votf.setVisible(true);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
	}
    
    public JTextPane getJtp() {
		return jtp;
	}
    //--------------------------------
    public JTextArea getJta(){
        return jta;
    }
    
    //_______________아이디 받아오기위해 추가된 부분 start_________________________
    public void setId(String id) {
		this.id = id;
	}
    
    public String getId(){
        return id;
    }
    //_______________아이디 받아오기위해 추가된 부분 end_________________________

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
			
			if(sendmesg.contains("/clear")){
				this.getJta().setText("  ");
				return;
			}

			if(sendmesg.contains("비속어")) {
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
			}
			
		
				send(sendmesg);
			
			
			  //out.println(sendmesg); 
			  //out.flush();
			 
		}
	}

	//implements로 인해 강제로 추가해야하는 부분 .. >이래서 익명클래스를 쓰는거구나 
	//시간 남으면 익명클래스로 구현해서 이부분을 없애보자!
	
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



	
}
