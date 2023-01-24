
public class ExceptionTest {
	//throws ArrayIndexOutOfBoundsException 를 선언하면 try catch를 사용하지 않고 예외처리 가능
	public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
		int [] arr = {1,2,3,4};

		for(int i=0; i<5; i++) {
			try {// 예외가 발생할 것 같은 예상이 되는 환경
				//여기 안에서 끝 예외처리 try catch가 있어야 End가 출력됨
				System.out.println(arr[i]);
				//위에 코드가 끝나면 catch로 가서 마지막 Test4는 출력이 안됨
				//System.out.println("Test"+i);
				//catch안에 오류이름 넣음
			}catch(ArrayIndexOutOfBoundsException ae) { //예외 처리기
				System.out.println("Exception");

			}//catch() {
			//예상할 수 있는것 ()에 추가 예상할 수 있는 오류를 쓰고 나서 
			//마지막에 모든 오류예외처리 catch (exception)사용
			//}
			//예외와 관계없이 무조건 실행
			finally {
				System.out.println("Test"+i);
			}
		}	
		System.out.println("End");
	}
}
