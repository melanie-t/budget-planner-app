
public class Util {
	public static boolean isNumeric(String str)  
	{  
		//Apparently this does not exist in java...
		//Credit: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
		try  {
			double d = Double.parseDouble(str);  
		} catch(NumberFormatException nfe)  {
			return false;  
		}
		return true;  
	}
}
