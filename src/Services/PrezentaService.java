package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;


public final class PrezentaService {

	public static Prezenta getPrezentaByID(int ID){
		Prezenta prezenta = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			prezenta = session.get(Prezenta.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return prezenta;
		
	}
	
	public static boolean addPrezenta(Modul modul, Saptamana saptamana, Student student, String prezent){
		Session session = null;
		boolean done = false;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Prezenta prezenta = new Prezenta(modul,saptamana,student,prezent);
			session.save(prezenta);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deletePrezentaByID(int ID){
		boolean done = false;
		Prezenta prezenta = getPrezentaByID(ID);
		Session session = null;
		if(prezenta != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(prezenta);
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
	
	public static boolean updatePrezentaByID(int ID,Modul modul, Saptamana saptamana, Student student, String prezent){
		boolean done = false;
		Session session = null;
		Prezenta prezenta = getPrezentaByID(ID);
		if(prezenta != null){
			prezenta.setModul(modul);
			prezenta.setSaptamana(saptamana);
			prezenta.setStudent(student);
			prezenta.setPrezent(prezent);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(modul);
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
	
	public static List<Prezenta> getAllFromPrezenta(){
		List<Prezenta> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Prezenta").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
}
