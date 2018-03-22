import java.util.Map.Entry;

/**
 * Singleton class used to generate String objects formatted for SQL queries.
 */
public class SQLStringFactory 
{
	private static SQLStringFactory m_instance = null;

	/**
	 * Returns the instance of the singleton.
	 * @return a reference to the instance
	 */
	public static SQLStringFactory getInstance()
	{
		if (m_instance == null)
		{
			m_instance = new SQLStringFactory();
		}
		return m_instance;
	}

	/**
	 * Constructor.
	 */
	private SQLStringFactory()
	{
	}

	/**
	 * Generate a DROP TABLE statement.
	 * @param tableName name of table
	 * @return SQL query String
	 */
	public String deleteTable(String tableName)
	{
		String sql = "DROP TABLE IF EXISTS " + tableName;
		
		return sql;
	}

	/**
	 * Generate a CREATE TABLE statement.
	 * @param tableName name of the table
	 * @param primaryKeyName name of the primary key
	 * @param primaryKeyType SQL type of the key
	 * @return SQL query String
	 */
	public String createTable(String tableName, String primaryKeyName, String primaryKeyType)
	{

		String sql = 
				"CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY);";
		
		return sql;
	}

	/**
	 * Generate a CREATE TABLE statement with foreign key references.
	 * @param tableName name of the table
	 * @param primaryKeyName name of the primary key
	 * @param primaryKeyType SQL type of the primary key
	 * @param foreignKeyName name of the foreign key
	 * @param foreignKeyType SQL type of the foreign
	 * @param foreignKeyReferencesTable table associated with foreign key
	 * @param foreignKeyReferencesColumn column in this table for foreign key
	 * @return SQL query string
	 */
	public String createTable(String tableName, String primaryKeyName, String primaryKeyType, String foreignKeyName, String foreignKeyType, String foreignKeyReferencesTable, String foreignKeyReferencesColumn)
	{

		String sql = 
				"CREATE TABLE IF NOT EXISTS " + tableName
				+ "(" + primaryKeyName + " " + primaryKeyType + " PRIMARY KEY,\n"
				+ foreignKeyName + " " + foreignKeyType + ",\n"
				+ "FOREIGN KEY("+foreignKeyName+") REFERENCES "+ foreignKeyReferencesTable+ "("+ foreignKeyReferencesColumn + "));";
		
		return sql;
	}

	/**
	 * Generate an ALTER TABLE ... ADD COLUMN statement.
	 * @param tableName name of the table
	 * @param columnName name of column
	 * @param columnType SQL type of column
	 * @return SQL query string
	 */
	public String addColumn(String tableName, String columnName, String columnType/*, String... defaultValue*/)
	{
		// TODO - implement stuff for default value
		
		String sql = 
				"ALTER TABLE " + tableName
				+ "\n ADD COLUMN " + columnName + " " + columnType;
		
		
		return sql;
	} 
	
//	public String addEntry(String tableName, String... values) //@TODO change quotes ESCAPE value update TEST
//	{
//		String sql = "INSERT INTO " + tableName + " VALUES (";
//
//		for (String value : values)
//		{
//			if (value == "NULL")
//			{
//				sql += "NULL, ";
//			}
//			else
//			{
//				sql += "\"" + value + "\", ";
//			}
//
//		}
//
//		sql = sql.substring(0, sql.length() - 2); // chop off the last ", "
//		sql += ");";
//
//		return sql;
//	}

	/**
	 * Generate DELETE FROM ... WHERE statement.
	 * @param tableName name of the table
	 * @param primaryKey name of the primary key
	 * @param value primary key value
	 * @return SQL query string
	 */
	public String deleteEntry(String tableName, String primaryKey, Integer value)
	{
		return "DELETE FROM " + tableName + " WHERE " + primaryKey + "='" + value + "';";
	}

	/**
	 * Generate INSERT INTO ... VALUES statement.
	 * @param tableName name of the table
	 * @param values (column, value) pairs to insert
	 * @return SQL query string
	 */
	public String addEntryUsingMap(String tableName, SQLValueMap values)
	{
		String strColumns = "";
		String strValues = "";
		String glue = ", ";
		
		if(values.size() > 0) {
			int i=0;
			for (Entry<String, String> entry : values.entrySet()) {
				++i; // current position on scale between 1 and n
				String key = entry.getKey();
				String value = entry.getValue();
				
				// apply glue
				if(i != 1) {// will only apply glue in-between entries
					strColumns += glue;
					strValues += glue;
				}
				
				// Add next column and value to sequence
				strColumns += key;
				
				if (value == "NULL") 
					strValues += "NULL";
				 else 
					strValues += "'" + EscapeSQLValue(value) + "'"; // prevent sql injection
			}
		}
		
		String sql = "INSERT INTO " + tableName + " ("+strColumns+") VALUES ("+strValues+");";
		
		return sql;
	}

	/**
	 * Generate UPDATE ... WHERE statement.
	 * @param tableName name of the table
	 * @param values (column, value) pairs to update
	 * @param where (column, value) tested values
	 * @return SQL query string
	 */
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
			String whereCondition = "";
			whereCondition = buildWhereCondition(where, conditionOp);
			if(whereCondition.length() > 0) 
				sql += " WHERE " + whereCondition;
		}
		sql += ';';
		
		return sql;
	}


	/**
	 * Generate SELECT * FROM ... WHERE statement
	 * @param tableName name of table
	 * @param where (column, value) tested values
	 * @return SQL query string
	 */
	public String selectEntryUsingMap(String tableName, SQLValueMap where)
	{
		String conditionOp = "AND"; // condition operator used to join where conditions
		String sql = "SELECT * FROM " + tableName;
		
		//Build Where condition
		String whereCondition = "";
		whereCondition = buildWhereCondition(where, conditionOp);
		if(whereCondition.length() > 0) 
			sql += " WHERE " + whereCondition;
		
		sql += ';';
		
		return sql;
	}

	/**
	 * Generate content of the WHERE portion of a statement
	 * @param where (column, value) tested values
	 * @param conditionOp condition operator
	 * @return SQL query string
	 */
	protected String buildWhereCondition(SQLValueMap where, String conditionOp) { 
		//This function may be used to create chunks of conditions
				
		String whereCondition = "";
		int whereSize = where.size();
		if(whereSize > 0) {		
			String glue = " " + conditionOp + " ";
			
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
			
		}
		return whereCondition;
	}

	/**
	 * Helper to generate escape character sequences
	 * @param value string to "escape"
	 * @return SQL escape character sequence
	 */
	protected String EscapeSQLValue(String value) {
		// Credit: https://stackoverflow.com/questions/1812891/java-escape-string-to-prevent-sql-injection
		value = value.replace("\\", "\\\\");
		value = value.replace("'", "\\'");
		value = value.replace("\0", "\\0");
		value = value.replace("\n", "\\n");
		value = value.replace("\r", "\\r");
		value = value.replace("\"", "\\\"");
		value = value.replace("\\x1a", "\\Z");
		return value;
	}
}
