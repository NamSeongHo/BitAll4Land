import java.awt.*;
public class ButtonEx {
	public static void main(String[] args) {
		Frame f = new Frame("Test");

		Panel p = new Panel();

		Button b1 = new Button();
		Button b2 = new Button("button1");
		Button b3 = new Button("button2");
		Button b4 = new Button("button3");

		b1.setLabel("button4");

		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);

		f.add(p);

		f.setLocation(300,300);
		f.setSize(30, 100);
		f.setVisible(true);
	}
}
