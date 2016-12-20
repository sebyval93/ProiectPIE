package Singleton;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Services.ProfesorService;
import Services.SaptamanaService;
import Utils.Week;
import entity.*;

public class Singleton {

	private static Singleton singleton = null;
	public SessionFactory sessionFactory;
	public Utilizator currentUser = null;
	public Profesor currentProfesor = null;
	public Saptamana currentSaptamana = null;
	public Week currentWeek = null;
	
	private Singleton(){
		initSingleton();
		getCurrentSession();
	}
	
	public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
	
	public void initSingleton(){
		buildSessionFactory();
		
	}
	
	public void getCurrentSaptamana(){
		currentSaptamana = SaptamanaService.getCurrentWeek();
	}
	
	public void setCurrentWeek(Week week) {
		currentWeek = week;
	}
	
	public void getCurrentProfesor() {
		currentProfesor = currentUser.getProfesor();
		if (currentProfesor.getId().intValue() == 0) {
			if (currentUser.getUsername().equals("admin"))
				System.out.println("Logat ca admin");
			else
				System.out.println("Eroare getCurrentProfesor");
		}
	}
	
	//Build the sessionFactory
	public void buildSessionFactory(){
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		sessionFactory = conf.buildSessionFactory();
	}
	
	//Get a session
	public Session getCurrentSession(){
		if(sessionFactory.getCurrentSession() == null){
			return getNewSession();
		}
		return sessionFactory.getCurrentSession();
	}
	
	public Session getNewSession(){
		return sessionFactory.openSession();
	}
	
}
