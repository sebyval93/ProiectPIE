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
	
	
	public static void addGrupa(AnUniversitar an,String nume){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Grupa grupa = new Grupa(an,nume);
			session.save(grupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
	
	
	public static void deleteGrupaByID(int ID){
		Grupa grupa = getGrupaByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(grupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateGrupaByID(int ID,AnUniversitar an,String nume){
		Session session = null;
		Grupa grupa = getGrupaByID(ID);
		grupa.setAnUniversitar(an);
		grupa.setNume(nume);
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.update(grupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
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
	
	
}
