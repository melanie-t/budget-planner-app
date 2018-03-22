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
