package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

public final class SemestruService {
	
	public static Semestru getSemestruByID(int ID){
		Semestru semestru = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			semestru = session.get(Semestru.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return semestru;
		
	}
		
	public static boolean addSemestru(String nume){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Semestru semestru = new Semestru(nume);
			session.save(semestru);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deleteSemestruByID(int ID){
		boolean done = false;
		Semestru semestru = getSemestruByID(ID);
		Session session = null;
		if(semestru != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(semestru);
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
	
	public static boolean updateSemestruByID(int ID,String nume){
		boolean done = false;
		Session session = null;
		Semestru semestru = getSemestruByID(ID);
		if(semestru != null){
			semestru.setNumeSem(nume);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(semestru);
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
	
	public static List<Semestru> getAllFromSemestru(){
		List<Semestru> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Semestru").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}

}
