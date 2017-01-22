/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import allegro.domain.UserSettings;
import allegro.service.tasks.TaskResult;
import https.webapi_allegro_pl.service.ArrayOfItemslisttype;
import https.webapi_allegro_pl.service.ItemsListType;
import https.webapi_allegro_pl.service.UserInfoType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("request")
public class ResultGeneratorService implements Serializable {

    @Autowired
    private UserSettingsService userSettings;

    //Logger
    private static final Logger LOG = Logger.getLogger(ResultGeneratorService.class.getName());
    //Listy uzytkownikow przed i po sortowaniu
    private List<UserInfoType> sortedSellerInfoList;
    private List<UserInfoType> sellerList;

    // Mapa z listami przedmiotów poszczególnych użytkowników;
    // Kluczem jest nazwa uzytkownika
    // Wartościa jest lista przedmiotów danego użytkownika
    private Map<String, Map< String, List<ItemsListType>>> usersItemMapByTaskMap = new HashMap();

    public Map<String, Map<String, List<ItemsListType>>> getUsersItemMapByTaskMap() {
        return usersItemMapByTaskMap;
    }

    public void setUsersItemMapByTaskMap(Map<String, Map<String, List<ItemsListType>>> usersItemMapByTaskMap) {
        this.usersItemMapByTaskMap = usersItemMapByTaskMap;
    }

    public Map<String, List<ItemsListType>> getUsersItemMap() {
        return usersItemMap;
    }

    public void setUsersItemMap(Map<String, List<ItemsListType>> usersItemMap) {
        this.usersItemMap = usersItemMap;
    }
    Map< String, List<ItemsListType>> usersItemMap = new HashMap();

    // zaleznosc
    @Autowired
    ResultContaioner results;
    @Autowired
    OnePageResultContainer page;

    // inicjujemy tu dane
    public ResultGeneratorService() {
        this.sortedSellerInfoList = new ArrayList();
        this.sellerList = new ArrayList();
    }

    /**
     * Metoda przepisuje struktury ze sprzedawcami i zapisuje je w liscie
     * Struktur Lista ze sprzedawcami nastepnie jest czysczona za powtarzajacych
     * sie uzytkowników
     *
     * @return
     */
    public List<UserInfoType> getClearSellerList() {
        rewriteSellersFromTaskResult();

        try {

            // Nie możemy nic robić jeśli główna lista jest pusta więc
            if (isThereAnySeller()) {
                // Lecimy po glownej liscie

                for (UserInfoType sellerInfo : this.sellerList) {

                    int sellerInstance = 0;
                    // Jeśli nasza lista jest pusta dodajemy pierwszy element
                    if (this.sortedSellerInfoList.isEmpty()) {
                        this.sortedSellerInfoList.add(sellerInfo);

                    } else {

                        for (UserInfoType sortedInfo : this.sortedSellerInfoList) {

                            if (sellerInfo.getUserId() == sortedInfo.getUserId()) {
                                sellerInstance++;
                            }
                        }

                        if (sellerInstance == 0) {
                            this.sortedSellerInfoList.add(sellerInfo);
                        }

                    }
                }
            }
        } catch (NullPointerException ex) {
            LOG.info("Blad w generatorze listy NPE");

        }
        return this.sortedSellerInfoList;
    }

    private boolean isThereAnySeller() {
        return !this.sellerList.isEmpty();
    }

    private void rewriteSellersFromTaskResult() {
        /**
         * Przepisujemy Użytkownikow
         *
         */

        for (TaskResult task : results.getTaskResultList()) {

            for (ArrayOfItemslisttype arrayOfItems : task.getArrayOfItemsList()) {
                for (ItemsListType item : arrayOfItems.getItem()) {

                    addSellerToList(item);

                }
            }

        }
    }

    private void addSellerToList(ItemsListType item) {
        UserInfoType sellerInfo = item.getSellerInfo();
        if (sellerInfo != null) {
            this.sellerList.add(sellerInfo);
        }
    }

