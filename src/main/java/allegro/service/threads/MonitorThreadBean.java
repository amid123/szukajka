package allegro.service.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
// zajmuje monitor, czeka odpowiedni czas i go zwalnia
@Service
@Scope("prototype")
public class MonitorThreadBean extends Thread {
    private static final Logger LOG = Logger.getLogger(MonitorThreadBean.class.getName());

    SoapTimeMonitorService threadMonitor;

    @Autowired
    public void setThreadMonitor(SoapTimeMonitorService threadMonitor) {
        this.threadMonitor = threadMonitor;
    }

    @Override
    public synchronized void run() {
        this.threadMonitor.setTimeFree(false);
        try {
            Thread.sleep(threadMonitor.getTimeout());
        } catch (InterruptedException ex) {
            Logger.getLogger(MonitorThreadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.threadMonitor.setTimeFree(true);
    }
}
