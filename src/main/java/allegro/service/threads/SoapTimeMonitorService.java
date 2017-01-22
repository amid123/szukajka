/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.threads;


import java.util.logging.Logger;
import allegro.tools.ProviderUtilBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("singleton")
public class SoapTimeMonitorService {
    private static final Logger LOG = Logger.getLogger(SoapTimeMonitorService.class.getName());

    
    private boolean timeFree;
    private long timeout;

    ProviderUtilBean util;

    // Zwraca true jeśli powiodło się zajęcie motnitora
    public synchronized boolean  activateTimeFlag() {
        if (this.timeFree) {
            this.newInstanceOfMonitorThread().start();
            return true;
        } else {
            return false;
        }
    }

    private MonitorThreadBean newInstanceOfMonitorThread() {
        return util.getBeanByType(MonitorThreadBean.class);
    }

    public ProviderUtilBean getUtil() {
        return util;
    }

    @Autowired
    public void setUtil(ProviderUtilBean util) {
        this.util = util;
    }

    public boolean isTimeFree() {
        return timeFree;
    }

    public synchronized void setTimeFree(boolean timeFree) {
        this.timeFree = timeFree;
        if(timeFree)
            LOG.info("### Zwolniono czas");
        else
            LOG.info("### Zajeto czas");
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public SoapTimeMonitorService() {
        // Domyślna wartość to 9ms
        // bo 1s/120 zapytań = 0.008333
        // czyli zaokraglamy do 9
        this.timeout = 50;
        this.timeFree = true;
    }
}
