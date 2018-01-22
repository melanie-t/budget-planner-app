import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class View 
{
	// TODO
	// This is just a placeholder for now. The idea is that whenever "query" request are made to the QueryWrapper
	// ( for now the only query is showAll() ), we should pass a view that needs to be update. Kinda like an observer pattern.
	// Right now the View object only has a toString() method.
	
	private ResultSet m_resultSet = null;

	View()
	{
		
	}
	
	public void update(ResultSet resultSet) throws SQLException
	{
		m_resultSet = resultSet;
	}
	
	public String toString()
	{
		if (m_resultSet == null)
		{
			return "";
		}
		
		
		String output = "";
		try
		{
			String columnNames[] = getColumnNames();
			String columnTypes[] = getColumnTypes();
			
			// Currently only supports INTEGER or VARCHAR types, cause the interface sucks
			String type = "";
			while(m_resultSet.next())
			{
				output += "\n-------------------\n";
				for (int i = 0; i < columnNames.length; i++)
				{
					output += columnNames[i] + " -> ";
					
					type = columnTypes[i].toUpperCase();
					switch (type)
					{
					case "VARCHAR":
						output += m_resultSet.getString(columnNames[i]) + "\n";
						break;
					case "INTEGER":
						output += m_resultSet.getInt(columnNames[i]) + "\n";
						break;
					default:
						break;
					}
				}
			}
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage());
		}
		
		return output;
		 
	}
	
	
	private String[] getColumnNames() throws SQLException 
	{
		if (m_resultSet == null)
		{
			return null;
		}
		
		ResultSetMetaData metaData = m_resultSet.getMetaData();
		String output[] = new String[metaData.getColumnCount()];
		
		for (int i = 0; i < output.length; i++)
		{
			output[i] = metaData.getColumnName(i + 1);  // Because their indexes start at 1 !!!
		}
		
		return output;

	}
	
	private String[] getColumnTypes() throws SQLException
	{
		if (m_resultSet == null)
		{
			return null;
		}
		
		ResultSetMetaData metaData = m_resultSet.getMetaData();
		String output[] = new String[metaData.getColumnCount()];
		
		for (int i = 0; i < output.length; i++)
		{
			output[i] = metaData.getColumnTypeName(i + 1);  // Because their indexes start at 1 !!!
		}
		
		return output;
	}

}
