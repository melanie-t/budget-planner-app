import java.util.ArrayList;

public interface IModelView extends IObservable{

    TransactionList getTransactions();
    TransactionList getTransactions(Integer fromAccount);

    AccountList getAllAccounts();

}
