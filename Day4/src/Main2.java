import java.util.Scanner;

public class Main2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//stack queue를 선택
		String stackqueue;
		
		//push pop를 선택
		int pushpop;
		MyStack mystack = new MyStack();
		MyQueue myqueue = new MyQueue();
		
		do {
			System.out.println("Stack은 S, Queue는 Q를 Exit는 E를 입력");
			stackqueue = sc.next();
			
			//stack를 선택한 경우
			if(stackqueue.equals("S")||stackqueue.equals("s")) {
				System.out.println("push는 1, pop는 2를 입력");
				pushpop = sc.nextInt();
				
				//push를 선택한 경우
				if(pushpop==1) {
					System.out.println("숫자입력");
					mystack.push(sc.nextInt());
				}
				
				//pop을 선택한 경우
				else if(pushpop==2) {
					mystack.pop();
				}
				
				//잘못 입력한 경우
				else {
					System.out.println("잘못된 입력");
				}
			}
			
			//queue를 선택한 경우
			else if(stackqueue.equals("Q")||stackqueue.equals("q")) {
				System.out.println("push는 1, pop는 2를 입력");
				pushpop = sc.nextInt();
				
				//push를 선택한 경우
				if(pushpop==1) {
					System.out.println("숫자입력");
					mystack.push(sc.nextInt());
				}
				
				//pop을 선택한 경우
				else if(pushpop==2) {
					mystack.pop();
				}
				
				//잘못 입력한 경우
				else {
					System.out.println("잘못된 입력");
				}
			}
				
				//종료를 선택
				else if(stackqueue.equals("E")||stackqueue.equals("e")) {
					System.out.println("프로그램 종료");
				}
				
				//잘못 입력한 경우
				else {
					System.out.println("잘못된 입력");
			}
		}
		while(!(stackqueue.equals("E")||stackqueue.equals("e")));	
	}
}
