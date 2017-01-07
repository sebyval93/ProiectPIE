package Services;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import Singleton.Singleton;
import entity.*;

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
		
	public static boolean addProfesor(String nume){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Profesor profesor = new Profesor(nume);
			session.save(profesor);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deleteProfesorByID(int ID){
		boolean done = false;
		Profesor profesor = getProfesorByID(ID);
		Session session = null;
		if( profesor != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(profesor);
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
	
	public static boolean updateProfesorByID(int ID,String nume){
		boolean done = false;
		Session session = null;
		Profesor profesor = getProfesorByID(ID);
		if(profesor != null){
			profesor.setNume(nume);
			try{
				session = Singleton.getInstance().getNewSession();	
				session.beginTransaction();
				session.update(profesor);
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
	
	public static Profesor getProfesorByNume(String nume) {
		Profesor profesor = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			profesor = (Profesor) session.createQuery("from Profesor where nume = '" + nume + "'").getResultList().get(0);
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return profesor;
	}
	
	public static Profesor getProfesorFromUser(Utilizator user){
		Profesor profesor = null;
		System.out.println(user.getProfesor().getId());
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			String query = "from Profesor where ID = (select profesor.id from Utilizator where id = " + (user.getProfesor().getId().intValue() + 1) + ")";
			System.out.println(query);
			profesor = (Profesor) session.createQuery(query).getResultList().get(0);
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return profesor;
	}
	
	public static List<String> getAllNume(){
		List<String> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("select nume from Profesor").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<Profesor> runSearchQuery(String query) {
		List<Profesor> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery(query).getResultList();
			session.close();
		}catch (Exception e) {
            //e.printStackTrace();
			System.out.println("Profesor not found!");
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
				session.createQuery("delete from Profesor").executeUpdate();
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
