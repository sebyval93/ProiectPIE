package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;

import Services.SaptamanaService;
import Singleton.Singleton;
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
	
	public static boolean resetSequence(String seqName){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.createSQLQuery("DROP SEQUENCE "+ seqName).executeUpdate();
			session.createSQLQuery("CREATE SEQUENCE "+ seqName + " START WITH 1 INCREMENT BY 1 CACHE 100").executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();    
		}finally{
			session.close();
		}
		return done;
	}
	
	public static Calendar getEasterDate(int year){
		int a,b,c,d,e;
		Calendar cal = Calendar.getInstance();
		a = year%4;
		b = year%7;
		c = year%19;
		d = (19*c + 15)%30;
		e = (2 * a + 4 * b - d + 34)%7;
		int LunaPaste,ziuaPaste;
		LunaPaste = (d + e + 114) / 31;
		ziuaPaste = ((d + e + 114) % 31) + 1;
		cal.set(year, LunaPaste - 1, ziuaPaste);
		cal.add(Calendar.DATE, 13);
		return cal;
	}
	
	public static boolean isDateBetween(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	        if (date.after(dateStart) && date.before(dateEnd)) {
	            return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;
	}
	
	
	
}
