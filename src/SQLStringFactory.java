import java.util.Map.Entry;

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
	public String createTable(String tableName, String primaryKeyName, String primaryKeyType, String foreignKeyName, String foreignKeyType, String foreignKeyReferencesTable, String foreignKeyReferencesColumn)
	{

		String sql = 
				"CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY,\n"
				+ foreignKeyName + " " + foreignKeyType + ",\n"
				+ "FOREIGN KEY("+foreignKeyName+") REFERENCES "+ foreignKeyReferencesTable+ "("+ foreignKeyReferencesColumn + "));";
		
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
	
	//This function updates the data
	public String updateEntryUsingMap(String tableName, SQLValueMap values, SQLValueMap where)
	{
		String conditionOp = "AND"; // condition operator used to join where conditions
		String sql = "UPDATE " + tableName + " SET ";
		
		//Build Values
		if(values.size() > 0) {
			for (Entry<String, String> entry : values.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				String sanitizedChunk;
				if (value == "NULL")
					sanitizedChunk = "NULL, ";
				else
					sanitizedChunk = "'" + EscapeSQLValue(value) + "', "; // prevent sql injection
				
				sql += key + " = " + sanitizedChunk;
			}
			sql = sql.substring(0, sql.length() - 2); // chop off the last ", " 
			
			
			//Build Where condition
			int whereSize = where.size();
			if(whereSize > 0) {
				String glue = " " + conditionOp + " ";
						
				String whereCondition = " WHERE ";
				int i=0;
				for (Entry<String, String> entry : where.entrySet()) {
					++i; // current position on scale between 1 and n
					String key = entry.getKey();
					String value = entry.getValue();
					
					// apply glue
					if(i != 1) // will only apply glue in-between entries
						whereCondition += glue;
					
					// stick next condition on to string
					if (value == "NULL")
						whereCondition += key + " IS " + "NULL";
					else
						whereCondition += key + " = '" + EscapeSQLValue(value) + "'"; // prevent sql injection
					
				}
				
				sql += whereCondition;
			}
		}
		sql += ';';
		
		return sql;
	}
	
	
	protected String EscapeSQLValue(String value) {
		// Credit: https://stackoverflow.com/questions/1812891/java-escape-string-to-prevent-sql-injection
		// I find it shocking Java dosen't have this built in - Jordan
		value = value.replace("\\", "\\\\");
	    value = value.replace("'", "\\'");
	    value = value.replace("\0", "\\0");
	    value = value.replace("\n", "\\n");
	    value = value.replace("\r", "\\r");
	    value = value.replace("\"", "\\\"");
	    value = value.replace("\\x1a", "\\Z");
		return value;
	}
	
	public String showAll(String tableName)
	{
		String sql = "SELECT * from " + tableName;
		
		return sql;
	}
	
}
