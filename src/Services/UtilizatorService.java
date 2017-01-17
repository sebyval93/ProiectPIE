package Services;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import Utils.EncryptService;
import Utils.Functions;
import entity.*;

public final class UtilizatorService {

	
	public static Utilizator getUtilizatorByID(int ID){
		Utilizator utilizator = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			utilizator = session.get(Utilizator.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return utilizator;
		
	}
	
	public static Utilizator getUtilizatorByUsername(String	username){
		Utilizator utilizator = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			DetachedCriteria dc = DetachedCriteria.forClass(Utilizator.class).add(Restrictions.eq("username" , username));
			utilizator = (Utilizator)dc.getExecutableCriteria(session).uniqueResult();
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return utilizator;
		
	}
	
	public static boolean addUtilizator(String username, String password,Profesor profesor){
		boolean done = false;
		Session session = null;
		Utilizator utilizator;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			if(Functions.stringIsNullOrEmpty(password)){
				utilizator = new Utilizator(username,password,profesor);
			}else{
				utilizator = new Utilizator(username,EncryptService.getHashOfString(password),profesor);
			}
			session.save(utilizator);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteUtilizatorByID(int ID){
		boolean done = false;
		Utilizator utilizator = getUtilizatorByID(ID);
		Session session = null;
		if(utilizator != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(utilizator);
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
	
	public static boolean updateUtilizator(int ID,String username, String password,Profesor profesor) throws NoSuchAlgorithmException{
		boolean done = false;
		Session session = null;
		Utilizator utilizator = getUtilizatorByID(ID);
		if(utilizator != null){
			utilizator.setUsername(username);
			try {
				utilizator.setPassword(EncryptService.getHashOfString(password));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			utilizator.setProfesor(profesor);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(utilizator);
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
	
	public static void generateAccounts(){
		deleteAllFromUtilizator();
		Functions.resetSequence("utilizator_seq");
		List<Profesor> profesori = ProfesorService.getAllFromProfesor();
		for(Profesor p : profesori){
			if(p.getNume().equals("admin"))
				continue;
			generateAccount(p);
		}
	}
	
	public static boolean deleteAllFromUtilizator(){
		Session session = null;
		boolean done = false;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.createQuery("delete from Utilizator where id <> 0").executeUpdate();
			session.getTransaction().commit();
			done = true;
		}catch(Exception e){
			  e.printStackTrace();   
		}finally{
			session.close();
		}
		return done;
	}
	
	public static boolean generateAccount(Profesor p){
		boolean done = false;
		if(p != null){
				String[] split = p.getNume().split(" ");
				String username = split[1].charAt(0) + split[0];
				done = addUtilizator(username, null, p);			
		}
		return done;
	}
	
	public static void resetPassword(String username) {
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.createQuery("update Utilizator set password=NULL where username = '" + username + "'").executeUpdate();
			session.getTransaction().commit();
		}catch(Exception e){
			  e.printStackTrace();   
		}finally{
			session.close();
		}
	}
	
	public static List<Utilizator> getAllFromUtilizator(){
		List<Utilizator> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Utilizator").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
}
