import java.util.ArrayList;
import java.util.List;

public class PrototypeMethod implements Cloneable{
	private List<String> empList;
	
	public PrototypeMethod() {
		empList = new ArrayList<String>();
	}
	
	public PrototypeMethod(List<String> list) {
		this.empList=list;
	}
	
	public void loadData() {
		empList.add("a");
		empList.add("b");
		empList.add("c");
	}
	
	public List<String> getEmpList(){
		return empList;
	}
	
	public Object clone() throws CloneNotSupportedException{
		List<String> temp = new ArrayList<String>();
		for(String s : this.empList) {
			temp.add(s);
		}
		return new PrototypeMethod(temp);
	}
}
