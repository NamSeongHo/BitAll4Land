
class A{
	private int a;

	public A(int a) {
		this.a = a;
	}
	public int getA() {
		return a;
	}
}

class B extends A{
	private int b;

	public B(int a, int b) {
		super(a);
		this.b = b;
	}
	public int getB() {
		return b;
	}
}

class C extends B{
	private int c;

	public C(int a, int b, int c) {
		super(a,b);
		this.c = c;
	}
	
		public int getC() {
		return c;
	}
}

class D extends C{
	private int d;

	public D(int a, int b, int c, int d) {
		super(a, b, c);
		this.d = d;
	}
	
	public int getD() {
		return d;
	}
}

public class SuperExam extends D{
	
	public SuperExam(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	public static void main(String[] args) {
		
		SuperExam se = new SuperExam(1,2,3,4);

		System.out.println(se.getA()); // 1 A클래스의 getA
		System.out.println(se.getB()); // 2 B클래스의 getB
		System.out.println(se.getC()); // 3 C클래스의 getC
		System.out.println(se.getD()); // 4 D클래스의 getD
	}
}
