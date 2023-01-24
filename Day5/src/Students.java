import java.util.Comparator;
public class Students {
	public Students(String name, int kor, int math, int eng) {
		this.name = name;
		this.kor = kor;
		this.math = math;
		this.eng = eng;
		
		this.sum = kor + math + eng;
		this.avg = kor + math + eng / (float) 3;
	}

	@Override
	public String toString() {
		return " 이름 : " + name + " 국어 : " + kor + " 수학 : " + math + " 영어 : " + eng + " 총점 : " + getSum() + " 평균 : "
				+ getAvg();
	}

	private String name;
	private int kor, math, eng;

	private int sum;
	private float avg;

	public int getSum() {
		return sum;
	}

	public float getAvg() {
		return avg;
	}

	public String getName() {
		return name;
	}

	public int getKor() {
		return kor;
	}

	public int getMath() {
		return math;
	}

	public int getEng() {
		return eng;
	}
}

class KorComparator implements Comparator<Students>
{
    @Override
    public int compare(Students s1, Students s2)
    {
        if (s1.getKor() > s2.getKor()) {
            return -1;
        }else if (s1.getKor() < s2.getKor()) {
			return 1;
		}return 0;
        
    }
}
class MathComparator implements Comparator<Students>
{
	@Override
	public int compare(Students s1, Students s2)
	{
		if (s1.getMath() > s2.getMath()) {
			return -1;
		}else if (s1.getMath() < s2.getMath()) {
			return 1;
		}return 0;
		
	}
}
class EngComparator implements Comparator<Students>
{
	@Override
	public int compare(Students s1, Students s2)
	{
		if (s1.getEng() > s2.getEng()) {
			return -1;
		}else if (s1.getEng() < s2.getEng()) {
			return 1;
		}return 0;
		
	}
}
class AvgComparator implements Comparator<Students>
{
	@Override
	public int compare(Students s1, Students s2)
	{
		if (s1.getAvg() > s2.getAvg()) {
			return -1;
		}else if (s1.getAvg() < s2.getAvg()) {
			return 1;
		}return 0;
		
	}
}
