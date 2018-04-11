import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import javax.imageio.ImageTranscoder;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

/*
 * Test Suite for input validation of AccountController
 * handleAddOrUpdate()
 */

public class AccountControllerInputTest {

    private static IModelController modelMock;
    private static Account testAccount;

    private static String ERROR_TITLE = "Input Error";
    private static String BASE_ERROR_MESSAGE = "Please complete the fields: ";
    private static String EMPTY_BANK_MESSAGE = "Bank. ";
    private static String EMPTY_NICKNAME_MESSAGE = "Nickname. ";
    private static String BALANCE_IS_NULL_MESSAGE = "Balance must be a number.";

    @BeforeClass
    public static void setupTestAccount() {
        int accountId = (int) (Math.random() * Integer.MAX_VALUE);
        String bankName = "testBaNk";
        String nickname = "TestAccount";
        Integer balance = (int) (Math.random() * Integer.MAX_VALUE);

        testAccount = new Account(accountId, bankName, nickname, balance);
    }

    @Test
    public void testAddAccountEmptyBankName() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              "",
                                              testAccount.getNickname(),
                                              testAccount.getBalance());

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_BANK_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountEmptyNickname() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              testAccount.getBankName(),
                                              "",
                                              testAccount.getBalance());

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_NICKNAME_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountBalanceIsNull() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              testAccount.getBankName(),
                                              testAccount.getNickname(),
                                              null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + BALANCE_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountEmptyBankAndNickName() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              "",
                                              "",
                                              testAccount.getBalance());

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_BANK_MESSAGE + EMPTY_NICKNAME_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountEmptyBankAndBalanceIsNull() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              "",
                                              testAccount.getNickname(),
                                              null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_BANK_MESSAGE + BALANCE_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountEmptyNicknameAndBalanceIsNull() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              testAccount.getBankName(),
                                              "",
                                              null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_NICKNAME_MESSAGE + BALANCE_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }

    @Test
    public void testAddAccountAllWrong() {
        //build a mocked view with the test conditions
        IAccountView viewMock = buildViewMock(testAccount.getId(),
                                              "",
                                              "",
                                              null);

        //construct error message
        String errorMessage = BASE_ERROR_MESSAGE + EMPTY_BANK_MESSAGE + EMPTY_NICKNAME_MESSAGE+ BALANCE_IS_NULL_MESSAGE;

        //run test
        runTestWithInputs(viewMock, ERROR_TITLE, errorMessage);
    }



    //run test given a mocked view and an error title and error message
    //no accounts should be saved (all invalid cases)
    private void runTestWithInputs(IAccountView viewMock, String expectedTitle, String expectedMessage) {
        IModelController modelMock = mock(IModelController.class);

        //spy on the controller
        AccountController spyController = spy(new AccountController(viewMock, modelMock));

        //Setup argument captors for error message dialog
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        //stub out the error dialog box method
        doNothing().when(spyController).showMessageDialog(titleCaptor.capture(), messageCaptor.capture());

        //mock an update action event
        ActionEvent e = mock(ActionEvent.class);
        when(e.getActionCommand()).thenReturn("Update");

        //call the method to test
        spyController.actionPerformed(e);

        //assert the right error title and message were produced
        assertEquals("Title", expectedTitle, titleCaptor.getValue());
        assertEquals("Message", expectedMessage, messageCaptor.getValue());

        //verify no accounts were saved
        verify(modelMock, never()).saveItem((Account) any());
    }

    /*
     * Builds a mocked view containing the inputs
     */
    private IAccountView buildViewMock(int accountId, String bankName, String nickname, Integer balance) {
        IAccountView viewMock = mock(IAccountView.class);

        when(viewMock.getSelectedAccountId()).thenReturn(accountId);
        when(viewMock.getBankInput()).thenReturn(bankName);
        when(viewMock.getNicknameInput()).thenReturn(nickname);
        when(viewMock.getBalanceInput()).thenReturn(balance);

        return viewMock;
    }
}