import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.awt.event.ActionEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    private static AccountController controller;
    private static IModelController modelMock;
    private static IAccountView viewMock;
    private static Account account;

    @BeforeClass
    public static void setUpClass() {
        int accountId = (int) (Math.random() * 10000000);;
        String bankName = "testBank";
        String nickname = "testAccount";
        int balance = (int) (Math.random() * 10000000);;

        account = new Account(accountId, bankName, nickname, balance);

        viewMock = mock(IAccountView.class);
        when(viewMock.getBankInput()).thenReturn(bankName);
        when(viewMock.getNicknameInput()).thenReturn(nickname);
        when(viewMock.getBalanceInput()).thenReturn(balance);

        when(viewMock.getSelectedAccountId()).thenReturn(accountId);
    }

    @Before
    public void resetModelMock() {
        modelMock = mock(IModelController.class);
        controller = new AccountController(viewMock, modelMock);
    }

    @Test
    public void testAddOrUpdateAccount() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Update");
        controller.actionPerformed(e);

        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(modelMock).saveItem(argument.capture());
        assertTrue("Saved account does not match inputs",
                new ReflectionEquals(account).matches(argument.getValue()));
    }
    @Test
    public void testDeleteAccount() {
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Delete");
        controller.actionPerformed(e);

        verify(modelMock).deleteAccount(account.getId());
    }
}
