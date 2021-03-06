package Services;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	public static boolean addStudent(Subgrupa subgrupa,String nume){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Student student = new Student(subgrupa,nume);
			session.save(student);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
	
	public static boolean deleteStudentByID(int ID){
		boolean done = false;
		Student student = getStudentByID(ID);
		Session session = null;
		if(student != null){
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.delete(student);
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
	
	public static boolean updateStudentByID(int ID,Subgrupa subgrupa,String nume){
		boolean done = false;
		Session session = null;
		Student student = getStudentByID(ID);
		if(student != null){
			student.setSubgrupa(subgrupa);
			student.setNume(nume);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(student);
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
	
	public static Student getStudentByNume(String nume) {
		Student student = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			student = (Student) session.createQuery("from Student where nume = '" + nume + "'").getResultList().get(0);
			session.close();
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return student;
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
	
	public static Student getStudentByNumeAndSubgrupa(String nume, Subgrupa subgrupa) {
		Student student = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			student = (Student) session.createQuery("from Student where nume = '" + nume + "' and subgrupa.id =" + subgrupa.getId()).getResultList().get(0);
			session.close();
		}catch (Exception e) {
            //e.printStackTrace();
			System.out.println("Student not found!");
        }finally{
        	session.close();
        }
		return student;
		
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
	
	public static List<Student> getStudentiFromParticipant(String participant) {
		List<Student> studenti = null;
		
		if (Character.isLetter(participant.charAt(participant.length() - 1))) {
			//subgrupa
			Subgrupa subgrupa = SubgrupaService.getSubgrupaByNume(participant);
			studenti = StudentService.getAllStudentsBySubgrupa(subgrupa);
			
			return studenti;
		}
		else {
			//grupa
			Grupa grupa = GrupaService.getGrupaByNume(participant);
			studenti = StudentService.getAllStudentsByGrupa(grupa);
			
			return studenti;
		}
	}

	public static List<Student> runSearchQuery(String query) {
		List<Student> list = null;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			list = session.createQuery(query).getResultList();
			session.close();
		}catch (Exception e) {
            //e.printStackTrace();
			System.out.println("Student not found!");
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
				session.createQuery("delete from Student").executeUpdate();
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
