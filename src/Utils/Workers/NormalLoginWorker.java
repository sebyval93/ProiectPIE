package Utils.Workers;

import javax.swing.SwingWorker;

import Singleton.Singleton;

public class NormalLoginWorker extends SwingWorker<String, Void> {

    @Override
    protected String doInBackground() throws Exception {
    	Singleton.getInstance().loadListsForNormalUser(Singleton.getInstance().currentUser);
    	return "Done";
    }
}
