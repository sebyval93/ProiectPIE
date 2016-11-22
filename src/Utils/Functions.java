package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Services.SaptamanaService;
import entity.Saptamana;

/**
 *
 * @author Nameless ^_^
 */
public class Functions {
	
	public static boolean stringIsNullOrEmpty(String s){
		if(s == null){
			return true;
		}else{
			if(s.equals("")){
				return true;
			}
		}
		return false;
	}
	
	public static Integer tryParseInt(String text) {
		try {
			return Integer.parseInt(text);
		} 
		catch (NumberFormatException e) {
		    return null;
		}
	}
	
}
