
public class ThreadEx1 {
	public static void main(String[] args) {
		ThreadEx1_1 t1 = new ThreadEx1_1();

		//
		Runnable r = new ThreadEx1_2();

		//생성자 Thread(Runnable target)
		Thread t2 = new Thread(r);

		t1.start();
		t2.start();
	}
}

//상속 하나만
class ThreadEx1_1 extends Thread{
	public void run() {
		for(int i=0; i<5; i++) {
			//조상인 Thread의 getName()을 호출
			//super.getName()과 같다.
			System.out.println(getName());
		}
	}
}

//인터페이스 상속여러개 됨
class ThreadEx1_2 implements Runnable{
	
	public void run() {
		for(int i=0; i<5; i++) {
			//Thread.currentThread() - 현재 실행중인 Thread를 반환한다.
			System.out.println(Thread.currentThread().getName());
		}
	}
}