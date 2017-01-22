<%-- 
    Document   : searchpage
    Created on : 2015-04-22, 12:47:56
    Author     : ringo99<amid123@gmail.com>
--%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta charset="utf-8">
        <meta name="description" content="">
        <meta name="author" content="G3 IT Solutions">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Kupujemy od jednego</title>

        <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
        <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script src="<c:url value="/resources/js/main.js"/>"></script>
        
        <link href="<c:url value="/resources/css/loader.css" />" rel="stylesheet"/>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:700,300,600&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">	 
    </head>
    <body>
        <header class="container">
            <div class="row head">
                <div class="col-md-3 col-xs-12 col-sm-12 text-center logo-container">
                    <a href="./search" title="Strona główna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
                </div>
                <div class="col-md-9 col-xs-12 text-right menu">
                    <p>Zalogowany jako, <span>${logedUser}</span></p>
                    <nav>
                        <ul>
                            <li><a href="./logout" class="menu_button">Wyloguj</a>	</li>	
                            <li><div id="basket_menu_elem"><a id="basket_a" class="basket menu_button" >Koszyk</a></div>
                                <div id="basket_container" style="display: none">
                                    <h4>Koszyk jest pusty</h4>
                                </div>
                            </li>
                            <li><a class="menu_button" >Ustawienia</a></li>
                            <li><a class="menu_button">Wyszukuj</a>	</li>	
                        </ul>
                    </nav>				
                </div>
            </div>	
        </header>
        <section class="container">
            <div class="row searcher">
                <div class="col-md-12 clearfix">
                    <h3>Wyszukiwarka kilku przedmiotów</h3>
                    <div id="filter_box">

                        <div id="form_dynamic_content" >
                        </div>
                    </div>
                    <aside>
                        <a id="add_query_button" class="addNew" ><img src="<c:url value="/resources/img/plus.png"/>" alt=""/>Dodaj kolejny</a>
                        <a href="./dosearch.view" class="batn search"><i  class="glyphicon glyphicon-search"></i>Wyszukaj</a>
                    </aside>
                </div>
            </div>
        </section>
        <section class="container">
            <div class="row">
                <div class="col-md-12 hr">
                    <span class="hr1"></span>
                    <span class="hr2"></span>
                </div>


                <div class="col-md-2 left-nav">
                    <div id="filter_creator_content">
                    </div>
                </div>

                <c:if test="${not empty itemsMapByTaskMap.keySet()}">
                    <div class="col-md-10">
                        <div class="oneSeller">
                            <ul style="list-style-type: none">




                                <c:forEach items="${itemsMapByTaskMap.keySet()}" var="mainKey">
                                    <c:forEach items="${itemsMapByTaskMap.get(mainKey).keySet()}" var="taskKey">


                                        <c:forEach items="${itemsMapByTaskMap.get(mainKey).get(taskKey)}" var="item">

                                            <li class="col-md-12 product clearfix" 
                                                ${taskKey == 'search_query_0' ? "style=\"border: solid 2px #088F88\"": ""}
                                                ${taskKey == 'search_query_1' ? "style=\"border: solid 2px #66512c\"": ""}
                                                ${taskKey == 'search_query_2' ? "style=\"border: solid 2px #c7254e\"": ""}

                                                >
                                                <div class="col-md-2">
                                                    <c:forEach items="${item.photosInfo.item}" begin="0" end="0" var="photo" varStatus="itemStatus">
                                                        <c:if test="${photo.photoIsMain == true}">
                                                            <img class="image_link_class" src="${photo.photoUrl}" alt="Smiley face"/> 
                                                            <c:set var="imgLink" value="${photo.photoUrl}" scope="page"/>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <div class="col-md-5">
                                                    <h3>
                                                        <a  class="auction_link_class" href="${linkGenerator.generateLink(item.itemTitle,item.itemId)}" >
                                                            ${item.itemTitle}
                                                            <!--(${item.itemId})-->
                                                        </a>
                                                    </h3>
                                                    <aside>
                                                        <p>Czas do końca: ${item.timeToEnd}</p>
                                                        <p><span>${item.leftCount}</span> sztuk</p>
                                                        <p><span>${item.bidsCount}</span> kupiło</p>
                                                    </aside>
                                                </div>
                                                <div class="col-md-5 col-sm-12">
                                                    <div class="col-sm-6 text-right">
                                                        <a onclick="addToBasket${item.itemId}()" class="batn buy" >Do koszyka</a>


                                                        <script>
                                                            <c:forEach items="${item.priceInfo.item}"  var="price" varStatus="itemStatus">
                                                                <c:if test="${ price.priceType == 'withDelivery'}">
                                                            function addToBasket${item.itemId}() {

                                                                $.post("./basketAddProduct.view", {price: "${price.priceValue}", productId: "${item.itemId}", productTitle: "${item.itemTitle}", imgUrl: "${imgLink}"});
                                                            }

                                                                </c:if>
                                                            </c:forEach>
                                                        </script>

                                                    </div>
                                                    <div class="col-sm-6 text-right">

                                                        <c:forEach items="${item.priceInfo.item}"  var="price" varStatus="itemStatus">
                                                            <c:if test="${ price.priceType == 'buyNow'}">
                                                                <p class="prize"><span>Kup teraz</span>  ${price.priceValue} zł</p>
                                                            </c:if>
                                                            <c:if test="${ price.priceType == 'withDelivery'}">
                                                                <p class="withshipping"><span>z dostawą</span>  ${price.priceValue} zł</p>
                                                            </c:if>
                                                            <c:if test="${ price.priceType == 'bidding'}">

                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </li>

                                        </c:forEach> 

                                    </c:forEach> 

                                    <div class="col-md-12 break">
                                        <div class="col-md-8 text-right whose">
                                            <p>Przedmioty sprzedającego <span></span></p>
                                        </div>
                                        <div class="col-md-4 text-right summary">
                                            <div class="innerbox">
                                                <p class="prize-sum">  <span>${mainKey}</span></p>

                                            </div>
                                        </div>
                                    </div>
                                </c:forEach> 







                            </ul>




                            <div class="col-md-12 hr">
                                <span class="hr1"></span>
                                <span class="hr2"></span>
                            </div>
                        </div>
                    </div>


                    <div class="sitePagination col-md-offset-2">
                        <div class="col-md-6 text-left display">
                            <h3>wyświetlaj</h3>
                            <span>30</span>
                            <span class="active">60</span>
                            <span>120</span>
                        </div>
                        <div class="col-md-6 text-right page-number">
                            <a class="prevPage" href=""></a>
                            <a href="">1</a>
                            <a class="active" href="">2</a>
                            <a href="">3</a>
                            <a href="">4</a>
                            <a href="">5</a>
                            <a href="">6</a>
                            <span>z</span>
                            <span class="count">101</span>
                            <a class="nextPage" href=""></a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${ empty itemsMapByTaskMap.keySet()}">

                </c:if>



            </div>
        </section>
        <footer class="container">
            <div class="row">
                <div class="col-md-12 hr">
                    <span class="hr1"></span>
                    <span class="hr2"></span>
                </div>
                <div class="col-md-12 text-center">
                    <p>copyright &copy; by KupujemyOdJednego.pl all rights reserverd 2014-2015</p>
                </div>
            </div>
        </footer>

        <div id="allegro_warper">

        </div>
        <div id="page_loader">
            <div id="floatingBarsG">
                <div class="blockG" id="rotateG_01">
                </div>
                <div class="blockG" id="rotateG_02">
                </div>
                <div class="blockG" id="rotateG_03">
                </div>
                <div class="blockG" id="rotateG_04">
                </div>
                <div class="blockG" id="rotateG_05">
                </div>
                <div class="blockG" id="rotateG_06">
                </div>
                <div class="blockG" id="rotateG_07">
                </div>
                <div class="blockG" id="rotateG_08">
                </div>
            </div>
        </div>

        <script>
            var searchBoxCount = 0;
            <c:if test="${empty taskList}">
            $(document).ready(function () {
                getFilters("search_query_0");
                lockQueryInputs("0");
            });
            </c:if>
            while (searchBoxCount <= ${not empty taskList ? taskList.size() : "0"}) {

                addQueryToForm();
                searchBoxCount++;
            }
            <c:if test="${lastUsedFilter > -1}">
            $(document).ready(function () {
                getFilters("search_query_${lastUsedFilter}");
                lockQueryInputs("${lastUsedFilter}");

            });
            </c:if>
            <c:forEach var="parameter" items="${taskList}" >
                <c:forEach var="filterOption" items="${parameter.filterOptionsArray.item}">
                    <c:if test="${ filterOption.filterId == 'search'}">
                        <c:if test="${parameter.queryId != null}">
            if ($("#${parameter.queryId}")) {
                $("#${parameter.queryId}").val("${filterOption.filterValueId.item.get(0)}");
            }
                        </c:if>
                    </c:if>
                </c:forEach>
            </c:forEach>

        </script>
    </body>
</html>