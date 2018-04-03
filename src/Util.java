
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

	public static boolean validDateString(String date) {
		try {
			if(date.length() != 10) return false;   //format dd-mm-yyyy

			String[] tokens = date.split("-");
			if (tokens.length != 3) return false;

			int day = Integer.parseInt(tokens[0]);
			int month = Integer.parseInt(tokens[1]);
			int year = Integer.parseInt(tokens[2]);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
}
