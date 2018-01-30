public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		Database myDatabase = new Database("MyDB");
		SQLStringFactory sql = SQLStringFactory.getInstance();
		ViewFactory viewFactory = new ViewFactory(myDatabase);		
		
		myDatabase.updateSQL(sql.deleteTable("test"));
		
		myDatabase.updateSQL(sql.createTable("test", "key", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn("test", "name", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn("test", "money", "FLOAT"));
		
		View listing = viewFactory.createView(sql.showAll("test"));
		
		myDatabase.updateSQL(sql.addEntry("test", "1", "Bob", "10.0"));
		myDatabase.updateSQL(sql.addEntry("test", "2", "Martha", "20.0"));
		
		System.out.println(listing);
		
		listing.shutdown();
		myDatabase.shutdown();
		
    }
}
