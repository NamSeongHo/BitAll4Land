public class SingleRunnableEx implements Runnable{
	private int[] temp;
	public SingleRunnableEx() {
		temp = new int[10];
		for(int start=0; start<temp.length;start++) {
			temp[start]=start;
		}
	}
	public void run() {
		for(int start: temp) { 
			try{
				Thread.sleep(1000);
			}catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			System.out.printf("쓰래드이름 : %s ,",Thread.currentThread().getName());
			System.out.printf("temp value : %d %n",start);
		}

	}
	public static void main(String[] args) {
		SingleRunnableEx ct = new SingleRunnableEx();
		Thread t = new Thread(ct, "superman");
		t.start();
	}
}