    /**
     * Metoda zwraca mape z przedmiotami uzytkowników gdzie kluczem jest nazwa
     * uzytkownika;
     */
    public Map<String, List<ItemsListType>> buildItemsMap() {
        this.usersItemMap.clear();

        for (UserInfoType user : this.getClearSellerList()) {
            /**
             * Lista produktów danego uzytkownika
             */
            List<ItemsListType> userItems = new ArrayList();

            /**
             * przeszukujemy zadania i listy ich zwrotek, szukamy przedmiotów od
             * konkretnych sprzedajacych
             */
            for (TaskResult task : results.getTaskResultList()) {
                try {
                    for (ArrayOfItemslisttype arrayOfItems : task.getArrayOfItemsList()) {
                        for (ItemsListType item : arrayOfItems.getItem()) {

                            /**
                             * Jeśli loginy sa rowne to to nasz prdzedmiot,
                             * dodajemy go do listy;
                             */
                            if (item.getSellerInfo().getUserLogin().equals(user.getUserLogin())) {
                                userItems.add(item);
                            }
                        }
                    }
                } catch (NullPointerException ex) {
                    //LOG.info("Wykryto nullowy element listy");
                }
            }
            // Dodajemy naszą listę produktów do mapy i przypisujemy jej odpowiedni klucz
            this.usersItemMap.put(user.getUserLogin(), userItems);

        }
        return this.usersItemMap;
    }

    public Map<String, Map< String, List<ItemsListType>>> buildItemsMapByTaskMap() {

        this.usersItemMapByTaskMap.clear();

        //Lista zaptań
//        List<String> queryList = new ArrayList();
        List<String> queryListClear = new ArrayList();
        /**
         * Pobieramy nazwy zadań i usuwamy powtarzajace sie
         */
        for (TaskResult task : results.getTaskResultList()) {

            int queryIdInstance = 0;
            // Jeśli nasza lista jest pusta dodajemy pierwszy element
            if (queryListClear.isEmpty()) {
                queryListClear.add(task.getQueryId());
            } else {

                /**
                 * Jeśli nie jest pusta to lecimy przez nia i sprawdzamy
                 */
                for (String clear : queryListClear) {
                    if (clear.equals(task.getQueryId())) {
                        queryIdInstance++;
                    }
                }
                if (queryIdInstance == 0) {
                    queryListClear.add(task.getQueryId());

                }
            }

        }

        /**
         * Tworzymy Mapę <Nazwa uzytkownika, Mapa * * * * *          * < Id wyszukiwania , Lista wynikow> > Dla każdego
         * uzytkownika musimy stworzyć mapę i przypisać Jej odpowiednią mapę z
         * Id wyszukiwania i listą wyników dla danego Id i Uzytkownika
         *
         * Lecimy najpierw po liscie sprzedawcow tworzymy nowa mape lecimy po
         * liscie z nazwami zadań
         *
         * wreszcie przeszukujemy zadania; jesli zadanie jest zadaniem z
         * konkretnym Id
         *
         */
        int i = 1;
        for (UserInfoType user : this.getClearSellerList()) {

//            LOG.info("BuildItemsByTaskMap: user: " + user.getUserLogin());
            Map< String, List<ItemsListType>> itemByTaskMap = new HashMap();
            /**
             * przeszukujemy zadania i listy ich zwrotek, szukamy przedmiotów od
             * konkretnych sprzedajacych
             */

            for (String taskId : queryListClear) {
                List<ItemsListType> userItems = new ArrayList();
                for (TaskResult task : results.getTaskResultList()) {
                    if (taskId.equals(task.getQueryId())) {

                        for (ArrayOfItemslisttype arrayOfItems : task.getArrayOfItemsList()) {
                            // LOG.info(arrayOfItems.toString());
                            for (ItemsListType item : arrayOfItems.getItem()) {

                                /**
                                 * Jeśli loginy sa rowne to to nasz prdzedmiot,
                                 * dodajemy go do listy;
                                 */
                                try {
                                    if (item.getSellerInfo().getUserLogin().equals(user.getUserLogin())) {
                                        userItems.add(item);
                                        //LOG.info("BuildItemsByTaskMap: Nr: " + i + " Tytuł: " + item.getItemTitle());
                                        i++;

                                        //LOG.info("dodaje przedmiot: " + item.getItemTitle());
                                    }

                                } catch (NullPointerException ex) {
//                                    LOG.info("BuildItemsByTaskMap: Wykryto nullowy element listy");
                                }
                            }
                        }

                    }
                }
                if (!userItems.isEmpty()) {
                    itemByTaskMap.put(taskId, userItems);
                }
            }
            // Dodajemy naszą listę produktów do mapy i przypisujemy jej odpowiedni klucz
            this.usersItemMapByTaskMap.put(user.getUserLogin(), itemByTaskMap);
        }
        return this.usersItemMapByTaskMap;
    }

