package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import entity.*;

public final class StareModulService {

	public static StareModul getStareModulByID(int ID){
		StareModul stareModul = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			stareModul = session.get(StareModul.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return stareModul;
		
	}
		
	public static boolean addStareModul(Modul modul,Saptamana saptamana,int operat){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			StareModul StareModul = new StareModul(modul,saptamana,operat);
			session.save(StareModul);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deleteStareModulByID(int ID){
		boolean done = false;
		StareModul StareModul = getStareModulByID(ID);
		Session session = null;
		if( StareModul != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(StareModul);
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
	
	public static boolean updateStareModulByID(int ID,Modul modul,Saptamana saptamana,int operat){
		boolean done = false;
		Session session = null;
		StareModul StareModul = getStareModulByID(ID);
		if(StareModul != null){
			StareModul.setModul(modul);
			StareModul.setSaptamana(saptamana);
			StareModul.setOperat(new BigDecimal(operat));
			try{
				session = Singleton.getInstance().getNewSession();	
				session.beginTransaction();
				session.update(StareModul);
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
	
	public static boolean updateStareModul(StareModul modul){
		boolean done = false;
		Session session = null;
		if(modul != null){			
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
	
	public static List<StareModul> getAllFromStareModul(){
		List<StareModul> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from StareModul").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static StareModul getStareModulByModulAndWeek(Modul modul, Saptamana saptamana){
		StareModul StareModul = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(StareModul.class).add(Restrictions.eq("modul" , modul))
					.add(Restrictions.eq("saptamana" , saptamana));
			StareModul = (StareModul)dc.getExecutableCriteria(session).uniqueResult();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return StareModul;
	}
	
	public static List<StareModul> getStareModulByModulesAndWeek(List<Modul> modul, Saptamana saptamana){
		List<StareModul> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(StareModul.class).add(Restrictions.in("modul" , modul))
					.add(Restrictions.eq("saptamana" , saptamana));
			list = dc.getExecutableCriteria(session).list();
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
				session.createQuery("delete from StareModul").executeUpdate();
				session.getTransaction().commit();
				done = true;
			}catch (Exception e) {
	            e.printStackTrace();           
	        } finally { 
	        	session.close();
	        }
		return done;
	}
	
	public static boolean resetStareSeq(){
		boolean done = false;
		Session session = null;
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.createSQLQuery("DROP SEQUENCE STAREMODUL_SEQ").executeUpdate();
				session.createSQLQuery("CREATE SEQUENCE STAREMODUL_SEQ MINVALUE 1 MAXVALUE 999999999999999999999999999"
						+ " START WITH 1 INCREMENT BY 14 NOCACHE NOCYCLE").executeUpdate();
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
