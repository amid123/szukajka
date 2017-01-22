/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import allegro.service.tasks.TaskParametersProvider;
import allegro.service.tasks.TaskResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author arek
 */
// Ziarno typu singleton. dostepna ta sama instancja dla wszystkich wywołołań
// Przechowuje listę wyników wyszukiwania
@Service
@Scope("session")
public class ResultContaioner implements Serializable {

    private List<TaskResult> taskResultList;
    private List<TaskResult> taskResultListCopy;
    private List<TaskParametersProvider> taskParameterList;

    private boolean firstTask;
    private int lastUsedFilterId;
    private Map<String, List<String>> paramsMap0;
    private Map<String, List<String>> paramsMap1 = new HashMap();
    private Map<String, List<String>> paramsMap2 = new HashMap();
    private Map<String, List<String>> paramsMap3 = new HashMap();
    private Map<String, List<String>> paramsMap4 = new HashMap();

    public List<TaskParametersProvider> getTaskParameterList() {
        return taskParameterList;
    }

    public void setTaskParameterList(List<TaskParametersProvider> taskParameterList) {
        this.taskParameterList = taskParameterList;
    }

    public boolean isFirstTask() {
        return firstTask;
    }

    public void setFirstTask(boolean firstTask) {
        this.firstTask = firstTask;
    }

    public List<TaskResult> getTaskResultList() {
        return taskResultList;
    }

    public void setTaskResultList(List<TaskResult> taskResultList) {
        this.taskResultList = taskResultList;
    }

    public List<TaskResult> getTaskResultListCopy() {
        return taskResultListCopy;
    }

    public void setTaskResultListCopy(List<TaskResult> taskResultListCopy) {
        this.taskResultListCopy = taskResultListCopy;
    }

    public ResultContaioner() {
        this.taskResultListCopy = new ArrayList();
        this.lastUsedFilterId = -1;

        this.taskParameterList = new ArrayList();
        this.paramsMap0 = new HashMap();
        this.firstTask = false;
        this.taskResultList = new ArrayList();
    }

    public void clearAllResults() {

        this.taskResultList = new ArrayList();
    }

    public Map<String, List<String>> getParamsMap0() {
        return paramsMap0;
    }

    public void setParamsMap0(Map<String, List<String>> paramsMap0) {
        this.paramsMap0 = paramsMap0;
    }

    public Map<String, List<String>> getParamsMap1() {
        return paramsMap1;
    }

    public void setParamsMap1(Map<String, List<String>> paramsMap1) {
        this.paramsMap1 = paramsMap1;
    }

    public Map<String, List<String>> getParamsMap2() {
        return paramsMap2;
    }

    public void setParamsMap2(Map<String, List<String>> paramsMap2) {
        this.paramsMap2 = paramsMap2;
    }

    public Map<String, List<String>> getParamsMap3() {
        return paramsMap3;
    }

    public void setParamsMap3(Map<String, List<String>> paramsMap3) {
        this.paramsMap3 = paramsMap3;
    }

    public Map<String, List<String>> getParamsMap4() {
        return paramsMap4;
    }

    public void setParamsMap4(Map<String, List<String>> paramsMap4) {
        this.paramsMap4 = paramsMap4;
    }

    public int getLastUsedFilterId() {
        return lastUsedFilterId;
    }

    public void setLastUsedFilterId(int lastUsedFilterId) {
        this.lastUsedFilterId = lastUsedFilterId;
    }

}
