package allegro.service.tasks.impl;

import allegro.service.tasks.TaskResult;
import allegro.service.ResultContaioner;
import allegro.service.tasks.SearchTaskType;
import allegro.service.tasks.TaskManager;
import allegro.service.tasks.TaskParametersProvider;
import allegro.service.threads.SearchThreadBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import allegro.tools.ProviderUtilBean;
import java.io.Serializable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99
 */
@Service
@Scope("request")
public class TaskManagerImpl implements Serializable, TaskManager {

    private static final Logger LOG = Logger.getLogger(TaskManagerImpl.class.getName());

    private List<SearchThreadBean> threadList;

    private ProviderUtilBean util;
    private int maxRedefinedResp;

    private SearchThreadBean searchThread;

    public TaskManagerImpl() {
        this.maxRedefinedResp = 1500;
        this.threadList = new ArrayList();
    }

    @Autowired
    ResultContaioner resultContainer;

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    public void setUtil(ProviderUtilBean util) {
        this.util = util;
    }

    private SearchThreadBean searchThread() {
        return this.beanFactory.getBean(SearchThreadBean.class);
    }

    public void createNewTask(TaskParametersProvider taskParameters) throws InterruptedException {

        if (isTaskTypeGetItemByFilter(taskParameters)) {

            search(taskParameters);
            addToThreadList();
            waitForSearchFinish();

            /**
             * Przed wyszukiwaniem musimy sie upewnic ze zwrotka z której
             * korzystamy pochodzi od odpowiedniego zapytania
             */
            TaskResult result = null;
            for (TaskResult t : this.resultContainer.getTaskResultList()) {
                if (t.getQueryId().equals(taskParameters.getQueryId())) {
                    result = t;
                    break;
                }
            }

            /**
             * Jeśli warunki przejdą to lecimy z wyszukiwaniem
             */
            int offsetIndex = 0;
            int offset = 0;
            if (isThereSomeResult(result)) {

                try {
                    if (isThereMoreResultsThenThenOneResultPart(result, taskParameters)) {
                        if (isThereIsLessResultsThenDefined(result)) {
                            offset = countOffsetSize(result, taskParameters);
                            searchAllOffsets(offset, offsetIndex, taskParameters);

                        } else {
                            LOG.info("Ilosc offsetow jest wieksza niz zdefiniowana");

                            /**
                             * Wyliczamy ilość offsetow jak powzyżej, jednak
                             * względem ilości zdefiniowanych zwrotek a nie
                             * wszystki zwrotek z allegro
                             *
                             */
                            if (this.maxRedefinedResp % taskParameters.getMaxResultsPerQuery() == 0) {
                                offset = (this.maxRedefinedResp / taskParameters.getMaxResultsPerQuery()) * taskParameters.getMaxResultsPerQuery();
                            } else {
                                offset = (this.maxRedefinedResp / taskParameters.getMaxResultsPerQuery()) * taskParameters.getMaxResultsPerQuery() + taskParameters.getMaxResultsPerQuery();
                            }

                            /**
                             *
                             * Lecimy przez wszystkie wyliczone offsety dopóki
                             * offsetIndex != offsets
                             */
                            do {
                                offsetIndex += taskParameters.getMaxResultsPerQuery();

                                search(offsetIndex, taskParameters);
                                addToThreadList();
                            } while (offsetIndex != offset);
                        }
                    } else {
                        LOG.info("There was less result data then defined");
                        LOG.info("At this time one part of result data is set to: " + taskParameters.getMaxResultsPerQuery());
                    }
                } catch (NullPointerException ex) {
                }
            }

            /**
             * we are geting filers only in code below
             */
        } else if (isThereTaskTypeGetFilterList(taskParameters)) {
            taskParameters.setSearchScopeSelector(4);
            search(taskParameters);
            addToThreadList();
        } else {
            LOG.info("Error in task manager unknow tast type: " + taskParameters.getTaskType());
        }
    }

    public static boolean isThereTaskTypeGetFilterList(TaskParametersProvider taskParameters) {
        return taskParameters.getTaskType().equals(SearchTaskType.TASK_TYPE_GET_FILTER_LIST);
    }

