/*
 * 성적프로그램
 * 
 * 1. 성적입력
 * 2. 검색 (이름)
 * 3. 삭제 (이름)
 * 4. 출력 - 전체출력
 */
import java.util.ArrayList;
import java.util.*;
public class Student {
	
		private ArrayList<String> name;
		private ArrayList<Integer> kor;
		private ArrayList<Integer> eng;
		private ArrayList<Integer> math;
		private ArrayList<Integer> total;
		private ArrayList<Double> avg;
		
		public Student() {
			name = new ArrayList<String>();
			kor = new ArrayList<Integer>();
			eng = new ArrayList<Integer>();
			math = new ArrayList<Integer>();
			total = new ArrayList<Integer>();
			avg = new ArrayList<Double>();
		}

	public static void main(String[] args) {

		int ch;
		int shindex;
		String shname;

		//ArrayList 선언
		/*ArrayList<String> name = new ArrayList<>();
		ArrayList<Integer> kor = new ArrayList<>();
		ArrayList<Integer> eng = new ArrayList<>();
		ArrayList<Integer> math = new ArrayList<>();
		ArrayList<Integer> total = new ArrayList<>();
		ArrayList<Double> avg = new ArrayList<>();*/
		
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("1.입력 2.검색 3.삭제 4.출력 5.종료");

			//선택한 번호를 ch에 저장
			ch = sc.nextInt();

			// 5번을 선택한다면
			if(ch==5) {

				//프로그램 종료
				break;
			}

			//1번을 선택한다면
			if(ch==1) {
				System.out.println("이름입력");

				//입력한 이름 저장
				name.add(sc.next());

				System.out.println("점수입력");

				//입력한 점수 저장
				kor.add(sc.nextInt());
				eng.add(sc.nextInt());
				math.add(sc.nextInt());

				//배열의 크기를 위치로 변환해서 -1해줌
				total.add(kor.get(kor.size()-1) + eng.get(eng.size()-1) + 
						math.get(math.size()-1));

				//배열의 크기를 위치로 변환하고 평균을 구한다.
				avg.add(total.get(total.size()-1)/3.0);
			}

			//2번을 선택한다면
			else if(ch==2) {
				System.out.println("이름입력");
				shname = sc.next();

				//선택한 이름을 shindex에 저장
				shindex=name.indexOf(shname);

				//정보 출력
				System.out.println("이름 : " + name.get(shindex) + "  " + "점수 : " + kor.get(shindex) + "  " 
						+ eng.get(shindex) + "  " + math.get(shindex) + "  " + "총합 : " + total.get(shindex) + "  " 
						+ "평균 : " + avg.get(shindex));
			}

			//3번을 선택한다면
			else if(ch==3) {
				System.out.println("이름입력");
				shname = sc.next();

				//선택한 이름을 shindex에 저장
				shindex = name.indexOf(shname);

				//인덱스 삭제
				name.remove(shindex);
				kor.remove(shindex);
				eng.remove(shindex);
				math.remove(shindex);
			}

			//4번을 선택한다면
			else if(ch==4) {
				//이름의 개수를 num에 저장
				int num = name.size();

				//num만큼 출력
				for(int i=0; i<num; i++) {
					System.out.println("이름 : " + name.get(i) + "  " + "점수 : " + kor.get(i) + "  " 
							+ eng.get(i) + "  " + math.get(i) + "  " + "총합 : " + total.get(i) + "  "
							+ "평균 : " + avg.get(i));
				}
			}

			//1~5 이외의 문자를 입력한 경우
			else {
				System.out.println("잘못된 입력");
			}
		}
		while(ch!=5);
		System.out.println("종료");
	}
}
