import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ViewFactory 
{
	private Database m_database = null;
	
	public ViewFactory(Database database)
	{
		m_database = database;
	}
	
	public View createView(String sqlString)
	{
		try 
		{ 
			PreparedStatement statement = m_database.getConnection().prepareStatement(sqlString);
			View view = new View(statement, m_database);
			m_database.attach(view);
			return view;
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		}
		
		return null;
	}
}
