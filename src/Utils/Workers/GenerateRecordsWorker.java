package Utils.Workers;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import org.hibernate.StatelessSession;

import Services.PrezentaService;
import Services.StudentService;
import Singleton.Singleton;
import entity.Modul;
import entity.Prezenta;
import entity.Saptamana;
import entity.Student;

public class GenerateRecordsWorker extends SwingWorker<String, Void> {
    @Override public String doInBackground() {
        int current = 0;
        int listSize = Singleton.getInstance().ListOfModules.size();
        PrezentaService.deleteAllFromTable();
        PrezentaService.resetPrezentaSeq();
		StatelessSession session = null;
			try{
				session = Singleton.getInstance().getStatelessSession();
				
				for(Saptamana saptamana : Singleton.getInstance().ListOfWeeks){
					session.beginTransaction();
					int contor = 0;
					System.out.println(saptamana.getDenumire());
					for(Modul modul : Singleton.getInstance().ListOfModules){
						contor = contor + 1;
						
						if(saptamana.getId().intValue() % 2 == 0){
							if(listSize/3 <= contor){
								current += 1;
								setProgress(current);
								contor = 0;
							}
						}else{
							if(listSize/4 <= contor){
								current += 1;
								setProgress(current);
								contor = 0;
							}
						}
						
						//Daca modul face parte din semestrul saptamanii
						if(saptamana.getSemestru().getId().intValue() 
								!= modul.getDisciplina().getSemestru().getId().intValue()){
							continue;
						}else{
							
							//Daca modulul este saptamanal
							if(modul.getInterval().intValue() == 0){
								
								List<Student> listOfParticipants 
										= StudentService.getStudentiFromParticipant(modul.getParticipanti());
								
								for(Student student : listOfParticipants){
									Prezenta prezenta = new Prezenta(modul,saptamana,student,0);								
									try{
										session.insert(prezenta);									
									}catch (Exception e) {
										e.printStackTrace();    
									}
								}
								System.out.println("Added for module " + modul);
								
								continue;							
							}
							//Daca modululul este in saptamanile impare
							if(modul.getInterval().intValue() == 1){
								//Daca saptamana este impara
								if(saptamana.getId().intValue() % 2 != 0){
									
									List<Student> listOfParticipants 
										= StudentService.getStudentiFromParticipant(modul.getParticipanti());
											
									for(Student student : listOfParticipants){
										Prezenta prezenta = new Prezenta(modul,saptamana,student,0);								
										try{
											session.insert(prezenta);									
										}catch (Exception e) {
											e.printStackTrace();    
										}
									}
									System.out.println("Added for module " + modul);
									
									continue;
								}
							}
							//Daca modululul este in saptamanile pare
							if(modul.getInterval().intValue() == 2){
								//Daca saptamana este para
								if(saptamana.getId().intValue() % 2 == 0){
									
									List<Student> listOfParticipants 
										= StudentService.getStudentiFromParticipant(modul.getParticipanti());
									
									for(Student student : listOfParticipants){
										Prezenta prezenta = new Prezenta(modul,saptamana,student,0);								
										try{
											session.insert(prezenta);									
										}catch (Exception e) {
											e.printStackTrace();    
										}
									}
									System.out.println("Added for module " + modul);
									
									continue;
								}
							}
							
						}
					}
					session.getTransaction().commit();					
				}
				
			}catch (Exception e) {
	            e.printStackTrace();           
	        } finally { 
	        	session.close();
	        }
   
        return "Done";
    }
    
    
}
