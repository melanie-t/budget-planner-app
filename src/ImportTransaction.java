import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ImportTransaction {
	
	String transactionFilePath = "tst/spread_sheet_test_case.csv";
	private AccountTransactionRepository accountTransactionRepository;
	
	/* Link the account's AccountTransactionRepository before calling addTransaction() */
	public void setAccountTransactionRepository(AccountTransactionRepository accTransacRepo) {
		accountTransactionRepository = accTransacRepo;
	}
	
	/* Function to import transactions */
	public void addTransaction(String transactionFilePath) {
		String [] tokenList = null;
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(transactionFilePath));
			String line = null;
			
			while((line = br.readLine()) != null) {
				/*
				 * tokenList maps the tokens as
				 * tokenList[0]: type
				 * tokenList[1]: date
				 * tokenList[2]: amount
				 */
				tokenList = line.split(",");
				
				Integer accountID = accountTransactionRepository.getAccount().getId();
				Integer amount = Integer.parseInt(tokenList[2]);
				
				TransactionModel transacMod = new TransactionModel();
				transacMod.setAccountId(accountID);
				transacMod.setType(tokenList[0]);
				transacMod.setDate(tokenList[1]);
				transacMod.setAmount(amount);
				
				System.out.println(transacMod.toString());
				
				accountTransactionRepository.saveItem(transacMod);
				
			}
			br.close();
			
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
}