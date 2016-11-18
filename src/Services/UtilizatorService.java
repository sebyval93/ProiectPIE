package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

public final class UtilizatorService {

	
	public static Utilizator getUtilizatorByID(int ID){
		Utilizator utilizator = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			utilizator = session.get(Utilizator.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return utilizator;
		
	}
	
	public static boolean addUtilizator(String username, String password){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Utilizator utilizator = new Utilizator(username,password);
			session.save(utilizator);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteUtilizatorByID(int ID){
		boolean done = false;
		Utilizator utilizator = getUtilizatorByID(ID);
		Session session = null;
		if(utilizator != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(utilizator);
				session.getTransaction().commit();
				done = true;
			}catch (Exception e) {
	            e.printStackTrace();           
	        }finally { 
	        	session.close();
	        } 
		}
		return done;
	}
	
	public static boolean updateUtilizator(int ID,String username, String password){
		boolean done = false;
		Session session = null;
		Utilizator utilizator = getUtilizatorByID(ID);
		if(utilizator != null){
			utilizator.setUsername(username);
			utilizator.setPassword(password); 
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(utilizator);
				session.getTransaction().commit();
				done = true;
			}catch (Exception e) {
	            e.printStackTrace();           
	        } finally { 
	        	session.close();
	        }
		}
		return done;
		
	}
	
	public static List<Utilizator> getAllFromUtilizator(){
		List<Utilizator> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Utilizator").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
}
