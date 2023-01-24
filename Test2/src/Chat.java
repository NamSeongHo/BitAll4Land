
// 사용자 인터페이스를 생성,그래픽과 이미지 처리에 필요한 클래스를 포함한 API를 import해준다.
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.TextField;
// AWT 컴포넌트가 발생시키는 다양한 이벤트를 처리하는데 필요한 요소들을 포함하는 API를 import해준다.
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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/** ServerSocket과 Socket 클래스를 가지고 TCP베이스의 소켓을 구성하는 클래스 */
public class Chat extends Frame implements ActionListener,KeyListener,Runnable,ItemListener,MouseListener
{
	TextField t1;			// 대화명 변경을 입력하기 위한 텍스트 필드 객체 선언
	TextField t2;			// 접속자수를 보여주는 텍스트 필드 객체 선언
	TextArea t3;            // 대화내용이 입력되는 텍스트에어리어 객체 선언(서버로부터 전달된 메시지 출력 컴포넌트)
	TextField t4;			// 대화내용을 입력하는 텍스트필드 객체 선언(사용자가 입력하는 메시지 입력 컴포넌트)
	String ip;              
	String nickname;
	String port;
	String agonick;			// 변경 전 대화명을 저장

	String sendmesg;
	String notice1;			// 대화중 각종 변경사항을 담고 있는 스트링 객체 선언					
	String notice2;
	String notice3;
	String notice4;

	String secret = "";     // 귓속말
	String listSelectName;          // 리스트 박스에서 선택된 아이템의 내용을 저장하는 객체
	Label label3;                  

	//팝업 메뉴
	PopupMenu popup;
	MenuItem menu1;
	MenuItem menu2;

	int state;
	int i;
	Socket socket;                     //  Socket 객체 선언
	PrintWriter out;					// 서버로의 출력 객체
	BufferedReader in;                  // 서버로부터의 입력 객체
	Thread t;
	List list;

	/** 채팅창을 구현하는 AWT구현 함수 */
	public Chat() 
	{

		Panel p1 = new Panel();        // 4개의 패널 객체 생성

		Panel p2 = new Panel();
		Panel p3 = new Panel();
		Panel p4 = new Panel();

		setLayout(new BorderLayout());       // 프레임 레이아웃을 BorderLayout으로 설정

		p1.setLayout(new FlowLayout());        // 각 패널의 레이아웃을 설정
		p2.setLayout(new BorderLayout());
		p3.setLayout(new BorderLayout());
		p4.setLayout(new FlowLayout());

		t1 = new TextField(15);
		t2 = new TextField(5);
		t3 = new TextArea(" ",5,20);  
		t4 = new TextField(60);
		Label label1 = new Label("대화명 : ");
		Label label2 = new Label("현재 접속자수 : ");
		label3 = new Label("[전체에게]");
		list = new List(15);
		Button b1 = new Button("대화명 변경");
		Button b2 = new Button("보내기");
		Button b3 = new Button("로그아웃");
		Button b4 = new Button("공지 등록 하기");



		b1.addActionListener(this);                    // 컴포넌트 이벤트를 처리할 컴포넌트 리스너를 추가.
		b2.addActionListener(this);                   
		b3.addActionListener(this);
		t4.addKeyListener(this);


		p1.add(label1);                              // 패널에 각 컴포넌트를 붙인다.
		p1.add(t1);
		p1.add(b1);
		p1.add(label2);
		p1.add(t2);
		p2.add(t3);
		p3.add(list);
		p4.add(label3);
		p4.add(t4);
		p4.add(b2);
		p4.add(b3);
		p1.add(b4);

		add(BorderLayout.NORTH,p1);                  // 패널을 프레임의 각 위체에 붙인다.

		add(BorderLayout.CENTER,p2);
		add(BorderLayout.EAST,p3);
		add(BorderLayout.SOUTH,p4);

		Color mainColor = new Color(180, 195, 220);
		Color pointColor = new Color(240, 230, 230);

		b1.setBackground(pointColor);              // 버튼 및 패널 배경 색깔 지정
		b2.setBackground(pointColor);
		b3.setBackground(pointColor);
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



		// WindowEvent에 대한 리스너 등록		
		this.addWindowListener(new WindowAdapter(){                  //창닫기 기능

			public void windowClosing(WindowEvent e){
				notice3 = "<" + nickname + ">님께서 로그아웃 하셨습니다.\n";
				out.println(notice3);
				out.flush();
				closeStreams();
				System.exit(0);

			}
		});

		t = new Thread(this);          // 입력 스레드 시작
	}


