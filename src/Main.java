import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		Class.forName("org.sqlite.JDBC");
		
		QueryWrapper wrapper = QueryWrapper.getInstance();
		View view = new View();
		
		wrapper.deleteTable("test");
		wrapper.createTable("test", "ids", "INTEGER");
		wrapper.addColumn("test", "Name", "VARCHAR");
		wrapper.addColumn("test", "Number", "INTEGER");

		wrapper.addEntry("test", "1", "Joe", "10");
		wrapper.addEntry("test", "2", "Jane", "5");
		
		wrapper.showAll("test", view);
		
	
		System.out.print(view.toString());
		

		try 
		{ 
			wrapper.shutdown();
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
		}
    }
}
