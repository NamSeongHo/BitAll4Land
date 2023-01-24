import java.util.List;

public class Prototype {
	public static void main(String[] args) throws CloneNotSupportedException{
		PrototypeMethod proty = new PrototypeMethod();
		proty.loadData();
		
		PrototypeMethod protyNew1 = (PrototypeMethod) proty.clone();
		PrototypeMethod protyNew2 = (PrototypeMethod) proty.clone();
		
		List<String> list1 = protyNew1.getEmpList();
		list1.add("d");
		List<String> list2 = protyNew2.getEmpList();
		list2.remove("e");
		
		System.out.println("proty List1: " + proty.getEmpList());
		System.out.println("protyNew1 List1: " + list1);
		System.out.println("protyNew1 List1: " + list2);
	}
}
