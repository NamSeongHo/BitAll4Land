
abstract class Memory {
	
	int[] stack = new int[5];
	int[] queue = new int[5];
	int index;
		
	void push(int num) {
		
		// index가 5보다 클 때 오버플로우
		if(index >= 5) {
			System.out.println("오버플로우 pop을 해주세요");
			
			//오버플로우인 경우 push를 하기 전 index로 변경
			index--;
		}
		//push 진행
		else {
			
			//index에 값 대입
			stack[index] = num;
			
			//index값 증가
			index++;
			
			//현재 stack 출력
			for(int i=0; i<5; i++) {
				System.out.println(stack[i] + "\t");
			}
		}
		System.out.println();
	}
	abstract void pop();
}
