import java.util.ArrayList;

public interface IModelView extends IObservable{

    ArrayList<Transaction> getTransactions(Integer fromAccount);

    ArrayList<Account> getAllAccounts();
}
