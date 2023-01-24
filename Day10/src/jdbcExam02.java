
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcExam02 {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver"); //사용등록
		
		String connurl = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "1234";
		
		try (Connection connection = DriverManager.getConnection(connurl, user, password);){	
			Statement stmt = connection.createStatement();			
			
			ResultSet rs = stmt.executeQuery("SELECT * from Test");
			while (rs.next()) {
				
				int num = rs.getInt("num");
				String name = rs.getString("name");
						
				System.out.println(num+"\t"+name);
			}
			
			rs.close();
			stmt.close();
			connection.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("연결실패");
		}
	}
}