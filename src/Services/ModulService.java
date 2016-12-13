package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

public final class ModulService {
	
	public static Modul getModulByID(int ID){
		Modul modul = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			modul = session.get(Modul.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return modul;
		
	}
	
	public static boolean addModul(Disciplina disciplina, Profesor profesor, String activitate, String participanti,
			int interval){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Modul modul = new Modul(disciplina,profesor,activitate,participanti,new BigDecimal(interval));
			session.save(modul);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteModulByID(int ID){
		boolean done = false;
		Modul modul = getModulByID(ID);
		Session session = null;
		if(modul != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(modul);
				session.getTransaction().commit();
			}catch (Exception e) {
	            e.printStackTrace();           
	        }finally { 
	        	session.close();
	        } 
		}
		return done;
	}
	
	public static boolean updateModulByID(int ID,Disciplina disciplina, Profesor profesor, String activitate, String participanti,
			int interval){
		boolean done = false;
		Session session = null;
		Modul modul = getModulByID(ID);
		if (modul != null){
			modul.setDisciplina(disciplina);
			modul.setProfesor(profesor);
			modul.setActivitate(activitate);
			modul.setParticipanti(participanti);
			modul.setInterval(new BigDecimal(interval));
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
	
	public static List<Modul> getAllModulByProfesor(Profesor profesor) {
		List<Modul> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery(("from Modul where ID_PROFESOR = " + profesor.getId())).getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<Modul> getAllFromModul(){
		List<Modul> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Modul").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}

}
