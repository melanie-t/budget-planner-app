import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class QueryWrapper 
{
	private static QueryWrapper m_instance = null;
	private static String m_dataBaseName = "jdbc:sqlite:test.db";
	
	private Connection m_connection = null; 
	private Statement m_statement = null;
	
	
	////////////////////////////////////////////////////
	// --- Public --------------------------------------
	public static QueryWrapper getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new QueryWrapper();
		}
		return m_instance;
	}

	public boolean deleteTable(String tableName)
	{
		String sql = "DROP TABLE IF EXISTS " + tableName;
		
		try 
		{ 
			updateSQL(sql); 
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean createTable(String tableName, String primaryKeyName, String primaryKeyType)
	{

		String sql = 
				"CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY);";
		
		try 
		{ 
			updateSQL(sql); 
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean addColumn(String tableName, String columnName, String columnType, String... defaultValue)
	{
		// TODO - make sure that the table actually exists, return false
		
		// added a defaultValue placeholder, it is implied that if a default value is provided then
		// we want to add a column with the NOT NULL constraint - can't get it work correctly though
		
		String sql = 
				"ALTER TABLE " + tableName
				+ "\n ADD COLUMN " + columnName + " " + columnType;
		
		if (defaultValue.length != 0)
		{
			sql += " DEFAULT '" + defaultValue[0] + "'";
		}
		
		try 
		{ 
			updateSQL(sql); 
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	
	public boolean addEntry(String tableName, String... values)
	{
		// TODO - make sure that the table actually exists, return false
		// TODO - make sure that the values argument fits the format of the table, return false
		
		if (values.length == 0)
		{
			return false;
		}
		
		String sql = "INSERT INTO " + tableName + " VALUES (";
				
		for (String value : values)
		{
			if (value == "NULL")
			{
				sql += "NULL, ";
			}
			else
			{
				sql += "\"" + value + "\", ";	
			}
			
		}
		
		sql = sql.substring(0, sql.length() - 2); // chop off the last ", " 
		sql += ");";
		
		try 
		{ 
			updateSQL(sql); 
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean showAll(String tableName, View view)
	{
		// TODO - make sure the table exists, return false
		
		String sql = "SELECT * from " + tableName;
		ResultSet resultSet = null;
		
		try 
		{ 
			resultSet = querySQL(sql);
			view.update(resultSet);
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
		
	public void shutdown() throws SQLException
	{
		if (m_statement != null)
		{
			m_statement.close();
		}
		
		if (m_connection != null)
		{
			m_connection.close();
		}

	}
    ////////////////////////////////////////////////////
	// --- Private -------------------------------------
	private QueryWrapper()
	{
		try 
		{ 
			init(); 
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		} 
		
	}
	
	
	private void updateSQL(String sql) throws SQLException
	{
		m_statement.executeUpdate(sql);
	}
	
	private ResultSet querySQL(String sql) throws SQLException
	{
		ResultSet result = m_statement.executeQuery(sql);
		return result;
	}
	
	private void init() throws SQLException
	{
		m_connection = DriverManager.getConnection(m_dataBaseName);
		m_statement = m_connection.createStatement();
		
	}
	
	
	
	
	
	
}
