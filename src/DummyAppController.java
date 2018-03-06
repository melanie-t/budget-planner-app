
public class DummyAppController extends AppController {
	
		Database myDatabase;
		SQLStringFactory sql;
		
		
		AccountRespository theAccountRespository;
		TransactionRepository theTransactionRepository;
		
		View accountsView;
		View transactionsView;
		
		public DummyAppController() {}
		
		
		
		public void start() {
			myDatabase = new Database("MyDB");
			this.sql = SQLStringFactory.getInstance();
			
			theAccountRespository = new AccountRespository(myDatabase);
			//theAccountRespository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
			
			theTransactionRepository = new TransactionRepository(myDatabase);
			//theTransactionRepository.reinitSQLStructure(); // will whipe and reinstall sql tables
			
		}
		
		public void shutdown() {
		
		}
		
		public void run() {
			
			SQLValueMap where = new SQLValueMap();
			where.put("accountId", 1);
			
			System.out.println(sql.selectEntryUsingMap("account", where));
			
			//myDatabase.fetchSQL @TODO make this function
			
		}
		
	
}

