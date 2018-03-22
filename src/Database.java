import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Database class uses the SQLite.JDBC library to serialize the repositories between user sessions.
 */
public class Database 
{
	private String m_driver; // Driver should always be the same
	private String m_dbName;
	private Connection m_connection ;

	/**
	 * Constructor.
	 * This will construct a Database instance using the org.sqlite.JDBC driver
	 * @param dbName name of the database that this class will connect to
	 */
	public Database(String dbName)
	{
		this("org.sqlite.JDBC", dbName);
	}

	/**
	 * Constructor.
	 * Allows to specify a different driver.
	 * @param driver driver for the database
	 * @param dbName name of the database that this class will connect to
	 */
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

	/**
	 * Execute a pull query on the database.
	 * @param sqlString SQL string query
	 * @return result of the query
	 */
	public ResultSet fetchSQL(String sqlString) {
		try 
		{ 
			PreparedStatement st = m_connection.prepareStatement(sqlString);
		    return st.executeQuery();
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		} 
		return null;
	}

	/**
	 * Execute a push query on the database. Should only insert one entry at a time.
	 * @param sqlString SQL query string
	 * @return generated id of the entry
	 */
	public Integer updateSQL(String sqlString)
	{
		int newId = 0;
		// PreparedStatement not supported yet, not sure we need them anyway with the SQLStringFactory
		try 
		{ 
			Statement statement = m_connection.createStatement();
			statement.executeUpdate(sqlString);
			
			//Get the auto-inc id of row if any 
			ResultSet keyset = statement.getGeneratedKeys();
			while (keyset.next()) {
				newId = keyset.getInt("last_insert_rowid()");
			}
			
			statement.close();
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		} 
		return newId;
	}

	/**
	 * Disconnect from the database
	 */
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
}
