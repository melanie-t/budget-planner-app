
public class AbstractModel {
	boolean boolNew;
	
	
	public AbstractModel(){
		boolNew = true;
	}
	
	// Is new
	// used to determine wither id has been set
	public boolean isNew() {return boolNew;}
	public void setIsNewModel(boolean boolNew) {this.boolNew = boolNew;}
}