    public void search(int offsetIndex, TaskParametersProvider taskParameters) {
        // Zapisujemy nowy index
        //params.setSearchIndex(offsetIndex);
        searchThread = searchThread();

        searchThread.setIndexOffset(offsetIndex);

        searchThread.setParameters(taskParameters);
        searchThread.setResults(resultContainer);
        searchThread.start();
    }

    public void searchAllOffsets(int offset, int offsetIndex, TaskParametersProvider taskParameters) {
        if (offset > 0) {
            do {
                offsetIndex += taskParameters.getMaxResultsPerQuery();
                taskParameters.setSearchIndex(offsetIndex);

                search(taskParameters);
                addToThreadList();

            } while (offsetIndex != offset);
        }

        /**
         * Jeśli jest więcej niż zadeklarowano Obliczamy ilość indexów wg.
         * zadeklarowanej maksymalnej ilości zwrotek I pobieramy tylko tą
         * wyliczoną ilość Resztę zostawiamy;
         */
    }

    public int countOffsetSize(TaskResult result, TaskParametersProvider taskParameters) {
        int offsetsCount;
        /**
         * Wyliczamy ilośc ofsetów
         */
        if (isThereItemsCountFitsDefinedMaxResults(result, taskParameters)) {
            offsetsCount = setOffsetAsOne(result, taskParameters);
        } else {
            offsetsCount = setOffsetAsOneMore(result, taskParameters);
        }
        return offsetsCount;
    }

    public int setOffsetAsOne(TaskResult result, TaskParametersProvider taskParameters) {
        return (result.getItemsCount() / taskParameters.getMaxResultsPerQuery()) * taskParameters.getMaxResultsPerQuery();
    }

    public int setOffsetAsOneMore(TaskResult result, TaskParametersProvider taskParameters) {
        return (result.getItemsCount() / taskParameters.getMaxResultsPerQuery()) * taskParameters.getMaxResultsPerQuery() + taskParameters.getMaxResultsPerQuery();
    }

    public static boolean isThereItemsCountFitsDefinedMaxResults(TaskResult result, TaskParametersProvider taskParameters) {
        return result.getItemsCount() % taskParameters.getMaxResultsPerQuery() == 0;
    }

    public boolean isThereIsLessResultsThenDefined(TaskResult result) {
        return result.getItemsCount() < this.maxRedefinedResp;
    }

    public static boolean isThereMoreResultsThenThenOneResultPart(TaskResult result, TaskParametersProvider params) {
        return result.getItemsCount() > params.getMaxResultsPerQuery();
    }

    public static boolean isThereSomeResult(TaskResult result) {
        return result != null;
    }

    public void waitForSearchFinish() throws InterruptedException {
        do {

            for (int i = 0; i < this.threadList.size(); i++) {
                SearchThreadBean thread = this.threadList.get(i);
                if (!thread.isAlive()) {
                    this.threadList.remove(i);
                }
            }
            Thread.sleep(50);
        } while (!this.threadList.isEmpty());
    }

    private void addToThreadList() {
        this.threadList.add(searchThread);
    }

    private void search(TaskParametersProvider params) {
        // Szukamy pierwszego wyniku
        searchThread = searchThread();
        searchThread.setParameters(params);
        searchThread.setResults(resultContainer);
        searchThread.start();
    }

    private static boolean isTaskTypeGetItemByFilter(TaskParametersProvider params) {
        return params.getTaskType().equals(SearchTaskType.TASK_TYPE_GET_ITEM_LIST_BY_FILTER);
    }

    public List<SearchThreadBean> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<SearchThreadBean> threadList) {
        this.threadList = threadList;
    }

    public ResultContaioner getResultContainer() {
        return resultContainer;
    }

    public void setResultContainer(ResultContaioner resultContainer) {
        this.resultContainer = resultContainer;
    }

    public int getMaxRedefinedResp() {
        return maxRedefinedResp;
    }

    public void setMaxRedefinedResp(int maxRedefinedResp) {
        this.maxRedefinedResp = maxRedefinedResp;
    }
}