	public static void main(String args[])
	{
		Chat f = new Chat();
		f.setTitle("채팅 UI");
		f.setSize(700,600);
		f.setVisible(true);
	}
/** 키가 눌러졌을때 발생하는 이벤트  */
	public void keyPressed(KeyEvent e) {                                     
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String today = (new SimpleDateFormat("H:mm:ss").format(date));

			sendmesg = secret + "<" + nickname + "> : " + t4.getText() +"["+ today + "]" + '\n';
			t4.setText("");

			if(sendmesg.contains("비속어")) {
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
			}
			out.println(sendmesg);
			out.flush();
		}
	}
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }

	/** 클라이언트 사용자로부터 메시지를 입력받아 서버로 전송 */
	public void actionPerformed(ActionEvent e) 
	{      // 액션이 발생할때 호출되는 메소드

		String str =String.valueOf(e.getActionCommand());    // 이벤트가 발생한 버튼의 명령을 문자로 변환
		Login l = new Login();
		if((str=="대화명 변경")) {   
			notice2 = "<" + nickname + ">님께서 <" + t1.getText() + ">으로 대화명을 변경하셨습니다." + '\n';	
			out.println(notice2);
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
		}else if((str=="보내기")) { 
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = calendar.getTime();
			String today = (new SimpleDateFormat("H:mm:ss").format(date));
			sendmesg = secret + "<" + nickname + "> : " + t4.getText() +"["+ today + "]" + '\n';
			t4.setText("");

			if(sendmesg.contains("비속어")) {
				sendmesg = nickname + "님이 비속어를 사용하였습니다.";
			}

			out.println(sendmesg);
			out.flush();
		}else if((str=="로그아웃")) {
			notice3 = "<" + nickname + ">님께서 로그아웃 하셨습니다.\n";
			out.println(notice3);
			out.flush();
			setVisible(false);
			l.setSize(300,200);
			l.setVisible(true);
			closeStreams();
		}
		else if (str.equals("귓속말"))
		{
			label3.setText("[귓속말]");
			menu1.setLabel("[전체에게]");
			secret = "***귓속말" + listSelectName +" : ";

		} else if (str.equals("강  퇴"))
		{
			out.println("***강퇴" + listSelectName + " : " + nickname);
			out.flush();
		} else if (str.equals("[전체에게]"))
		{
			label3.setText("[전체에게]");
			menu1.setLabel("[귓속말]");
			secret = "";
		}
	}

	/**마우스 클릭 이벤트를 처리하는 메소드 */
	public void mouseClicked(MouseEvent e) {
		// 마우스에서 가운데버튼, 오른쪽 버튼을 클릭했을때..
		if ((e.getButton() == MouseEvent.BUTTON2) || (e.getButton() == MouseEvent.BUTTON3))
		{
			popup.show(list, e.getX(), e.getY());
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	/**list Item 이벤트, 선택한 값을 저장한다. */
	public void itemStateChanged(ItemEvent e) {
		int state = Integer.parseInt(e.getItem().toString());
		listSelectName = list.getItem(state);
	}

	public void print(String nickname,String ip,String port)
	{
		try
		{
			socket = new Socket(ip, Integer.parseInt(port));
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println("===" + nickname);                    // 대화명에 ===문자열을 붙여 추출가능,식별가능 문자열 생성해서 버퍼에 출력
			out.flush();                                      // 버퍼를 비운다.서버로 전송...
		}
		catch(Exception e) {
			System.err.println(e);
		}

		t.start();

		t1.setText(nickname);
		notice1 = "<" + nickname + ">님께서 입장하셨습니다.\n";
		out.println(notice1);
		out.flush();
	}
	/** 서버에서 전송된 메시지를 읽고 TextArea에 출력하는 기능(무한 루프) */
	public void run() {
		try
		{
			while(true)
			{
				String s = in.readLine();
				if(s == null) 
				{
					t3.append("\nConnection closed.");
					return;
				}
				else {
					if (s.startsWith("==="))
					{
						t3.append("\n"+s);

						//접속한 명단 추가
						if (s.indexOf(" Connected") != -1)
						{
							list.add(s.substring(4, s.indexOf(" Connected")));
							t2.setText(String.valueOf(list.getItemCount()));                // 현재접속자수를 리스트로부터 얻어와서 텍스트 필드에 뿌려준다.
						}
						//접속이 끊긴 명단자 삭제
						else if (s.indexOf(" Disconnected") != -1)
						{
							list.remove(s.substring(4, s.indexOf(" Disconnected")));
							t2.setText(String.valueOf(list.getItemCount())); // 현재접속자수를 리스트로부터 얻어와서 텍스트 필드에 뿌려준다.
						}
					} else if (s.startsWith("***귓속말")) //귓속말
					{
						//받는 사람 중 자신의 대화명을 찾는다.
						if(nickname.equals(s.substring(6, s.indexOf(" : "))))
							t3.append("\n귓속말 : "+s.substring(s.indexOf(" : ")+3, s.length()));
						//보내는 사람 중 자신의 대화명을 찾는다.
						else if (nickname.equals(s.substring(s.indexOf("<")+1, s.indexOf(">"))))
							t3.append("\n귓속말 : "+s.substring(s.indexOf(" : ")+3, s.length()));

					} else if (s.startsWith("***강퇴"))
					{
						//자신의 대화명이 맞으면 while문을 빠져 나간다.
						if(nickname.equals(s.substring(5, s.indexOf(" : "))))
						{
							notice4 = "\n" + s.substring(s.indexOf(" : ")+3, s.length()) + "님께서 " + nickname + "님을 강퇴시켰습니다.";
							t3.append(notice4);

							list.removeAll();
							t2.setText("");
							t1.setText("넌 강퇴야!!");
							break;
						}
					} else {
						t3.append("\n"+s);
					}
				}
			}

		}catch(Exception e) {
			t3.append("\n" + e);
		}
		closeStreams();
	}

	/** 소켓 스트림을 닫는 메소드 */
	public void closeStreams(){				
		try{
			out.close();
			in.close();
		}
		catch(Exception e){}
	}
}