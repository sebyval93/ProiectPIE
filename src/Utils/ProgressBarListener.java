package Utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JProgressBar;

public class ProgressBarListener implements PropertyChangeListener  {

        private final JProgressBar progressBar;
        public ProgressBarListener(JProgressBar progressBar) {
            this.progressBar = progressBar;
            this.progressBar.setValue(0);
        }
        @Override public void propertyChange(PropertyChangeEvent e) {
            String strPropertyName = e.getPropertyName();
            if ("progress".equals(strPropertyName)) {
                progressBar.setIndeterminate(false);
                int progress = (Integer) e.getNewValue();
                progressBar.setValue(progress);
            }
        }
}


