/**
 * Base class for all objects that are meant to be saved in a repository and/or database.
 */
public abstract class AbstractUniqueId {
    private Integer id;

    public AbstractUniqueId(Integer id) {
        this.id = id;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {
        this.id = id;
    }
}
