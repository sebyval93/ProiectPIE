package Singleton;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

import Services.AnUniversitarService;
import Services.DisciplinaService;
import Services.GrupaService;
import Services.ModulService;
import Services.ProfesorService;
import Services.SaptamanaService;
import Services.SemestruService;
import Services.StareModulService;
import Services.StudentService;
import Services.SubgrupaService;
import Services.UtilizatorService;
import Utils.Week;
import entity.*;

public class Singleton {

	private static Singleton singleton = null;
	public SessionFactory sessionFactory;
	public Utilizator currentUser = null;
	public Profesor currentProfesor = null;
	public Week currentWeek = null;
	public Saptamana currentWeekStatic;
	
	
	
	//		Normal user lists
	
	public List<AnUniversitar> ListOfYears;
	public List<Semestru> ListOfSemesters;
	public List<Saptamana> ListOfWeeks;
	public List<Grupa> ListOfGroups;
	public List<Subgrupa> ListOfSubgroups;
	public List<Modul> ListOfTeacherModules;
	public List<StareModul> ListOfModuleStats;
	
	// 		End
	
	//		Admin user lists
	
	public List<Profesor> ListOfTeachers;
	public List<Disciplina> ListOfDisciplines;
	public List<Student> ListOfStudents;
	public List<Modul> ListOfModules;
	public List<Utilizator> ListOfAccounts;
	
	//		End
	
	private Singleton(){
		initSingleton();
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
		currentWeek = new Week(SaptamanaService.getCurrentWeek());
		currentWeekStatic = currentWeek.getSaptamana();
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
	
	public StatelessSession getStatelessSession(){		
		return sessionFactory.openStatelessSession();
	}
	
	public Session getNewSession(){
		return sessionFactory.openSession();
	}
	
	
		public void loadListOfWeeks(){
			ListOfWeeks = SaptamanaService.getAllFromSaptamana();
		}
		
		public void loadListOfYears(){
			ListOfYears = AnUniversitarService.getAllFromAn();
		}
		
		public void loadListOfModulesForProfesor(Profesor x){
			ListOfTeacherModules = ModulService.getAllModulByProfesor(x);
		}
		
		public void loadListOfSemesters(){
			ListOfSemesters = SemestruService.getAllFromSemestru();
		}
		
		public void loadListOfGroups(){
			ListOfGroups = GrupaService.getAllFromGrupa();
		}
		
		public void loadListOfSubgroups(){
			ListOfSubgroups = SubgrupaService.getAllFromSubgrupa();
		}
			
		public void loadAllModules(){
			Singleton.getInstance().ListOfModules = ModulService.getAllFromModul();
		}
		
		public void loadAllDisciplines(){
			Singleton.getInstance().ListOfDisciplines = DisciplinaService.getAllFromDisciciplina();
		}
		
		public void loadAllAccounts(){
			Singleton.getInstance().ListOfAccounts = UtilizatorService.getAllFromUtilizator();
		}
		
		public void loadAllStudents(){
			Singleton.getInstance().ListOfStudents = StudentService.getAllFromStudent();
		}
		
		public void loadAllTeachers(){
			Singleton.getInstance().ListOfTeachers = ProfesorService.getAllFromProfesor();
		}
		
		public void loadModulStateForCurrentWeek(){
			if(!currentUser.getUsername().equals("admin")){
				List<Modul> modules = ModulService.getAllModulBySaptamanaAndProfesor(currentWeekStatic,currentProfesor);
				Singleton.getInstance().ListOfModuleStats = StareModulService.getStareModulByModulesAndWeek(modules, currentWeekStatic);
			}
		}
		
		public void loadListsForNormalUser(Utilizator u){
			loadListOfYears();
			loadListOfWeeks();
			loadListOfGroups();
			loadListOfSemesters();
			loadListOfSubgroups();
			loadListOfModulesForProfesor(u.getProfesor());
			loadModulStateForCurrentWeek();
		}
		
		public void loadListsForAdminUser(){
			loadAllStudents();
			loadAllTeachers();
			loadAllModules();
			loadAllDisciplines();
			loadAllAccounts();		
		}
		
	
}
