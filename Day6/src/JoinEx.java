
class MyRunnableTwo implements Runnable{
	public void run() {
		System.out.println("run");
		first();
	}
	public void first() {
		System.out.println("first");
		second();
	}
	public void second() {
		System.out.println("second");
	}
}
public class JoinEx {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName()+" start");
		Runnable r = new MyRunnableTwo();
		Thread mThread = new Thread(r);
		mThread.start();
		
		//스래드는 별개로 존재
		
		//join을 사용해 main실행 중간에종료하지 않고 쓰래드를 실행
		/*try {
			mThread.join();
		}catch (InterruptedException ie) {
			ie.printStackTrace();
		}*/
		System.out.println(Thread.currentThread().getName()+" end");
	}
}
