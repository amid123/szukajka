<%-- 
    Document   : filters
    Created on : 2015-04-22, 16:05:38
    Author     : ringo99<amid123@gmail.com>
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="https.webapi_allegro_pl.service.FiltersListType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page trimDirectiveWhitespaces="true" %>
<div id="filter_container">
    <form  id="filter_form" name="filter_form" >
        <%--definicje kotrolek--%> 
        <c:set var="inputTypeTextbox" value="textbox"/>
        <c:set var="inputTypeCombobox" value="combobox"/>
        <c:set var="inputTypeCheckbox" value="checkbox"/>

        <input type="hidden" id="queryId" name="queryId" value=""/>







        <c:forEach var="task" items="${taskResultList}" varStatus="taskStatus">
            
            <%-- Ten warunek jest niezbędny inaczej przy asynchronicznych zapytaniach miksują się
            filtry i wychodzi straszny młyn....--%>
            <c:if test="${searchQueryId.equals(task.queryId)}">

                <%--Lecimy po lscie  filtrów--%>
                <c:forEach var="listValue" items="${task.arrayOfFilters.item}" varStatus="filtesStatus">
                    <div class="one_filter_class">

                        <%--sprwadzamy czy filtr nie jest na liscie filtrow odrzuconych--%>
                        <c:set var="isFilterRejected" value="${false}"/>
                        <c:forEach items="${rejectedFilters}" var="rejectedFilter">
                            <c:if test="${rejectedFilter.equals(listValue.filterId)}">
                                <c:set var="isFilterRejected" value="${true}"/>
                            </c:if>
                        </c:forEach>
                        <c:if test="${isFilterRejected == false}">
                            <%-- jeśli lista nie jest pusta --%>
                            <c:if  test="${not empty lastRequestData}">
                                <%-- Iterujemy w celu sprawdzenia czy dany element 
                                    znajduje się na liscie filtrów posiadających wartość
                                    z poprzedniego przeładowania
                                --%>
                                <c:set var="isOnTheMap" value="${false}"/>
                                <c:forEach var="parameterMapElement" items="${lastRequestData}">
                                    <%-- Jeśli jest taki parametr --%>
                                    <c:if test="${parameterMapElement.key == listValue.filterId}" >
                                        <c:set var="isOnTheMap" value="${true}"/>
                                    </c:if>
                                </c:forEach>
                                <%-- 
                                    Jeśli dany filtr posiada wartość
                                --%>
                                <c:if test="${isOnTheMap}" >

                                    <%--
                                    Znajdujemy konkretny klucz z wartosciami filtra który teraz obsługujemy
                                    --%>
                                    <c:forEach var="parameterMapElement" items="${lastRequestData}">
                                        <%-- Jeśli to ten klucz o który nam chodzi --%>
                                        <%--Jeśli jest taki filtr na liśce to inicjujemy go wg mapy parametrow --%>
                                        <c:if test="${parameterMapElement.key == listValue.filterId}">

                                            <%--
                                                Teraz sprawdzamy pokolei czy typ kontrolki 
                                                i przechodzimi do obsługi konkretnego typu
                                                dla checkbox -checkbox dla inputow text itd;
                                            --%> 


                                            <%--
                                                ####################################
                                                ####################################
                                                Jesli to textbox to:
                                            --%> 
                                            <c:if  test="${listValue.filterControlType == inputTypeTextbox}">

                                                <%--Jeśli mamy filtr range od/do--%>
                                                <c:if test="${listValue.filterId != 'userId'}">
                                                    <c:if test="${listValue.filterIsRange == true}" >

                                                        <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>

                                                        <%--twrzymy jeden input ukryty z wartością range--%>
                                                        <input type="hidden" name="${listValue.filterId}" value="range" />





                                                        <div class="_ranged_from_class">
                                                            <label class="${listValue.filterId == 'price' ? "price1_lab_class": "range1_lab_class"}" for="${filterValue.filterValueId}"> Od</label>
                                                            <input class="${listValue.filterId == 'price' ? "price1_inp_class": "range1_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" value="${parameterMapElement.value[1]}" size="1" />

                                                        </div>
                                                        <div class="_ranged_to_class">



                                                            <label class="${listValue.filterId == 'price' ? "price2_lab_class": "range2_lab_class"}" for="${filterValue.filterValueId}"> Do</label>
                                                            <input class="${listValue.filterId == 'price' ? "price2_inp_class": "range2_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" value="${parameterMapElement.value[2]}" size="1" />
                                                        </div>



                                                    </c:if>
                                                    <%--Jeśli nie jest typu range--%> 
                                                    <c:if test="${listValue.filterIsRange != true}">
                                                        <c:if test="${ listValue.filterId != 'category' }">
                                                            <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                            <input id="${listValue.filterId}" type="text" name="${listValue.filterId}" value="${parameterMapElement.value[0]}" ${listValue.filterId == 'search' ? "required=\"required\"" : ""} />

                                                        </c:if>
                                                        <%--Jeśli to kategoria--%>
                                                        <c:if test="${ listValue.filterId == 'category' }" >

                                                            <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                            <div class="select_container_class">
                                                                <select  id="${listValue.filterId}" name="${listValue.filterId}">
                                                                    <option value="not_selected" selected>Wybierz z listy</option>
                                                                    <c:forEach items="${task.categoriesList.categoriesTree.item}" var="itemList" varStatus="itemStatus">
                                                                        <c:if test="${parameterMapElement.value[0] == itemList.categoryId}">
                                                                            <option  value="${itemList.categoryId}" selected> ${itemList.categoryName} (${itemList.categoryItemsCount})</option>
                                                                        </c:if>
                                                                        <c:if test="${parameterMapElement.value[0] != filterVal.filterValueId}">
                                                                            <option  value="${itemList.categoryId}"> ${itemList.categoryName} (${itemList.categoryItemsCount})</option>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>

                                                            </div>


                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                            </c:if>


                                            <%--
                                                ####################################
                                                ####################################
                                                Jeśli to combobox
                                            --%>
                                            <c:if  test="${listValue.filterControlType == inputTypeCombobox}">
                                                <c:if test="${listValue.filterId != 'special' && listValue.filterId != 'department'}">

                                                    <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                    <div class="select_container_class">
                                                        <select   id="${listValue.filterId}" name="${listValue.filterId}">
                                                            <option value="not_selected" selected>Wybierz z listy</option>
                                                            <c:forEach var="filterVal" items="${listValue.filterValues.item}" varStatus="filterValStatus">
                                                                <c:if test="${parameterMapElement.value[0] == filterVal.filterValueId}">
                                                                    <option  value="${filterVal.filterValueId}" selected> ${filterVal.filterValueName} </option>
                                                                </c:if>
                                                                <c:if test="${parameterMapElement.value[0] != filterVal.filterValueId}">
                                                                    <option  value="${filterVal.filterValueId}"> ${filterVal.filterValueName} </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>

                                                    </div>



                                                </c:if>
                                            </c:if>

                                            <%--
                                                ####################################
                                                ####################################
                                                Jeśli to CheckBox
                                            --%>   

                                            <c:if  test="${listValue.filterControlType == inputTypeCheckbox}">

                                                <%--
                                                    Jeśli filt jest wielowartościowy to tworzymy dla niego tytół
                                                    a wewnątrz zostawiamy lable
                                                --%>

                                                <c:if test="${not empty listValue.filterValues.item}">
                                                    <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>


                                                    <c:if test="${ not empty listValue.filterValues}">

                                                        <c:set var="isValueInList" value="${false}"/>
                                                        <%--przeszukujemy mape parametrow aby sprawdzic czy ktorys pasuje do listy--%>

                                                        <c:if test="${listValue.filterId.equals(parameterMapElement.key)}">
                                                            <c:forEach var="checkBoxVal" items="${listValue.filterValues.item}" >

                                                                <%--
                                                                    Jeśli w danym filtrze sa jakies zwtrotki to lecimy dalej
                                                                --%>
                                                                <c:if test="${checkBoxVal.filterValueCount != 0}">
                                                                    <c:set var="isChecked" value="${false}"  />
                                                                    <c:forEach  var="valueString" items="${parameterMapElement.value}" varStatus="checkStatus">
                                                                        <c:if   test="${checkBoxVal.filterValueId.equals(valueString)}">
                                                                            <c:set var="isChecked" value="${true}"  />
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <input id="${checkBoxVal.filterValueId}" type="checkbox" ${ isChecked ? "checked": ""} ${checkBoxVal.filterValueCount == 0 ? "disabled":""} name="${listValue.filterId}" value="${checkBoxVal.filterValueId}" />
                                                                    <label for="${checkBoxVal.filterValueId}"> 
                                                                        <span>

                                                                        </span> 
                                                                        ${checkBoxVal.filterValueName} (${checkBoxVal.filterValueCount})
                                                                    </label>
                                                                </c:if>

                                                            </c:forEach>
                                                        </c:if> 
                                                    </c:if> 
                                                </c:if>
                                                <c:if test="${empty listValue.filterValues.item}">
                                                    <input id="${listValue.filterId}" type="checkbox" ${parameterMapElement.value[0] == 'true' ?  "checked": ""}  name="${listValue.filterId}" value="true" />
                                                    <label for="${listValue.filterId}">
                                                        <span>

                                                        </span> ${listValue.filterName}


                                                    </label>
                                                </c:if>

                                            </c:if>
                                        </c:if> 
                                    </c:forEach>
                                </c:if>
                                <c:if test="${!isOnTheMap}">
                                    <%--Jesli to textbox to:--%> 
                                    <c:if  test="${listValue.filterControlType == inputTypeTextbox}">
                                        <c:if test="${listValue.filterId != 'userId'}">
                                            <%--Jeśli mamy filtr range od/do--%>
                                            <c:if test="${listValue.filterIsRange == true}" >
                                                <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>


                                                <input type="hidden" name="${listValue.filterId}" value="range" />

                                                <div class="_ranged_from_class">
                                                    <label class="${listValue.filterId == 'price' ? "price1_lab_class": "range1_lab_class"}" for="${filterValue.filterValueId}"> Od</label>
                                                    <input class="${listValue.filterId == 'price' ? "price1_inp_class": "range1_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" value="${parameterMapElement.value[1]}" size="1" />

                                                </div>
                                                <div class="_ranged_to_class">



                                                    <label class="${listValue.filterId == 'price' ? "price2_lab_class": "range2_lab_class"}" for="${filterValue.filterValueId}"> Do</label>
                                                    <input class="${listValue.filterId == 'price' ? "price2_inp_class": "range2_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" value="${parameterMapElement.value[2]}" size="1" />
                                                </div>



                                            </c:if>
                                            <%--Jeśli nie jest typu range--%> 
                                            <c:if test="${listValue.filterIsRange != true}">
                                                <c:if test="${ listValue.filterId != 'category' }">



                                                    <c:if test="${ listValue.filterId != 'search' }">
                                                        <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                    </c:if>



                                                    <input id="${listValue.filterId}" type="text" name="${listValue.filterId}" value="" ${listValue.filterId == 'search' ? "required=\"required\"" : ""} />

                                                </c:if>
                                                <%--Jeśli to kategoria--%>
                                                <c:if test="${ listValue.filterId == 'category' }" >


                                                    <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                    <div class="select_container_class">

                                                        <select  id="${listValue.filterId}" name="${listValue.filterId}">
                                                            <option value="not_selected" selected>Wybierz z listy</option>
                                                            <c:forEach items="${task.categoriesList.categoriesTree.item}" var="itemList" varStatus="itemStatus">
                                                                <option  value="${itemList.categoryId}"> ${itemList.categoryName} (${itemList.categoryItemsCount})</option>
                                                            </c:forEach>
                                                        </select>


                                                    </div>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                    <%--Jeśli to combobox--%>
                                    <c:if  test="${listValue.filterControlType == inputTypeCombobox}">
                                        <c:if test="${listValue.filterId != 'special' && listValue.filterId != 'department'}">


                                            <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                            <div class="select_container_class">

                                                <select   id="${listValue.filterId}" name="${listValue.filterId}">
                                                    <option value="not_selected" selected>Wybierz z listy</option>
                                                    <c:forEach var="filterVal" items="${listValue.filterValues.item}" varStatus="filterValStatus">
                                                        <option  value="${filterVal.filterValueId}"> ${filterVal.filterValueName} </option>
                                                    </c:forEach>
                                                </select>
                                            </div>




                                        </c:if>
                                    </c:if>
                                    <c:if  test="${listValue.filterControlType == inputTypeCheckbox}">
                                        <c:if test="${not empty listValue.filterValues.item}">
                                            <p id="check_param_filter_name_${listValue.filterId}"> 
                                                ${listValue.filterName} 
                                            </p>
                                        </c:if>
                                        <c:if test="${empty listValue.filterValues.item}">
                                            <input id="${listValue.filterId}" type="checkbox"  value="true"  name="${listValue.filterId}"/>
                                            <label for="${listValue.filterId}">
                                                <span>

                                                </span> ${listValue.filterName}
                                            </label>
                                        </c:if>
                                        <c:forEach var="checkBoxVal" items="${listValue.filterValues.item}" >
                                            <c:if test="${ not empty checkBoxVal.filterValueCount && checkBoxVal.filterValueCount > 0}">

                                                <input id="${checkBoxVal.filterValueId}" type="checkbox" name="${listValue.filterId}" value="${checkBoxVal.filterValueId}" />
                                                <label for="${checkBoxVal.filterValueId}">
                                                    <span>

                                                    </span> 
                                                    ${checkBoxVal.filterValueName} ( ${checkBoxVal.filterValueCount})
                                                </label>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:if>
                            </c:if>
                            <%-- jeśli lista jest pusta --%>
                            <c:if  test="${ empty lastRequestData}">
                                <%--Jesli to textbox to:--%> 
                                <c:if  test="${listValue.filterControlType == inputTypeTextbox}">
                                    <c:if test="${listValue.filterId != 'userId'}">
                                        <%--Jeśli mamy filtr range od/do--%>
                                        <c:if test="${listValue.filterIsRange == true}" >

                                            <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>

                                            <%--twrzymy jeden input ukryty z wartością range--%>
                                            <input type="hidden" name="${listValue.filterId}" value="range" />
                                            <div class="_ranged_from_class">
                                                <label class="${listValue.filterId == 'price' ? "price1_lab_class": "range1_lab_class"}" for="${filterValue.filterValueId}"> Od</label>
                                                <input class="${listValue.filterId == 'price' ? "price1_inp_class": "range1_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" size="1" />

                                            </div>
                                            <div class="_ranged_to_class">



                                                <label class="${listValue.filterId == 'price' ? "price2_lab_class": "range2_lab_class"}" for="${filterValue.filterValueId}"> Do</label>
                                                <input class="${listValue.filterId == 'price' ? "price2_inp_class": "range2_inp_class"}" id="${filterValue.filterValueId}" type="text" name="${listValue.filterId}" size="1" />
                                            </div>

                                        </c:if>
                                        <%--Jeśli nie jest typu range--%> 
                                        <c:if test="${listValue.filterIsRange != true}">
                                            <c:if test="${ listValue.filterId != 'category' }">
                                                <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                <input id="${listValue.filterId}" type="text" name="${listValue.filterId}" value="" ${listValue.filterId == 'search' ? "required=\"required\"" : ""}/>

                                            </c:if>
                                            <%--Jeśli to kategoria--%>
                                            <c:if test="${ listValue.filterId == 'category' }" >



                                                <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                                <div class="select_container_class">

                                                    <select  id="${listValue.filterId}" name="${listValue.filterId}">
                                                        <option value="not_selected" selected>Wybierz z listy</option>
                                                        <c:forEach items="${task.categoriesList.categoriesTree.item}" var="itemList" varStatus="itemStatus">
                                                            <option  value="${itemList.categoryId}"> ${itemList.categoryName} (${itemList.categoryItemsCount})</option>
                                                        </c:forEach>
                                                    </select>

                                                </div>

                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <%--Jeśli to combobox--%>
                                <c:if  test="${listValue.filterControlType == inputTypeCombobox}">
                                    <c:if test="${listValue.filterId != 'special' && listValue.filterId != 'department'}">
                                        <p class="filter_title_${listValue.filterId}"> ${listValue.filterName} </p>
                                        <div class="select_container_class">
                                            <select   id="${listValue.filterId}" name="${listValue.filterId}">
                                                <option value="not_selected" selected>Wybierz z listy</option>
                                                <c:forEach var="filterVal" items="${listValue.filterValues.item}" varStatus="filterValStatus">
                                                    <option  value="${filterVal.filterValueId}"> ${filterVal.filterValueName} </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </c:if>
                                </c:if>
                                <c:if  test="${listValue.filterControlType == inputTypeCheckbox}">
                                    <c:if test="${not empty listValue.filterValues.item}">
                                        <p id="check_param_filter_name_${listValue.filterId}"> ${listValue.filterName} </p>
                                    </c:if>
                                    <c:if test="${empty listValue.filterValues.item}">
                                        <input id="${listValue.filterId}" type="checkbox"  value="true"  name="${listValue.filterId}"/>
                                        <label for="${listValue.filterId}">
                                            <span>

                                            </span> 
                                            ${listValue.filterName}

                                        </label>
                                    </c:if>
                                    <c:forEach var="checkBoxVal" items="${listValue.filterValues.item}" >
                                        <input id="${checkBoxVal.filterValueId}" type="checkbox" name="${listValue.filterId}" value="${checkBoxVal.filterValueId}" />
                                        <label for="${checkBoxVal.filterValueId}"> 
                                            <span>

                                            </span> 
                                            ${checkBoxVal.filterValueName} 
                                        </label>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </c:if>
                    </div>


                </c:forEach>
            </c:if>

        </c:forEach>
        <%-- Sprawdzamy cze parametr search był pusty czy nie,
                jesli nie był pusty to pokazujemy listę z opcjami sortowania 
                dla danego filtra --%>
        <c:if  test="${not empty lastRequestData}">
            <c:set var="sort_val" value="not_selected" />
            <c:set var="sort_dir" value="not_selected" />

            <c:forEach var="parameterMapElement" items="${lastRequestData}">
                <%-- jesli to ten parametr to ustaiwamy  --%>
                <c:if test="${parameterMapElement.key == 'sort_type'}" >
                    <c:if test="${not empty parameterMapElement.value}" >
                        <c:set var="sort_val" value="${parameterMapElement.value[0]}" />
                    </c:if>
                </c:if>
                <c:if test="${parameterMapElement.key == 'sort_dir'}" >
                    <c:if test="${not empty parameterMapElement.value}" >
                        <c:set var="sort_dir" value="${parameterMapElement.value[0]}" />
                    </c:if>
                </c:if>
            </c:forEach>
            <c:forEach var="parameterMapElement" items="${lastRequestData}">
                <%-- jesli to ten parametr to ustaiwamy  --%>
                <c:if test="${parameterMapElement.key == 'search'}" >
                    <c:if test="${not empty parameterMapElement.value}" >




                        <p class="sort_select_title_class">Sortuj wg.</p>
                        <div class="select_container_class">
                            <select id="sort_select" name="sort_type">
                                <option ${ sort_val.equals("endingTime") ?"selected" : ""} value="endingTime">Czas do końca</option>
                                <option ${ sort_val.equals("startingTime") ?"selected" : ""} value="startingTime"> Czas rozpoczęcia </option>
                                <option ${ sort_val.equals("price") ?"selected" : ""} value="price">Cena</option>
                                <option ${ sort_val.equals("priceDelivery") ?"selected" : ""} value="priceDelivery">Cena z dostawą</option>
                                <option ${ sort_val.equals("popularity") ?"selected" : ""} value="popularity">Ilość złożonych ofert</option>
                                <option ${ sort_val.equals("name") ?"selected" : ""} value="name">Tytuł</option>
                                <option ${ sort_val.equals("relevance") ?"selected" : ""} value="relevance">Trafność</option>
                            </select>

                        </div>

                        <p class="sort_dir_title_class">Kierunek sortowania</p>
                        <div class="select_container_class">
                            <select id="sort_dir" name="sort_dir">
                                <option ${ sort_dir.equals("asc") ?"selected" : ""} value="asc">Rosnąco</option>
                                <option ${ sort_dir.equals("desc") ?"selected" : ""} value="desc">Malejąco</option>
                            </select>

                        </div>
                    </c:if>
                </c:if>
            </c:forEach>    
        </c:if>
    </form>
</div>