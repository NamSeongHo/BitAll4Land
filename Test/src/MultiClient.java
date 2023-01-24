import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.*;

public class MultiClient implements ActionListener {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JFrame jframe;
	private JTextField jtf;
	private JTextArea jta, jta2;
	private JLabel jlb1, jlb2;
	private JPanel jp1, jp2;
	private String ip;
	private String id;
	private JButton jbtn;

	public MultiClient(String argId) {
		ip = "127.0.0.1";
		id = argId;
		jframe = new JFrame("Multi Chatting");
		jtf = new JTextField(30);
		jta = new JTextArea("", 10, 50);
		jlb1 = new JLabel("Usage ID : [[ " + id + "]]");
		jlb2 = new JLabel("IP : " + ip);
		jbtn = new JButton("종료");
		jp1 = new JPanel();
		jp2 = new JPanel();

		jlb1.setBackground(Color.yellow);
		jlb2.setBackground(Color.green);
		jta.setBackground(Color.pink);
		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new BorderLayout());

		jp1.add(jbtn, BorderLayout.EAST);
		jp1.add(jtf, BorderLayout.CENTER);
		jp2.add(jlb1, BorderLayout.CENTER);
		jp2.add(jlb2, BorderLayout.EAST);

		jframe.getContentPane().add(jp1, BorderLayout.SOUTH);
		jframe.getContentPane().add(jp2, BorderLayout.NORTH);
		JScrollPane jsp = new JScrollPane(jta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jframe.getContentPane().add(jsp, BorderLayout.CENTER);

		jtf.addActionListener(this);
		jbtn.addActionListener(this);

		jframe.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				try {
					//oos.writeObject(id);			// Hash에서 id를 빼는 과정 = 퇴장
					oos.writeObject(id+"#exit");

				} catch (IOException ee) {
					ee.printStackTrace();
				}
				//
				try {
				oos.writeObject(id);	
				jta2 = new JTextArea();
				 jsp.setRowHeaderView(jta2);
				 jta2.setColumns(10);
				}catch (IOException ee) {
					ee.printStackTrace();
				}
				
				System.exit(0);
			}
//---------------------------------------------------------------
			public void windowOpened(WindowEvent e) {
				try {
					//oos.writeObject(id);			//입장
					oos.writeObject(id+"#enter");
					
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				
				//
				try {
					oos.writeObject(id);	
					jta2 = new JTextArea();
					 jsp.setRowHeaderView(jta2);
					 jta2.setColumns(10);
					}catch (IOException ee) {
						ee.printStackTrace();
					}
				
				
				jtf.requestFocus();
			}
//------------------------------------------------------------------
		});
		jta.setEditable(false);

		//채팅 하는 사람 목록 기능--------------------------------------------
		 //jta2 = new JTextArea();
		 //jsp.setRowHeaderView(jta2);
		 //jta2.setColumns(10);
		//textField.setText("접속자 목록");
		//textField.setText(id);
		//-------------------------------------------------------------


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
		String msg = jtf.getText();
		if (obj == jtf) {
			if (msg == null || msg.length()==0) {
				JOptionPane.showMessageDialog(jframe, 
						"글을쓰세요", "경고",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					//채팅입력시 현재시간 같이 찍히는 기능---------------------------
					Calendar calendar = Calendar.getInstance();
					java.util.Date date = calendar.getTime();
					String today = (new SimpleDateFormat("H:mm:ss").format(date));

					oos.writeObject(today+" "+id+"#"+msg);
					//-----------------------------------------------------
					//있으면 2번 출력
					//oos.writeObject(id+"#"+msg);
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				jtf.setText("");
			}
		} else if (obj == jbtn) {
			try {
				//oos.writeObject(id);				// Hash에서 id를 빼는 과정 = 퇴장
				oos.writeObject(id+"#exit");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			System.exit(0);
		}
	}
	public void exit(){
		System.exit(0);
	}
	public void init() throws IOException {
		socket = new Socket(ip, 5001);
		System.out.println("connected...");
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		MultiClientThread ct = new MultiClientThread(this);
		Thread t = new Thread(ct);
		t.start();
		
		/* try {
			//oos.writeObject(id);			//입장
			oos.writeObject(id+"#enter");
			
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		
		try {
			oos.writeObject(id);	
			jta2 = new JTextArea();
			 jsp.setRowHeaderView(jta2);
			 jta2.setColumns(10);
			}catch (IOException ee) {
				ee.printStackTrace();
			}*/
		
	}

	public static void main(String args[]) throws IOException {

		Scanner sc = new Scanner(System.in);
		JFrame.setDefaultLookAndFeelDecorated(true);

		System.out.println("user name input :");
		String name = sc.next();

		//2. 클라이언트 스레드 생성
		MultiClient cc = new MultiClient(name);

		// 3. Client의 name을 Server로 보냄
		cc.oos.writeObject(name);   // Hash에 name을 넣는 과정


		cc.init();
	}
	public ObjectInputStream getOis(){
		return ois;
	}
	public JTextArea getJta(){
		return jta;
	}

	public JTextArea getJta2(){
		return jta2;
	}

	//아이디 받아오기--------------------------------------------------
	public void setId(String id) {
		this.id = id;
	}

	public String getId(){
		return id;
	}
	//------------------------------------------------------------


}
