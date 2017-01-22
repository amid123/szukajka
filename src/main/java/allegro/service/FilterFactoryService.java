/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.service;

import https.webapi_allegro_pl.service.ArrayOfFilteroptionstype;
import https.webapi_allegro_pl.service.ArrayOfString;
import https.webapi_allegro_pl.service.FilterOptionsType;
import https.webapi_allegro_pl.service.RangeValueType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ringo99<amid123@gmail.com>
 */
@Service
@Scope("prototype")
public class FilterFactoryService implements Serializable {

    ArrayOfFilteroptionstype filters;
    private static final Logger LOG = Logger.getLogger(FilterFactoryService.class.getName());

    @Autowired
    ResultContaioner results;

    public ArrayOfFilteroptionstype fabricateFiltersFormRequestData(HttpServletRequest request, String searchId) {
        //### TESTUJEMY PRZEKAZYWANIE PARAMETRÓW REQUESTA DO PAMĘTAIA STANU FORMULARZA
        //################################################################
        // Czyścimy po poprzednim 

        if (searchId.equals("search_query_0")) {
            if (!this.results.getParamsMap0().isEmpty()) {
                this.results.getParamsMap0().clear();
            }
        } else if (searchId.equals("search_query_1")) {
            if (!this.results.getParamsMap1().isEmpty()) {
                this.results.getParamsMap1().clear();
            }
        } else if (searchId.equals("search_query_2")) {
            if (!this.results.getParamsMap2().isEmpty()) {
                this.results.getParamsMap2().clear();
            }
        } else if (searchId.equals("search_query_3")) {
            if (!this.results.getParamsMap3().isEmpty()) {
                this.results.getParamsMap3().clear();
            }
        } else if (searchId.equals("search_query_4")) {
            if (!this.results.getParamsMap4().isEmpty()) {
                this.results.getParamsMap4().clear();
            }
        }

        if (this.filters == null) {
            filters = new ArrayOfFilteroptionstype();
        }
        // Konwertujemy enumeracje - shitowy objekt
        List<String> paramNames = Collections.list(request.getParameterNames());

        // Przegladamy nazwy parametrów
        for (String filterId : paramNames) {
            // tworzymy listę parametrów do przechowywania wiecej niż jednej wartosci parametru
            List<String> parameterValues = new ArrayList();
            parameterValues = Arrays.asList(request.getParameterValues(filterId));

            LOG.info("FilterFactory: parametry: " + filterId);
            // Inicjujemy mapę 
            List<String> ps = new ArrayList();
            for (String p : parameterValues) {
                ps.add(p);

            }

            if (searchId.equals("search_query_0")) {
                this.results.getParamsMap0().put(filterId, ps);
            } else if (searchId.equals("search_query_1")) {
                this.results.getParamsMap1().put(filterId, ps);
            } else if (searchId.equals("search_query_2")) {
                this.results.getParamsMap2().put(filterId, ps);
            } else if (searchId.equals("search_query_3")) {
                this.results.getParamsMap3().put(filterId, ps);
            } else if (searchId.equals("search_query_4")) {
                this.results.getParamsMap4().put(filterId, ps);
            }

            //LOG.info("mapa parametrów"+this.results.getParamsMap());
            //################################################################
            FilterOptionsType filter = new FilterOptionsType();
            ArrayOfString valuesArray = new ArrayOfString();

            // Przypadek gdy mamy wiecej niż jedną wartość
            if (parameterValues.size() >= 2) {

                boolean isRange = false;

                // Sprawdzamy czy mamy wartosc range na pokładzie :D
                for (String p : parameterValues) {
                    if ("range".equals(p)) {
                        isRange = true;
                    }
                }

                // Tworzymy instancje range
                RangeValueType range = new RangeValueType();
                // Jeśli parametr jest typu range
                if (isRange) {
                    // LOG.info("Filtr multi  Range:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    // lecimy przez parametry i przypisujemy kolejno 
                    // parametr 1 do RangeMin i parametr 2 do RangeMax
                    // parametr 0 mowi o tym ze to jest range;
                    for (int i = 0; i < parameterValues.size(); i++) {
                        if (i == 1) {
                            range.setRangeValueMin(parameterValues.get(i));
                        }
                        if (i == 2) {
                            range.setRangeValueMax(parameterValues.get(i));
                        }
                    }
                    // ustawiamy kolejno ID i Range
                    filter.setFilterId(filterId);
                    filter.setFilterValueRange(range);
                    if (parameterValues.get(2).isEmpty() && parameterValues.get(1).isEmpty()) {

                        if (parameterValues.get(1).isEmpty()) {
                            continue;
                        }
                    } else if (parameterValues.get(2).isEmpty() && !parameterValues.get(1).isEmpty()) {
                        range.setRangeValueMax("99999999");
                        filter.setFilterValueRange(range);
                        this.filters.getItem().add(filter);
                    } else if (!parameterValues.get(2).isEmpty() && parameterValues.get(1).isEmpty()) {
                        range.setRangeValueMin("0");
                        filter.setFilterValueRange(range);
                        this.filters.getItem().add(filter);

                    } else {
                        this.filters.getItem().add(filter);
                    }
                } // A jeśli nie jest range
                else {
                    //LOG.info("Filtr multi NIE range:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    for (String str : parameterValues) {
                        if (str.isEmpty()) {
                            LOG.info("Parametry z MULTI: " + "empty");
                        } else {
                            LOG.info("Parametry z MULTI: " + str);
                        }
                    }
                    // TODO:
                    // filtry z wieloma parametrami nie range - jeśli takie w ogóle są
                    // to obsługujemy je właśnie tutaj

                }
                // Jeśli jest jedena wartosc parametru
            } else {
                // LOG.info("Domyślny filtr prosty:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if (!request.getParameter(filterId).isEmpty()
                        && !"not_selected".equals(request.getParameter(filterId))) {
                    //LOG.info("Parametr: " + filterId + " wartosc: " + request.getParameter(filterId));

//                    FilterOptionsType filter = new FilterOptionsType();
                    // Inicjujemy id
                    filter.setFilterId(filterId);
                    // Tablica wartości
//                    ArrayOfString valuesArray = new ArrayOfString();
                    // Pobieramy wartość dla danego filtra
                    valuesArray.getItem().add(request.getParameter(filterId));
                    filter.setFilterValueId(valuesArray);
                    this.filters.getItem().add(filter);
                }
            }

            //this.filters.getItem().add(filter);
            LOG.info(filterId + " " + request.getParameter(filterId));
            //}
            // Dodajemy do wyszukiwania tylko filtr który został wypełniony

//                if (!request.getParameter(filterId).isEmpty()
//                        && !"not_selected".equals(request.getParameter(filterId))) {
//                    //LOG.info("Parametr: " + filterId + " wartosc: " + request.getParameter(filterId));
//                    FilterOptionsType filter = new FilterOptionsType();
//                    // Inicjujemy id
//                    filter.setFilterId(filterId);
//                    // Tablica wartości
//                    ArrayOfString valuesArray = new ArrayOfString();
//                    // Pobieramy wartość dla danego filtra
//                    valuesArray.getItem().add(request.getParameter(filterId));
//                    filter.setFilterValueId(valuesArray);
//                    this.filters.getItem().add(filter);
//                }
        }

        return this.filters;
    }
}
