package Services;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import entity.*;

public final class DisciplinaService {
	
	public static Disciplina getDisciplinaByID(int ID){
		Disciplina disciplina = null;
		Session session = null;
		try{
		    session = Singleton.getInstance().getNewSession();
		    disciplina = session.get(Disciplina.class,new BigDecimal(ID));	
		}catch (Exception e) {
            e.printStackTrace();
        } finally {
        	session.close();
        }
		return disciplina;
	}
		
	public static boolean addDisciplina(String denumire, int an, int orecurs, int orelab,
			int oreseminar, int oreproiect, String numeScurt){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Disciplina disciplina = new Disciplina(denumire,new BigDecimal(an),new BigDecimal(orecurs),
					new BigDecimal(orelab),new BigDecimal(oreseminar),new BigDecimal(oreproiect),numeScurt);
			session.save(disciplina);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deleteDisciplinaByID(int ID){
		boolean done = false;
		Disciplina disciplina = getDisciplinaByID(ID);
		Session session = null;
		if(disciplina != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(disciplina);
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
	
	public static boolean updateDisciplinaByID(int ID,String denumire, int an, int orecurs, int orelab,
			int oreseminar, int oreproiect, String numeScurt){
		boolean done = false;
		Session session = null;
		Disciplina disciplina = getDisciplinaByID(ID);
		if(disciplina != null){
			disciplina.setDenumire(denumire);
			disciplina.setAn(new BigDecimal(an));
			disciplina.setOrecurs(new BigDecimal(orecurs));
			disciplina.setOrelab(new BigDecimal(orelab));
			disciplina.setOreseminar(new BigDecimal(oreseminar));
			disciplina.setOreproiect(new BigDecimal(oreproiect));
			disciplina.setNumeScurt(numeScurt);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(disciplina);
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
	
	public static List<Disciplina> getAllFromDisciciplina(){
		List<Disciplina> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Disciplina").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<String> getAllDenumire() {
		List<String> list = null;
		Session session = null;
		
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("select denumire from Disciplina").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static Disciplina getDisciplinaByNumeScurt(String numeScurt){
		Session session = null;
		Disciplina disciplina = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Disciplina.class).add(Restrictions.in("numeScurt", numeScurt));
			disciplina =  (Disciplina)dc.getExecutableCriteria(session).uniqueResult();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return disciplina;
	}
	
	public static List<Disciplina> getDisciplineByAn(int an){
		Session session = null;
		List<Disciplina> list = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Disciplina.class).add(Restrictions.eq("an", new BigDecimal(an)));
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
				session.createQuery("delete from Disciplina").executeUpdate();
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
