
class ATM implements Runnable {
	private long depositeMoney = 10000;
	public void run() {
		synchronized (this) {
			for(int i=0; i<10; i++) {
				//예외처리 try안에서 문제가 생기면 catch로 간다
				try {
					//son, mother 바뀌고 시작
					notify();
					//1초에 한번씩 실행 단위 ms
					Thread.sleep(1000);
					//만약 getDepositeMoney()가 0이라면 break;
					if(getDepositeMoney() <= 0) {
						//getDepositeMoney()이 0이라면 출력하고 break;
						System.out.print(Thread.currentThread().getName()+ " , ");
						System.out.println("out.");
						break;
					}
					//withDraw에 1000을 대입해서 실행
					withDraw(1000);    
					//정지 son이 시작된다
					wait();
				} catch (InterruptedException e) {
					//예외처리 오류 문장 보여줌
					e.printStackTrace();
				}
				
			}
		}
	}
	public void withDraw(long howMuch) {
	//	if(getDepositeMoney() > 0) {
			//depositeMoney에서 howMuch를 빼준다
			depositeMoney -= howMuch;
			System.out.print(Thread.currentThread().getName() + " , ");
			System.out.printf("money: %,d %n", getDepositeMoney());
		//} else {
			//System.out.print(Thread.currentThread().getName()+ " , ");
			//System.out.println("out.");
		//}
	}
	public long getDepositeMoney() {
		return depositeMoney;
	}
}

public class SynchronizedEx {
	public static void main(String[] args) {
		ATM atm = new ATM();
		
		Thread mother = new Thread(atm, "mother");
		Thread son = new Thread(atm,"son");
		
		mother.start();
		son.start();
	}
}
