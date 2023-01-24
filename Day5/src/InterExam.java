/* 인터페이스
 * 1. 추상클래스를 더 추상화 시킨 개념.
 * 2. 다중상속을 대체할 수 있게 사용.
 * 
 * 3. 구성요소 : 상수 + 추상메소드
 * 4. 식당의 메뉴판.
 * 5. 다중상속이 가능하다.
 * 6. 강제성
 * 7. 동적바인딩 가능
 * 
 * interface 이름{
 * 상수
 * 추상메소드
 * }
 * 
 */

interface Aaa{
	final static int a = 100; // final static 생략
	abstract void disp1(); // abstract 생략
}

interface Bbb{
	final static int a = 100; // final static 생략
	abstract void disp2(); // abstract 생략
}

interface Acc extends Aaa, Bbb{
	
}

public class InterExam implements Acc{

	public static void main(String[] args) {
		Acc aaa = new InterExam();// 동적바인딩.
		aaa.disp1();
		aaa.disp2();
		
	}

	@Override
	public void disp1() {
		// TODO Auto-generated method stub
		System.out.println("Aaa");
	}

	@Override
	public void disp2() {
		// TODO Auto-generated method stub

	}
}
