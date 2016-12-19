package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import entity.*;

public final class SubgrupaService {
	
	public static Subgrupa getSubgrupaByID(int ID){
		Subgrupa subgrupa = null;
		Session session = null;
		try{
		    session = Singleton.getInstance().getNewSession();
			subgrupa = session.get(Subgrupa.class,new BigDecimal(ID));	
		}catch (Exception e) {
            e.printStackTrace();
        } finally {
        	session.close();
        }
		return subgrupa;
	}
	
	public static boolean addSubgrupa(Grupa grupa,String nume){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Subgrupa subgrupa = new Subgrupa(grupa,nume);
			session.save(subgrupa);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteSubgrupaByID(int ID){
		boolean done = false;
		Subgrupa subgrupa = getSubgrupaByID(ID);
		Session session = null;
		if(subgrupa != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(subgrupa);
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
	
	public static boolean updateSubgrupaByID(int ID,Grupa grupa,String nume){
		boolean done = false;
		Session session = null;
		Subgrupa subgrupa = getSubgrupaByID(ID);
		if(subgrupa != null){
			subgrupa.setGrupa(grupa);
			subgrupa.setNume(nume);
			try{
				session = Singleton.getInstance().getNewSession();	
				session.beginTransaction();
				session.update(subgrupa);
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
	
	public static List<Subgrupa> getAllFromSubgrupa(){
		List<Subgrupa> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Subgrupa").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static Subgrupa getSubgrupaByNume(String nume) {
		Subgrupa subgrupa = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			subgrupa = (Subgrupa) session.createQuery("from Subgrupa where NUME = '" + nume + "'").getResultList().get(0);
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return subgrupa;
	}
	
	public static List<Subgrupa> getAllSubGrupeByGrupa(Grupa grupa){
		Session session = null;
		List<Subgrupa> list = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Subgrupa.class).add(Restrictions.in("grupa", grupa));
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
				session.createQuery("delete from Subgrupa").executeUpdate();
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
