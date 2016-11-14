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
		
	public static void addDisciplina(String denumire, int an, int orecurs, int orelab,
			int oreseminar, int oreproiect, String numeScurt){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Disciplina disciplina = new Disciplina(denumire,new BigDecimal(an),new BigDecimal(orecurs),
					new BigDecimal(orelab),new BigDecimal(oreseminar),new BigDecimal(oreproiect),numeScurt);
			session.save(disciplina);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
		
	public static void deleteDisciplinaByID(int ID){
		Disciplina disciciplina = getDisciplinaByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(disciciplina);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateDisciplinaByID(int ID,String denumire, int an, int orecurs, int orelab,
			int oreseminar, int oreproiect, String numeScurt){
		Session session = null;
		Disciplina disciciplina = getDisciplinaByID(ID);
		disciciplina.setDenumire(denumire);
		disciciplina.setAn(new BigDecimal(an));
		disciciplina.setOrecurs(new BigDecimal(orecurs));
		disciciplina.setOrelab(new BigDecimal(orelab));
		disciciplina.setOreseminar(new BigDecimal(oreseminar));
		disciciplina.setOreproiect(new BigDecimal(oreproiect));
		disciciplina.setNumeScurt(numeScurt);
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.update(disciciplina);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
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

	
}
