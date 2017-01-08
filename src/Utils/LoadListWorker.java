package Utils;

import javax.swing.SwingWorker;

import Services.DisciplinaService;
import Services.ModulService;
import Services.StudentService;
import Services.UtilizatorService;
import Singleton.Singleton;

public class LoadListWorker extends SwingWorker<String, Void> {

    @Override
    protected String doInBackground() throws Exception {
    	Singleton.getInstance().loadListsForNormalUser(Singleton.getInstance().currentUser);
    	Singleton.getInstance().loadListsForAdminUser();
    	return "Done";
    }
    
    
 
}