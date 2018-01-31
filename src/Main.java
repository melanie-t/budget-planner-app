public class Main {

	public static void main(String[] args) throws ClassNotFoundException
    {
		
		Database myDatabase = new Database("MyDB");
		SQLStringFactory sql = SQLStringFactory.getInstance();
		ViewFactory viewFactory = new ViewFactory(myDatabase);		
		
		myDatabase.updateSQL(sql.deleteTable(Account.getTableName()));
		
		myDatabase.updateSQL(sql.createTable(Account.getTableName(), "accId", "INTEGER"));
		myDatabase.updateSQL(sql.addColumn(Account.getTableName(), "bankName", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn(Account.getTableName(), "nickname", "VARCHAR"));
		myDatabase.updateSQL(sql.addColumn(Account.getTableName(), "money", "FLOAT"));
		
		View listing = viewFactory.createView(sql.showAll(Account.getTableName()));
		Account a = new Account(1234567890, "A", null, 10.0);
		a.upsertAccount(myDatabase);
		
		System.out.println(listing);
		
		listing.shutdown();
		myDatabase.shutdown();
		
    }
}
