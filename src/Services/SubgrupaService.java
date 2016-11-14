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
	
	public static void addSubgrupa(Grupa grupa,String nume){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Subgrupa subgrupa = new Subgrupa(grupa,nume);
			session.save(subgrupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
	
	public static void deleteSubgrupaByID(int ID){
		Subgrupa subgrupa = getSubgrupaByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(subgrupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateSubgrupaByID(int ID,Grupa grupa,String nume){
		Session session = null;
		Subgrupa subgrupa = getSubgrupaByID(ID);
		subgrupa.setGrupa(grupa);
		subgrupa.setNume(nume);
		try{
			session = Singleton.getInstance().getNewSession();	
			session.beginTransaction();
			session.update(subgrupa);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
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

}
