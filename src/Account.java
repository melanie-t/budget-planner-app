public class Account {
	private static String tableName = "ACCOUNTS";
	private int accountId;
	private String bankName;
	private String nickname;
	private double balance;
	
	public void upsertAccount(Database db) {
		SQLStringFactory sql = SQLStringFactory.getInstance();
		db.updateSQL(sql.upsertEntry(tableName, ""+accountId, bankName, nickname, ""+balance));
	}
	
	public static String getTableName() {
		return tableName;
	}
	
	public Account(int accountId, String bankName, String nickname, double balance) {
		this.accountId = accountId;
		this.bankName = bankName;
		this.nickname = nickname;
		this.balance = balance;
	}
	
	public Account() {}
}
