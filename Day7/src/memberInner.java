
public class memberInner {

	int a = 10;
	private int b = 20;
	static int c = 30;

	public void output() {
		//local inner class
		class Inner{
			public void disp() {

				//static는 이런 일반 인스턴스를 사용할 수 없다. static는 static만 불러올 수 있다.
				System.out.println(a);
				System.out.println(b);
				System.out.println(c);
			}
		}
		//Inner 출력
		//Inner in = new Inner();
		//in.disp();
	}

	public static void main(String[] args) {
		//memberInner mi = new memberInner();
		//memberInner.Inner in = mi.new Inner();

		//한줄로 작성
		//memberInner.Inner in = new memberInner().new Inner();

		//static로 출력은 new를 빼준다.
		//memberInner.Inner in = new memberInner().Inner();

		//Inner 출력
		//memberInner mi = new memberInner();
		//mi.output();

		//in.disp();
	}
}