    class ValueComparator implements Comparator<String> {

        Map<String, Integer> base;

        public ValueComparator(Map<String, Integer> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public void generatePages() {

        page.getBestSellersList().clear();
        page.getMapList().clear();
        buildItemsMapByTaskMap();

        /**
         * Musimy wiedzieć ile mamy w sumie zapytań; queryListCleat nam to
         * zalatwia
         */
        List<String> queryListClear = new ArrayList();
        /**
         * Pobieramy nazwy zadań i usuwamy powtarzajace sie
         */
        for (TaskResult task : results.getTaskResultList()) {

            int queryIdInstance = 0;
            // Jeśli nasza lista jest pusta dodajemy pierwszy element
            if (queryListClear.isEmpty()) {
                queryListClear.add(task.getQueryId());
            } else {

                /**
                 * Jeśli nie jest pusta to lecimy przez nia i sprawdzamy
                 */
                for (String clear : queryListClear) {
                    if (clear.equals(task.getQueryId())) {
                        queryIdInstance++;
                    }
                }
                if (queryIdInstance == 0) {
                    queryListClear.add(task.getQueryId());

                }
            }
        }

        /**
         * Dla każdego sprzedawcy (w pętli) sprawdzamy czy miał przedmioty z
         * konkretnych zapytan
         *
         */
        HashMap<String, Integer> bestSellersMap = new HashMap<String, Integer>();
        ValueComparator bvc = new ValueComparator(bestSellersMap);
        TreeMap<String, Integer> best_seller_sorted = new TreeMap<String, Integer>(bvc);

        for (int i = queryListClear.size(); i >= 0; i--) {
            for (String mainKey : this.usersItemMapByTaskMap.keySet()) {

                /**
                 * Jeśli ten uzytkownik ma przedmioty pasujace do tylu zapytań
                 * ile teraz wynosi i - jedziemy ilosci wszystkich zapytań aż do
                 * 1 przegrzebujemy listy i pobierane informacje o sprzedawcy
                 * zapisujemy do naszej listy wg ktorej później będziemy
                 * sortować
                 */
                if (this.usersItemMapByTaskMap.get(mainKey).keySet().size() == i) {

                    for (String taskKey : this.usersItemMapByTaskMap.get(mainKey).keySet()) {

                        /**
                         * Klucz glowny jest nazwą sprzedawcy a klucz zadania
                         * jest identyfikatorem zadania
                         */
                        for (ItemsListType item : this.usersItemMapByTaskMap.get(mainKey).get(taskKey)) {
                            if (item.getSellerInfo().getUserLogin().equals(mainKey)) {
                                /**
                                 * Jeśi w mapie nie ma jeszcze takiego
                                 * uzytkownika to dodajemy go w wartosci dodajmy
                                 * ilosc przedmiotów danego użytkownik aw danej
                                 * kategorii
                                 */
                                if (!bestSellersMap.containsKey(item.getSellerInfo().getUserLogin())) {
                                    bestSellersMap.put(item.getSellerInfo().getUserLogin(), this.usersItemMapByTaskMap.get(mainKey).get(taskKey).size());

                                }
                            }
                        }
                    }
                }
            }
        }

        // sortujemy mapę aby wziasc posortowany keySet wg ilosci przedmiotów
        best_seller_sorted.putAll(bestSellersMap);

        List<String> bestSellersList = new ArrayList();
        for (int i = queryListClear.size(); i >= 0; i--) {
            for (String mainKey : best_seller_sorted.keySet()) {

                if (this.usersItemMapByTaskMap.get(mainKey).keySet().size() == i) {

                    for (String taskKey : this.usersItemMapByTaskMap.get(mainKey).keySet()) {

                        /**
                         * Klucz glowny jest nazwą sprzedawcy a klucz zadania
                         * jest identyfikatorem zadania
                         */
                        for (ItemsListType item : this.usersItemMapByTaskMap.get(mainKey).get(taskKey)) {

                            if (item.getSellerInfo().getUserLogin().equals(mainKey)) {
                                //LOG.info("Nazwa uzytkowniaka po sortowaniu: " + item.getSellerInfo().getUserLogin() + " Ilość trafień: " + i);
                                // dodajemy naszych sprzedawców pokolei do listy wg trafności

                                if (!bestSellersList.contains(item.getSellerInfo().getUserLogin())) {
                                    bestSellersList.add(item.getSellerInfo().getUserLogin());
                                }
                            }
                        }
                    }
                }
            }
        }

        UserSettings logedUserOptions = userSettings.getLogedUserOptions();

        int itemsOnPage = 50;
        if (logedUserOptions != null) {
            try {
                itemsOnPage = Integer.parseInt(logedUserOptions.getItemsOnOnePage());
            } catch (NullPointerException ex) {
            }
        }

        int itemsCount = 0;

        List<ItemsListType> inTaskItemList = new ArrayList();

        int pageTestCount = 0;
        /**
         * na podstawie posortowanej mapy tworze mniesjze mapyy ktore mają tyle
         * przedmiotów ile okresliono w zmiennej itemsOnPage;
         */
        Map< String, List<ItemsListType>> itemByTaskMap = new HashMap();
        Map<String, Map< String, List<ItemsListType>>> mainMap = new HashMap();
        List< Map<String, Map< String, List<ItemsListType>>>> mapList = new ArrayList();
        for (Iterator<String> it = bestSellersList.iterator(); it.hasNext();) {
            String mainKey = it.next();
            //mainMap = new HashMap();
            for (String taskKey : this.usersItemMapByTaskMap.get(mainKey).keySet()) {
                //itemByTaskMap = new HashMap();
                for (ItemsListType item : this.usersItemMapByTaskMap.get(mainKey).get(taskKey)) {

                    itemsCount++;

                    /**
                     * Jeśli jest miejsce na stronie
                     */
                    if (itemsCount < itemsOnPage) {
                        inTaskItemList.add(item);
                        // LOG.info("Dodaje przedmiot: " + item.getItemTitle());

                        /**
                         * Jeśli strona się skończyła
                         */
                    } else {
                        itemsCount = 0;
                        inTaskItemList.add(item);
                        // LOG.info("Dodaje ostatni: " + item.getItemTitle());

                        pageTestCount++;
                        //LOG.info("Nr strony: " + pageTestCount);

                        itemByTaskMap.put(taskKey, inTaskItemList);
                        mainMap.put(mainKey, itemByTaskMap);
                        mapList.add(mainMap);

                        inTaskItemList = new ArrayList();
                        itemByTaskMap = new HashMap();
                        mainMap = new HashMap();
                    }
                }
                // if (itemsCount != 0) {
                itemByTaskMap.put(taskKey, inTaskItemList);
                inTaskItemList = new ArrayList();
                //mainMap.put(mainKey, itemByTaskMap);
                //itemByTaskMap = new HashMap();
                //}

            }

            if (it.hasNext()) {
                if (!itemByTaskMap.isEmpty()) {
                    mainMap.put(mainKey, itemByTaskMap);
                    itemByTaskMap = new HashMap();
                }
                /**
                 * jeśli to już koniec to dodajemy ostatnią stronę do lisy.
                 */
            } else {
                if (!itemByTaskMap.isEmpty()) {
                    mainMap.put(mainKey, itemByTaskMap);
                    itemByTaskMap = new HashMap();
                    mapList.add(mainMap);

                    LOG.info("Ostatnia strona!!!!");
                }
            }
        }

        LOG.info("Ilość stron: " + mapList.size());

        /**
         * Przekazujemy wyniki do naszego sesyjnego ziarenka reprezentujacego
         * wyniki w postaci stron
         */
        page.setBestSellersList(bestSellersList);
        page.setMapList(mapList);

    }

    public List<UserInfoType> getSortedSellerInfoList() {
        return sortedSellerInfoList;
    }

    public void setSortedSellerInfoList(List<UserInfoType> sortedSellerInfoList) {
        this.sortedSellerInfoList = sortedSellerInfoList;
    }

    public List<UserInfoType> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<UserInfoType> sellerList) {
        this.sellerList = sellerList;
    }

    public ResultContaioner getResults() {
        return results;
    }

    public void setResults(ResultContaioner results) {
        this.results = results;
    }

    /**
     *
     * Musimy wyłuskać wszystkich sprzedających tak aby się nie powtarzali
     * Musimy wyszukać przedmioty
     *
     */
}
