package Services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

public class SaptamanaService {
	
	public static Saptamana getSaptamanaByID(int ID){
		Saptamana saptamana = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			saptamana = session.get(Saptamana.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return saptamana;
		
	}
		
	public static void addSaptamana(Semestru semestru, String denumire, Date startdate, Date enddate){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Saptamana saptamana = new Saptamana(semestru,denumire,startdate,enddate);
			session.save(saptamana);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
		
	public static void deleteSaptamanaByID(int ID){
		Saptamana saptamana = getSaptamanaByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();	
			session.beginTransaction();
			session.delete(saptamana);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateSaptamanaByID(int ID,Semestru semestru, String denumire, Date startdate, Date enddate){
		Session session = null;
		Saptamana saptamana = getSaptamanaByID(ID);
		
		saptamana.setSemestru(semestru);
		saptamana.setDenumire(denumire);
		saptamana.setStartdate(startdate);
		saptamana.setEnddate(enddate);
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.update(saptamana);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
	}
	
	public static List<Saptamana> getAllFromSaptamana(){
		List<Saptamana> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Saptamana").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}

}
