import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Base class for repository objects.
 * Classes that inherit from AbstractController should specialize the template with the object
 * type it is meant to hold.
 * @param <T> type of objects contained in the repository
 */
public abstract class AbstractRepository<T extends AbstractUniqueId> {

    protected Database database;
    protected SQLStringFactory sql;

    protected String tableName;
    protected String primaryKey;

    protected HashMap<Integer, T> itemMap;

    /**
     * Constructor.
     * @param database database this repository will write to
     * @param tableName name of the table associated with this repository in the database
     * @param primaryKey name of the primary key for the SQL table
     */
    public AbstractRepository(Database database, String tableName, String primaryKey) {
        this.database = database;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.sql = SQLStringFactory.getInstance();
        itemMap = new HashMap<>();
    }

    /**
     * Return the item with the specified id.
     * @param id id of the desired item
     * @return item associated with id
     */
    abstract public T getItem(Integer id);

    /**
     * Initialize this repository will all the contents of its associated table in the database.
     */
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

    /**
     * Add the specified item to the repository.
     * @param item item to add
     */
    protected void addItemToMap(T item) {
        itemMap.put(item.getId(), item);
    }

    /**
     * Delete the item with the specified id from the repository and the database.
     * @param id unique id of the item to remove
     */
    public void deleteItem(Integer id) {
        if(itemMap.containsKey(id)) {
        	itemMap.remove(id);
        }
        
        
        database.updateSQL(sql.deleteEntry(tableName, primaryKey, id));
    }

    /**
     * Get all items currently stored in the repository.
     * @return all items in the repository
     */
    public ArrayList<T> getItems() {
        return  new ArrayList<T>(itemMap.values());
    }

    /**
     * Deletes the associated table from the database and all its content and creates a new empty one.
     */
    public void reinitSQLStructure() {
        destroySQLStructure();
        initSQLStructure();
    }

    /**
     * Delete associated table in the database.
     */
    private void destroySQLStructure() {
        database.updateSQL(sql.deleteTable(tableName));
    }

    /**
     * Deserialize an entry from the database and create and save a new item to the repository.
     * @param result result query returned by the database
     */
    abstract protected void setItemFromResult(ResultSet result);

    /**
     * Create a table and initialize its structure.
     */
    abstract public void initSQLStructure();

    /**
     * Save the specified item to the repository and the database.
     * @param item
     */
    abstract public void saveItem(T item);
}
