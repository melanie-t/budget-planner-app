import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {

	@Test
	public void setupAccount() {

		/* Test Account */
		int id = 1;
		int balance = 1000;
		String bankName = "Bank of Montreal";
		String nickname = "BMO";

		Account testAccount = new Account(id, bankName, nickname, balance);
		
		/* Expected Account tuple */
		Account expectedAccount = new Account();
		expectedAccount.setId(id);
		expectedAccount.setBalance(balance);
		expectedAccount.setBankName(bankName);
		expectedAccount.setNickname(nickname);
		
		assertEquals(testAccount.getBalance(), expectedAccount.getBalance());
		assertEquals(testAccount.getId(), expectedAccount.getId());
		assertEquals(testAccount.getBankName(), expectedAccount.getBankName());
		assertEquals(testAccount.getNickname(), expectedAccount.getNickname());
	}
	
	@Test
	public void testUpdateAccount() {
		
		/* Test Account */
		int id = 1;
		int balance = 1000;
		String bankName = "Bank of Montreal";
		String nickname = "BMO";

		Account testAccount = new Account(id, bankName, nickname, balance);
		
		/* Updated account info */
		String newBankName = "Toronto Dominion Bank";
		String newNickname = "TD Bank";
		Integer newBalance = 2000;
		
		Account updatedAccount = new Account();
		updatedAccount.setBankName(newBankName);
		updatedAccount.setBalance(newBalance);
		updatedAccount.setNickname(newNickname);
		
		/* Updating the actual account */
		testAccount.updateWith(updatedAccount);
		assertEquals(testAccount.getBalance(), newBalance);
		assertEquals(testAccount.getBankName(), newBankName);
		assertEquals(testAccount.getNickname(), newNickname);
	}
}
