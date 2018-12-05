/// ****Authors: Wilfredo Hernandez Escobar & Thomas Gutierrez***///
import java.sql.*;
import javax.swing.*;

public class sqlConnector {
	
	
	
	public static Connection dbConnector()
	{
		Connection conn = null;
		try {
				String url = "jdbc:sqlite:EmployeeDB.db";
				conn = DriverManager.getConnection(url);
				//JOptionPane.showMessageDialog(null, "Connection Successsful");
				return conn;
				
		}	
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

}
}
