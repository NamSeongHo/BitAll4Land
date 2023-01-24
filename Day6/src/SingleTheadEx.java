public class SingleTheadEx extends Thread{
	private int[] temp;
	public SingleTheadEx(String threadname) {
		super(threadname);
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
			System.out.printf("쓰래드이름 : %s ,",currentThread().getName());
			System.out.printf("temp value : %d %n",start);
		}

	}
	public static void main(String[] args) {
		SingleTheadEx st = new SingleTheadEx("superman");
		st.start();
	}
}
