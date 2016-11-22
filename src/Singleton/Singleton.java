package Singleton;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Services.SaptamanaService;
import entity.*;

public class Singleton {

	private static Singleton singleton = null;
	public SessionFactory sessionFactory;
	public Utilizator currentUser = null;
	public Saptamana currentWeek = null;
	
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
	
	public void getCurrentWeek(){
		currentWeek = SaptamanaService.getCurrentWeek();
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
