import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractRepository<T extends AbstractUniqueId> {

    protected Database database;
    protected SQLStringFactory sql;

    protected String tableName;
    protected String primaryKey;

    protected HashMap<Integer, T> itemMap;

    public AbstractRepository(Database database, String tableName, String primaryKey) {
        this.database = database;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.sql = SQLStringFactory.getInstance();
        itemMap = new HashMap<>();
    }

    public T getItem(Integer id) {
        return itemMap.get(id);
    }

    public void loadAllItems() {
        SQLValueMap where = new SQLValueMap(); // left blank so where is omitted
        ResultSet result = database.fetchSQL(sql.selectEntryUsingMap(tableName, where));

        try {
            while(result.next())
                setItemFromResult(result);

        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }


    protected void addItemToMap(T item) {
        itemMap.put(item.getId(), item);
    }

    public void deleteItem(Integer id) {
        if(itemMap.containsKey(id))
        {
            itemMap.remove(id);
            database.updateSQL(sql.deleteEntry(tableName, primaryKey, id));
        }
    }

    public ArrayList<T> getItems() {
        return  new ArrayList<T>(itemMap.values());
    }

    public void reinitSQLStructure() {
        destroySQLStructure();
        initSQLStructure();
    }

    private void destroySQLStructure() {
        database.updateSQL(sql.deleteTable(tableName));
    }

    abstract protected void setItemFromResult(ResultSet result);

    abstract public void initSQLStructure();

    abstract public void saveItem(T item);
}
