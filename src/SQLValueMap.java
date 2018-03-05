import java.util.LinkedHashMap;
/*********************************************************************
 * 
 * 							SQLValueMap
 * 
 * Description: Shortcut Class
 * 				LinkedHashMap<String, String> is too long to write every time... 
 * 				ain't no body got time for that...
 *********************************************************************/
public class SQLValueMap extends LinkedHashMap<String, String>{ // likely to change to a "contains" relationship
	
	// Put integer in list without type-casting every time
	public void put(String key, Integer value) {
		put(key, Integer.toString(value));
	}
	
	// Put Float in list without type-casting every time
	public void put(String key, Float value) {
		put(key, Float.toString(value));
	}
	
	
}

