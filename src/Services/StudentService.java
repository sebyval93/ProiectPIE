package Services;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import entity.*;

public final class StudentService {

	public static Student getStudentByID(int ID){
		Student student = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			student = session.get(Student.class,new BigDecimal(ID));
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return student;
		
	}
	
	
	public static void addStudent(Subgrupa subgrupa,String nume){
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Student student = new Student(subgrupa,nume);
			session.save(student);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
	}
	
	public static void deleteStudentByID(int ID){
		Student student = getStudentByID(ID);
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.delete(student);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        }finally { 
        	session.close();
        } 
	}
	
	public static void updateStudentByID(int ID,Subgrupa subgrupa,String nume){
		Session session = null;
		Student student = getStudentByID(ID);
		student.setSubgrupa(subgrupa);
		student.setNume(nume);
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			session.update(student);
			session.getTransaction().commit();
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		
	}
	
	public static List<Student> getAllFromStudent(){
		List<Student> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery("from Student").getResultList();
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<Student> getAllStudentsBySubgrupa(Subgrupa subgrupa){
		Session session = null;
		List<Student> list = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Student.class).add(Restrictions.eq("subgrupa", subgrupa));
			list =  dc.getExecutableCriteria(session).list();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<Student> getAllStudentsByGrupa(Grupa grupa){
		Session session = null;
		List<Student> list = null;
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Student.class).add(Restrictions.in("subgrupa", grupa.getSubgrupas()));
			list =  dc.getExecutableCriteria(session).list();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return list;
	}
	
	public static List<Student> getAllStudentsByAn(AnUniversitar an){
		
		List<Student> listOfStudents = new ArrayList();
		Set<Grupa> list = an.getGrupas();
		for(Grupa x : list){
			listOfStudents.addAll(getAllStudentsByGrupa(x));
		}
		return listOfStudents;
		
	}
	
	
	
}
