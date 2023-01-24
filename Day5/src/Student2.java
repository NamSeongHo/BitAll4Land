
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Student2 {
	
	//ArrayList 선언
	private ArrayList<String> name; 
	private ArrayList<Integer> kor; 
	private ArrayList<Integer> eng;
	private ArrayList<Integer> math;
	private ArrayList<Integer> total; 
	private ArrayList<Double> avg; 

	private String shName; 
	private String dName; 
	
	int shIndex;
	
	public Student2() {
		
		//ArrayList 선언
		name = new ArrayList<String>();
		kor = new ArrayList<Integer>();
		eng = new ArrayList<Integer>();
		math = new ArrayList<Integer>();
		total = new ArrayList<Integer>();
		avg = new ArrayList<Double>();
	}
	
	// 입력 기능
	public void input(Scanner sc) {
		System.out.println("이름입력");
		name.add(sc.next());

		System.out.println("국어점수");
		kor.add(sc.nextInt());

		System.out.println("영어점수 ");
		eng.add(sc.nextInt());

		System.out.println("수학점수");
		math.add(sc.nextInt());

		//배열의 크기를 위치로 변환해서 -1해줌
		int totals = kor.get(name.size()-1) +  eng.get(name.size()-1) +  math.get(name.size()-1);
		total.add(totals);
		
		//배열의 크기를 위치로 변환하고 평균을 구한다.
		double avgs = (double)total.get(name.size()-1) / 3;
		avg.add(avgs);
	}
	
	//국어점수 비교
	class StudentComparatorKor implements Comparator<Student2>{
		public int compare(Student2 f1, Student2 f2) {
			if(f1.kor.get(shIndex) > f2.kor.get(shIndex)) {
				return 1;
			}
			
			else if(f1.kor.get(shIndex) < f2.kor.get(shIndex)) {
				return -1;
			}
			return 0;
		}
	}
	
	//수학점수 비교
	class StudentComparatorMat implements Comparator<Student2>{
		public int compare(Student2 f1, Student2 f2) {
			if(f1.math.get(shIndex) > f2.math.get(shIndex)) {
				return 1;
			}
			
			else if(f1.math.get(shIndex) < f2.math.get(shIndex)) {
				return -1;
			}
			return 0;
		}
	}
	
	//영어점수 비교
	class StudentComparatorEng implements Comparator<Student2>{
		public int compare(Student2 f1, Student2 f2) {
			if(f1.eng.get(shIndex) > f2.eng.get(shIndex)) {
				return 1;
			}
			
			else if(f1.eng.get(shIndex) < f2.eng.get(shIndex)) {
				return -1;
			}
			return 0;
		}
	}
	
	private ArrayList<Students> studentList = new ArrayList<Students>();
	
	private void searchKor() {
		Collections.sort(studentList, new KorComparator());
		Students(studentList);
	}
	private void searchMath() {
		Collections.sort(studentList, new MathComparator());
		list(studentList);
	}
	private void searchEng() {
		Collections.sort(studentList, new EngComparator());
		list(studentList);
	}
	private void searchAvg() {
		Collections.sort(studentList, new AvgComparator());
		list(studentList);
	}
	
	
	// 찾기 기능
	public void find(Scanner sc) {
		System.out.println("이름입력");
		shName = sc.next();

		//선택한 이름을 shIndex에 저장
		shIndex = name.indexOf(shName); 
		
		System.out.println(name.get(shIndex)+ "국어: " + kor.get(shIndex) + "영어: " + eng.get(shIndex) 
							+ "수학: " + math.get(shIndex) + "총점 : " + total.get(shIndex) 
							+ "평균 : " + avg.get(shIndex));
		
	}
	
	// 삭제 기능
	public void delete(Scanner sc) {
		System.out.println("이름입력");
		dName = sc.next();
		
		//선택한 이름을 shIndex에 저장
		shIndex = name.indexOf(dName); 
		
		//인덱스 삭제
		name.remove(shIndex);
		kor.remove(shIndex);
		eng.remove(shIndex);
		math.remove(shIndex);
		total.remove(shIndex);
		avg.remove(shIndex);
	}
	
	// 출력 기능
	public void output(Scanner sc) {
		for(int i = 0; i < name.size(); i++) {
			System.out.println(name.get(i)+ "국어: " + kor.get(i) + "영어 : " +eng.get(i) 
								+ "수학 : " + math.get(i) + "총점 : " 
								+ total.get(i) + "평균 : " + avg.get(i));
		}
	}
	
	public static void main(String[] args) {
		
		// 반복 여부
		boolean check = true;
		char checked = 'y';
		
		// 기능 번호
		int num;
		
		Student2 s = new Student2();
		
		Scanner sc = new Scanner(System.in);

		// 1. 성적 입력
		do {			
			s.input(sc); // 입력 함수 호출
			
			// 성적 입력 반복 여부
			System.out.println("계속하시겠습니까?(y/n)");
			checked = sc.next().charAt(0);

			if (checked == 'y') {
				check = true;
			} 
			
			else {
				check = false;
			}
		} 
		
		while (check);
		
		// 반복상태 true로 초기화
		check = true;
				
		do {
			System.out.print("1.검색, 2.삭제, 3.출력, 4.종료");
			num = sc.nextInt();
			
			switch(num) {
				case 1: // 검색
					s.find(sc); // 검색 함수 호출
					break;
				
				case 2: // 삭제
					s.delete(sc);
					break;
				
				case 3: // 전체 출력
					s.output(sc);
					break;
				
				case 4:
					check = false;
					System.out.println("종료");
					System.exit(0);
					break;
			}
		} 
		
		while(check);

		sc.close();
	}
}
