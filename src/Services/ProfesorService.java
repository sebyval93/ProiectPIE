package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.Disciplina;
import entity.Modul;
import entity.Profesor;

public final class ProfesorService {

	public static Profesor getProfesorByID(int ID){
		Profesor profesor = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			profesor = session.get(Profesor.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return profesor;
		
	}
		
	public static void addProfesor(String nume){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Profesor profesor = new Profesor(nume);
			session.save(profesor);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
		
	public static void deleteProfesorByID(int ID){
		Profesor profesor = getProfesorByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(profesor);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateModulByID(int ID,String nume){
		Session session = null;
		Profesor profesor = getProfesorByID(ID);
		profesor.setNume(nume);
		try{
			session = Singleton.getInstance().getNewSession();	
			session.beginTransaction();
			session.update(profesor);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
	}
	
	public static List<Profesor> getAllFromProfesor(){
		List<Profesor> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Profesor").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
}
