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
	
	public static void addUtilizator(String username, String password){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Utilizator utilizator = new Utilizator(username,password);
			session.save(utilizator);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
	
	public static void deleteUtilizatorByID(int ID){
		Utilizator utilizator = getUtilizatorByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(utilizator);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateUtilizator(int ID,String username, String password){
		Session session = null;
		Utilizator utilizator = getUtilizatorByID(ID);
		utilizator.setUsername(username);
		utilizator.setPassword(password); 
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.update(utilizator);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
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
