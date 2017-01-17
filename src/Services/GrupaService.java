package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import entity.*;

public final class GrupaService {

	public static Grupa getGrupaByID(int ID){
		Grupa grupa = null;
		Session session = null;
		try{
		    session = Singleton.getInstance().getNewSession();
			grupa = session.get(Grupa.class,new BigDecimal(ID));	
		}catch (Exception e) {
            e.printStackTrace();
        } finally {
        	session.close();
        }
		return grupa;
	}
		
	public static boolean addGrupa(AnUniversitar an,String nume){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Grupa grupa = new Grupa(an,nume);
			session.save(grupa);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteGrupaByID(int ID){
		boolean done = false;
		Grupa grupa = getGrupaByID(ID);
		Session session = null;
		if(grupa != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(grupa);
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
	
	public static boolean updateGrupaByID(int ID,AnUniversitar an,String nume){
		boolean done = false;
		Session session = null;
		Grupa grupa = getGrupaByID(ID);
		if(grupa != null){
			grupa.setAnUniversitar(an);
			grupa.setNume(nume);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(grupa);
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
	
	public static List<Grupa> getAllFromGrupa(){
		List<Grupa> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Grupa").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static Grupa getGrupaByNume(String nume) {
		Grupa grupa = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Subgrupa.class);
			dc.add(Restrictions.eq("nume", nume));
			grupa = (Grupa)dc.getExecutableCriteria(session).list().get(0);
			
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return grupa;
	}
	
	public static List<Grupa> getAllGrupaByAn(AnUniversitar an){
		Session session = null;
		List<Grupa> list = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Grupa.class).add(Restrictions.in("anUniversitar", an));
			list =  dc.getExecutableCriteria(session).list();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static boolean deleteAllFromTable(){
		boolean done = false;
		Session session = null;
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.createQuery("delete from Grupa").executeUpdate();
				session.getTransaction().commit();
				done = true;
			}catch (Exception e) {
	            e.printStackTrace();           
	        } finally { 
	        	session.close();
	        }
		return done;
	}
	
	
}
