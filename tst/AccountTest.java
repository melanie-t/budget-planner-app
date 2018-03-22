import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {

	@Test
	public void setUpClass() {

		/* Test Account */
		Integer id = 1;
		Integer balance = 1000;
		String bankName = "Bank of Montreal";
		String nickname = "BMO";

		Account testAccount = new Account(id, bankName, nickname, balance);
		
		assertEquals(testAccount.getBalance(), balance);
		assertEquals(testAccount.getId(), id);
		assertEquals(testAccount.getBankName(), bankName);
		assertEquals(testAccount.getNickname(), nickname);
	}
	
	@Test
	public void updateAccountTest() {
		
		/* Test Account */
		Integer id = 1;
		Integer balance = 1000;
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
