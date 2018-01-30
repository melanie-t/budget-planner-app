import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class View
{
	/////////////////////////////////////////////////////////////////
	// --- Members --------------------------------------------------
	private PreparedStatement m_preparedStatement = null;
	private ResultSet m_resultSet = null;
	private ArrayList<Object> m_statementParams = new ArrayList<Object>();	// Ugly ass way to create a type-agnostic array
	private Database m_database = null; // For observer pattern thing
	// ______________________________________________________________


	/////////////////////////////////////////////////////////////////
	// --- Constructor ----------------------------------------------
	public View(PreparedStatement statement, Database database)
	{
		m_preparedStatement = statement;
		m_database = database;
	}
	// ______________________________________________________________

	
	// Please pass object types (Integer instead of int) - currently only supports Integer, Float and String
	public void setStatementParams(Object... params)
	{
		for (Object param : params)
		{
			m_statementParams.add(param);
		}
		
		// Ugly ass dynamic cast at runtime to call the appropriate method - cause overloaded methods are over rated AMIRIGHT !
		Object param = null;
		for (int i = 0; i < m_statementParams.size(); i++)
		{
			param = m_statementParams.get(i);
			
			try
			{
				// Index start at 1 : what could go wrong :D
				if (param instanceof Integer)
				{
					m_preparedStatement.setInt(i+1, (int) param);		// Nothing like a good downcast to start the day !
				}
				else if (param instanceof String)
				{
					m_preparedStatement.setString(i+1, (String) param);	// Feel the downcast
				}
				else if (param instanceof Float)
				{
					m_preparedStatement.setFloat(i+1, (float) param);	// OOP BABY -- Wooooooooooooo
				}
			}

			catch (SQLException e) 
			{ 
				// System.out.println("kill ureself");
				System.err.println(e.getMessage()); 
			} 
		}
	}
	
	public void clearStatementParams()
	{
		m_statementParams.clear();
	}
	
	/////////////////////////////////////////////////////////////////
	// --- Observer things ------------------------------------------
	public void update()
	{
		try
		{
			m_resultSet = m_preparedStatement.executeQuery();
		}
		catch (SQLException e) 
		{ 
			System.err.println(e.getMessage()); 
		} 

	}

	private void detach()
	{
		m_database.detach(this);
	}
	// ______________________________________________________________
	
	
	// This is what does the "viewing"
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
					case "FLOAT":
						output += m_resultSet.getFloat(columnNames[i]) + "\n";
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
	
	/////////////////////////////////////////////////////////////////
	// --- Private Utilities ---------------------------------------
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
			output[i] = metaData.getColumnName(i + 1);
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
			output[i] = metaData.getColumnTypeName(i + 1);
		}
		
		return output;
	}
	// _____________________________________________________________
	
	/////////////////////////////////////////////////////////////////
	// --- CleanUp -------------------------------------------------
	public void shutdown()
	{
		detach();
		
		try
		{
			if (m_resultSet != null && !m_resultSet.isClosed())
			{
				m_resultSet.close();
			}
			if (m_preparedStatement != null && !m_preparedStatement.isClosed())
			{
				m_preparedStatement.close();
			}			
		}
		catch (Exception e) 
		{ 
			System.err.println(e.getMessage()); 
		}
	}
	// _____________________________________________________________
	

}
