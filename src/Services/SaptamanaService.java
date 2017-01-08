package Services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import Singleton.Singleton;
import Utils.Functions;
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
	
	public static Saptamana getSaptamanaByDenumireAndSemestru(String denumire, String semestru){
		Saptamana saptamana = null;
		Session session = null;
		try{			
			session = Singleton.getInstance().getNewSession();			
			saptamana = (Saptamana) session.createQuery("from Saptamana where DENUMIRE = '" + denumire + "' AND ID_SEM = "
					+ "(select id from Semestru where numeSem = '" + semestru + "')").getResultList().get(0);
		}catch (Exception e) {
            e.printStackTrace();
        }finally { 
        	session.close();
        }
		return saptamana;
		
	}
		
	public static boolean addSaptamana(Semestru semestru, String denumire, Date startdate, Date enddate){
		boolean done = false;
		Session session = null;
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			Saptamana saptamana = new Saptamana(semestru,denumire,startdate,enddate);
			session.save(saptamana);
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();           
        } finally { 
        	session.close();
        }
		return done;
	}
		
	public static boolean deleteSaptamanaByID(int ID){
		boolean done = false;
		Saptamana saptamana = getSaptamanaByID(ID);
		Session session = null;
		if(saptamana != null){
			try{
				session = Singleton.getInstance().getNewSession();	
				session.beginTransaction();
				session.delete(saptamana);
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
	
	public static boolean updateSaptamanaByID(int ID,Semestru semestru, String denumire, Date startdate, Date enddate){
		boolean done = false;
		Session session = null;
		Saptamana saptamana = getSaptamanaByID(ID);
		if(saptamana != null){
			saptamana.setSemestru(semestru);
			saptamana.setDenumire(denumire);
			saptamana.setStartdate(startdate);
			saptamana.setEnddate(enddate);
			try{
				session = Singleton.getInstance().getNewSession();
				session.beginTransaction();
				session.update(saptamana);
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
	
	public static Saptamana getCurrentWeek(){
		Session session = null;
		Saptamana saptamana = null;
		Date currentDate = Calendar.getInstance().getTime();
		
		Saptamana temp = getSaptamanaByID(12);
		System.out.println(temp);
		
		try{
			session = Singleton.getInstance().getNewSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Saptamana.class).add(Restrictions.le("startdate" , currentDate))
																			.add(Restrictions.gt("enddate" , currentDate));
			saptamana = (Saptamana)dc.getExecutableCriteria(session).uniqueResult();
			
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return saptamana;
	}
	
	public static boolean updateSaptamaniForNewYear(Date date){
		Session session = null;
		boolean done = false;
		List<Saptamana> saptamani = null;
		
		
		try{
			session = Singleton.getInstance().getNewSession();
			session.beginTransaction();
			saptamani = getAllFromSaptamana();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Calendar easterDate = Functions.getEasterDate(calendar.get(Calendar.YEAR)+1);		
			for(Saptamana x : saptamani){
				helperUpdateSaptamaniMethod(session,x,calendar,easterDate);
			}
			session.getTransaction().commit();
			done = true;
		}catch (Exception e) {
            e.printStackTrace();        
        }finally{
        	session.close();
        }
		return done;
	}
	
	
	
	private static void helperUpdateSaptamaniMethod(Session session,Saptamana saptamana,Calendar calendar,Calendar easterDate){
		Date yearStartNextYear = calendar.getTime();
		yearStartNextYear.setYear(yearStartNextYear.getYear()+1);
		Calendar helper = Calendar.getInstance();
		helper.setTime(calendar.getTime());
		helper.add(Calendar.DATE, 7);
		
		Calendar helper2 = Calendar.getInstance();
		helper2.setTime(calendar.getTime());
		
		switch(saptamana.getId().intValueExact()){
			case 1:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 2:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 3:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 4:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 5:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 6:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 7:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 8:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 9:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 10:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 11:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 12:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 21);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				//calendar.add(Calendar.DATE, 14);
				break;
			case 13:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 14:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 35);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				//calendar.add(Calendar.DATE, 28);
				break;
			case 15:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 16:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 17:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;
			case 18:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 19:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 20:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 21:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 22:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 23:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 24:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 25:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 26:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 27:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 7);
				if(Functions.isDateBetween(easterDate.getTime(),helper2.getTime(),helper.getTime())){
					calendar.add(Calendar.DATE, 7);
				}
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				
				break;
			case 28:
				saptamana.setStartdate(calendar.getTime());
				calendar.add(Calendar.DATE, 150);
				saptamana.setEnddate(calendar.getTime());
				session.update(saptamana);
				break;

		}
	}

}
