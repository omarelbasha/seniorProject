import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class jdbc {
private  Connection con;
private ResultSet rs;
private int a;
public Connection connect() {
	try {
	
		con=DriverManager.getConnection("jdbc:mysql://localhost/sworddatabase", "root", "");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return con;
	
}
public ResultSet query(Connection con,String sql) {
	
	try {
		Statement stmt=con.createStatement();
		rs=stmt.executeQuery(sql);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return rs;
	
	
	
}
public void insert(Connection con,String sql) {
	
	try {
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

public void disconnect(Connection con) {
	try {
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	public static void main(String[] args) {
		

	}

}
