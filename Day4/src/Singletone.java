
public class Singletone {
	// st라는 싱글톤 생성
	private static Singletone st = new Singletone();
	
	// 외부에서 호출을 못 하도록 생성자를 private로 설정한다.
	private Singletone() {
		
	}
	
	public static Singletone getst() {
		//st를 반환해준다.
		return st;
	}
	
	public void hello() {
		//Hello 출력
		System.out.println("Hello");
	}
	
}
