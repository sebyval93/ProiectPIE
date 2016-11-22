package Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
}
