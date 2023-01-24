
interface TestInner{
	int data = 1000;
	public void print();
}

public class AnonyInner {

	public void test() {
		
		
		
		new TestInner() { // anonymous inner class

			@Override
			public void print() {
				// TODO Auto-generated method stub
				System.out.println(data);
			}
			
		}.print();;

	}

	public static void main(String[] args) {
		AnonyInner ti = new AnonyInner();
		ti.test();
	}
}
