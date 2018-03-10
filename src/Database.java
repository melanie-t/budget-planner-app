import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;


public class Database 
{
	/////////////////////////////////////////////////////////////////
	// --- Members --------------------------------------------------
	private String m_driver = ""; // Driver should always be the same
	private String m_dbName = "";
	private Connection m_connection = null;
	// ______________________________________________________________
	
	
	/////////////////////////////////////////////////////////////////
	// --- Constructors ---------------------------------------------	
	public Database(String dbName)
	{
		this("org.sqlite.JDBC", dbName);
	}
	
	// In case we want to use a different driver, should never be the case though
	public Database(String driver, String dbName)
	{
		m_driver = driver;
		m_dbName = dbName;
		
		try 
		{
			Class.forName(m_driver);
			m_connection = DriverManager.getConnection("jdbc:sqlite:" + m_dbName);
		}
		catch (Exception e) 
		{ 
			System.err.println(e.getMessage()); 
		}
	}
	// ______________________________________________________________
	
	
	/////////////////////////////////////////////////////////////////
	// --- Getters --------------------------------------------------
	public Connection getConnection()
	{
		return m_connection;
	}
	// ______________________________________________________________
	

	/////////////////////////////////////////////////////////////////
	// --- Setters --------------------------------------------------
	// This is the "lowest" we go in terms of abstraction for our db, this is where are modifications one the db are done
	// Comment: suggest rename executeSQL(String sqlString) - Jordan
	public void updateSQL(String sqlString)
	{
		// PreparedStatement not supported yet, not sure we need them anyway with the SQLStringFactory
		try 
		{ 
			Statement statement = m_connection.createStatement();
			statement.executeUpdate(sqlString);
			statement.close();
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		} 

	}
	// ______________________________________________________________
	


	
	/////////////////////////////////////////////////////////////////
	// --- CleanUp -------------------------------------------------
	public void shutdown()
	{
		try
		{
			if (m_connection != null && !m_connection.isClosed())
			{
				m_connection.close();
			}			
		}
		
		catch (Exception e) 
		{ 
			System.err.println(e.getMessage()); 
		}
	}
	// _____________________________________________________________
}
