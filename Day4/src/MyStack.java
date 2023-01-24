
public class MyStack extends Memory{
		
	void pop() {
		
		//index가 0보다 작을 때 언더플로우
		if(index <=0 ) {
			System.out.println("언더플로우 push를 해주세요");
			
			//언더플로우인 경우 pop를 하기 전 index로 변경
			index++;
		}
		
		//index값 감소
		index--;
		
		//pop된 index갑을 0으로 변경한다
		stack[index]=0;
		
		for(int i=0; i<5; i++) {
			System.out.println(stack[i] + "\t");
		}
		System.out.println();
	}
}
