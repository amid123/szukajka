/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service.tasks;

import allegro.service.ResultContaioner;
import allegro.service.threads.SearchThreadBean;
import java.util.List;

/**
 *
 * @author arek
 */
public interface TaskManager {

    public void createNewTask(TaskParametersProvider params) throws InterruptedException;

    public void setThreadList(List<SearchThreadBean> threadList);

    public ResultContaioner getResultContainer();

    public void setResultContainer(ResultContaioner resultContainer);

    public int getMaxRedefinedResp();

    public void setMaxRedefinedResp(int maxRedefinedResp);
    
     public List<SearchThreadBean> getThreadList() ;
}
