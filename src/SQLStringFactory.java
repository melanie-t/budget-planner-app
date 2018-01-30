public class SQLStringFactory 
{
	private static SQLStringFactory m_instance = null;
	
	public static SQLStringFactory getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new SQLStringFactory();
		}
		return m_instance;
	}
	
	private SQLStringFactory()
	{
		
	}
	
	public String deleteTable(String tableName)
	{
		String sql = "DROP TABLE IF EXISTS " + tableName;
		
		return sql;
	}
	
	public String createTable(String tableName, String primaryKeyName, String primaryKeyType)
	{

		String sql = 
				"CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY);";
		
		return sql;
	}
	
	public String addColumn(String tableName, String columnName, String columnType/*, String... defaultValue*/)
	{
		// TODO - implement stuff for default value
		
		String sql = 
				"ALTER TABLE " + tableName
				+ "\n ADD COLUMN " + columnName + " " + columnType;
		
		
		return sql;
	} 
	
	public String addEntry(String tableName, String... values)
	{
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
		
		return sql;
	}
	
	public String showAll(String tableName)
	{
		String sql = "SELECT * from " + tableName;
		
		return sql;
	}
}
