package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

public final class AnUniversitarService {

	public static AnUniversitar getAnByID(int ID){
		AnUniversitar an = null;
		Session session = null;
		try{
		    session = Singleton.getInstance().getNewSession();
			an = session.get(AnUniversitar.class,new BigDecimal(ID));	
		}catch (Exception e) {
            e.printStackTrace();
        } finally {
        	session.close();
        }
		return an;
	}
	
	public static boolean addAn(int number){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			AnUniversitar an = new AnUniversitar(new BigDecimal(number));
			session.save(an);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteAnByID(int ID){
		boolean done = false;
		AnUniversitar an = getAnByID(ID);
		Session session = null;	
		if(an != null){
			try{		
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(an);
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
	
	public static boolean updateAnByID(int ID,int number){
		boolean done = false;
		Session session = null;
		AnUniversitar an = getAnByID(ID);
		an.setAn(new BigDecimal(number));
		try{
			
			session = Singleton.getInstance().getNewSession();	
			session.beginTransaction();
			session.update(an);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static List<AnUniversitar> getAllFromAn(){
		List<AnUniversitar> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from AnUniversitar").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
}
