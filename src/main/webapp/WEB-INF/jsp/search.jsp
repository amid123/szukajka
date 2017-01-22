<%-- 
    Document   : searchpage
    Created on : 2015-04-22, 12:47:56
    Author     : ringo99<amid123@gmail.com>
--%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <script src="<c:url value="/resources/js/jquery.noty.packaged.min.js"/>"></script>
        <script src="<c:url value="/resources/js/notification_html.js"/>"></script>
        <script src="<c:url value="/resources/js/main.js"/>"></script>

        <link href="<c:url value="/resources/css/loader.css" />" rel="stylesheet"/>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:700,300,600&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />">

        <link rel="stylesheet" href="<c:url value="/resources/css/animate.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/buttons.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css" />">
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
                            <li><div id="basket_menu_elem"><a href="./basketp" id="basket_a" class="basket menu_button" >Koszyk</a></div>
                                <div id="basket_container" style="display: none">
                                    <h4>Koszyk jest pusty</h4>
                                </div>
                            </li>
                            <li><a href="./settings" class="menu_button" >Ustawienia</a></li>
                            <li><a href="./search" class="menu_button">Wyszukuj</a>	</li>	
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
                        <a id="search_button"  class="batn search"><i  class="glyphicon glyphicon-search"></i>Wyszukaj</a>
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
                <div class="col-md-10">
                    <div class="oneSeller">
                        <ul style="list-style-type: none">
                            <c:forEach var="mainKey" items="${sellersList}">
                                <c:if test="${pageMap.containsKey(mainKey)}">
                                    <c:if test="${not empty pageMap.keySet()}">
                                        <c:forEach items="${pageMap.get(mainKey).keySet()}" var="taskKey">
                                            <c:forEach items="${pageMap.get(mainKey).get(taskKey)}" var="item">
                                                <li class="col-md-12 product clearfix">
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


                                                        <c:forEach var="parameter" items="${taskList}" >
                                                            <c:forEach var="filterOption" items="${parameter.filterOptionsArray.item}">
                                                                <c:if test="${ filterOption.filterId == 'search'}">
                                                                    <c:if test="${parameter.queryId == taskKey}">
                                                                        <span> 
                                                                            Szukana fraza: 
                                                                            <span style="color: #ff0000"> 
                                                                                ${filterOption.filterValueId.item.get(0)}
                                                                            </span> 
                                                                        </span>
                                                                    </c:if>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </div>
                                                    <div class="col-md-5 col-sm-12">
                                                        <div class="col-sm-6 text-right">
                                                            <c:if test="${connectionStatus == null}" >

                                                                <button id="getMoreInfo${item.itemId}" class="more_info_button_class batn" onclick="getData${item.itemId}()">
                                                                    Opis aukcji
                                                                </button>


                                                                <script>
                                                                    function getData${item.itemId}() {
                                                                        $.ajax({
                                                                            url: "./crossproxy?url=" + "${linkGenerator.generateLink(item.itemTitle,item.itemId)}",
                                                                            type: 'GET',
                                                                            success: function (data) {
                                                                                $('#detailed_product_info_content').html($(data).find('#userFieldTab').find('form').html());
                                                                                $('#detailed_info_container').css("width", $('#detailed_product_info').width() + 60);
                                                                                $('#detailed_info_container').css("margin", "0 auto");
                                                                                $("#detailed_product_info").show();
                                                                            }
                                                                        });
                                                                    }
                                                                </script>




                                                                <c:forEach items="${item.priceInfo.item}"  var="price" varStatus="itemStatus">

                                                                    <c:if test="${ price.priceType == 'buyNow'}">
                                                                        <a onclick="addToBasket${item.itemId}()" class="batn buy" >Do koszyka</a>
                                                                    </c:if>
                                                                </c:forEach>



                                                                
                                                                    <c:set var="priceBuyNow" value="${null}"/>
                                                                    <c:set var="priceWithDelivery" value="${null}"/>
                                                                    <c:forEach items="${item.priceInfo.item}"  var="price" varStatus="itemStatus">
                                                                        <c:if test="${ price.priceType == 'withDelivery'}">
                                                                            
                                                                            <c:set var="priceWithDelivery" value="${price.priceValue}"/>
                                                                        </c:if>
                                                                        <c:if test="${ price.priceType == 'buyNow'}">
                                                                           
                                                                            <c:set var="priceBuyNow" value="${price.priceValue}"/>
                                                                        </c:if>
                                                                    </c:forEach>




                                                               





                                                                <script>





                                                                    function addToBasket${item.itemId}() {
                                                                        function generate(type, layout) {
                                                                            var n = noty({
                                                                                text: type,
                                                                                type: type,
                                                                                dismissQueue: true,
                                                                                layout: layout,
                                                                                theme: 'defaultTheme',
                                                                                buttons: [
                                                                                    {addClass: 'btn btn-primary', text: 'Tak', onClick: function ($noty) {
                                                                                            $noty.close();
                                                                                            noty({dismissQueue: true, force: true, layout: layout, theme: 'defaultTheme', text: 'Pomyślnie dodano przedmio', type: 'success', timeout: '1000'});
                                                                                            $.post("./basketAddProduct.view", {sellerName: "${mainKey}", priceWithDelivery: "${priceWithDelivery!= null ? priceWithDelivery: 0}", priceBuyNow: "${priceBuyNow!= null ? priceBuyNow : 0}", productId: "${item.itemId}", productTitle: "${item.itemTitle}", imgUrl: "${imgLink}"});
                                                                                        }
                                                                                    },
                                                                                    {addClass: 'btn btn-danger', text: 'Nie', onClick: function ($noty) {
                                                                                            $noty.close();
                                                                                            noty({dismissQueue: true, force: true, layout: layout, theme: 'defaultTheme', text: 'Dodawanie przedmiotu anulowane', type: 'error', timeout: '1000'});
                                                                                        }
                                                                                    }
                                                                                ]
                                                                            });
                                                                            console.log('html: ' + n.options.id);
                                                                        }
                                                                        generate('Czy napewno chcesz dodać \"${item.itemTitle}\" do koszyka ?', 'topLeft');
                                                                    }

                                                                    function generate(type, layout) {
                                                                        var n = noty({
                                                                            text: type,
                                                                            type: type,
                                                                            dismissQueue: true,
                                                                            layout: layout,
                                                                            theme: 'defaultTheme',
                                                                            buttons: [
                                                                                {addClass: 'btn btn-primary', text: 'Dodaj', onClick: function ($noty) {
                                                                                        $noty.close();
                                                                                        noty({dismissQueue: true, force: true, layout: layout, theme: 'defaultTheme', text: 'Pomyślnie dodano przedmio', type: 'success', timeout: '1000'});
                                                                                    }
                                                                                },
                                                                                {addClass: 'btn btn-danger', text: 'Anuluj', onClick: function ($noty) {
                                                                                        $noty.close();
                                                                                        noty({dismissQueue: true, force: true, layout: layout, theme: 'defaultTheme', text: 'Dodawanie przedmiotu anulowane', type: 'error', timeout: '1000'});
                                                                                    }
                                                                                }
                                                                            ]
                                                                        });
                                                                        console.log('html: ' + n.options.id);
                                                                    }
                                                                </script>
                                                            </c:if>
                                                            <c:if test="${connectionStatus == false}" >
                                                                <a onclick = "setTimeout(function () {
                                                                            generateAll();
                                                                        }, 500)" class="batn buy" >Do koszyka</a>
                                                                <script>
                                                                    function generateAll() {
                                                                        generate('error', notification_html[1]);
                                                                    }
                                                                    function generate(type, text) {

                                                                        var n = noty({
                                                                            text: text,
                                                                            type: type,
                                                                            dismissQueue: true,
                                                                            timeout: '1000',
                                                                            closeWith: ['hover'],
                                                                            layout: 'topLeft',
                                                                            theme: 'relax',
                                                                            maxVisible: 10,
                                                                            animation: {
                                                                                open: 'animated bounceInLeft',
                                                                                close: 'animated bounceOutLeft',
                                                                                easing: 'swing',
                                                                                speed: 500
                                                                            }
                                                                        });
                                                                        console.log('html: ' + n.options.id);
                                                                    }
                                                                </script>
                                                            </c:if>
                                                        </div>
                                                        <div class="col-sm-6 text-right">
                                                            <c:forEach items="${item.priceInfo.item}"  var="price" varStatus="itemStatus">

                                                                <c:if test="${ price.priceType == 'buyNow'}">

                                                                    <p class="prize"><span>Kup teraz</span>  
                                                                        <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${price.priceValue}"/>
                                                                        zł</p>
                                                                    </c:if>
                                                                    <c:if test="${ price.priceType == 'withDelivery'}">

                                                                    <p class="withshipping"><span>z dostawą</span>  
                                                                        <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${price.priceValue}"/>
                                                                        zł</p>
                                                                    </c:if>
                                                                    <c:if test="${ price.priceType == 'bidding'}">

                                                                    <p class="prize"><span>Licytacja</span>  
                                                                        <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${price.priceValue}"/>
                                                                        zł</p>
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
                                    </c:if>
                                    <c:if test="${ empty pageMap.keySet()}">

                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <c:if test="${not empty pageMap}">

                    <div class="col-md-12 hr">
                        <span class="hr1"></span>
                        <span class="hr2"></span>
                    </div>

                    <div class="sitePagination col-md-offset-2">
                        <div class="col-md-3 text-left display">
                            <!--                            <h3>wyświetlaj</h3>
                                                        <span>30</span>
                                                        <span class="active">60</span>
                                                        <span>120</span>-->
                        </div>
                        <div class="col-md-9 text-right page-number">

                            <c:if test="${currentPageNo !=1}">
                                <a class="prevPage" href="<c:url value="/dosearch?page=${currentPageNo != 1 ?currentPageNo-1 : currentPageNo}"/>"></a>

                            </c:if>

                            <c:forEach begin="1" end="${currentPageNo+10}" var="loop">
                                <c:if test="${loop>=0 && loop <=pagesCount}">
                                    <c:if test="${loop!=currentPageNo}">
                                        <c:if test="${currentPageNo-10 <= loop}" >
                                            <a href="<c:url value="/dosearch?page=${loop}"/>">${loop}</a>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${loop==currentPageNo}">
                                        <a class="active" >${loop}</a>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <span>z</span>
                            <span class="count">${pagesCount}</span>
                            <c:if test="${currentPageNo+1 <= pagesCount}">
                                <a class="nextPage" href="<c:url value="/dosearch?page=${currentPageNo+1}"/>"></a>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </div>
            <div id="detailed_info_container">
                <div id="detailed_product_info" style="display: none">
                    <span id="close_detailed_span" class="close_detailed_span glyphicon glyphicon-remove-circle"></span>
                    <div id="detailed_product_info_content" style="float: left">
                    </div>
                </div>
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
        <div id="page_loader" style="background: #000; display: block;" >
            <div id="circularG">
                <div id="circularG_1" class="circularG">
                </div>
                <div id="circularG_2" class="circularG">
                </div>
                <div id="circularG_3" class="circularG">
                </div>
                <div id="circularG_4" class="circularG">
                </div>
                <div id="circularG_5" class="circularG">
                </div>
                <div id="circularG_6" class="circularG">
                </div>
                <div id="circularG_7" class="circularG">
                </div>
                <div id="circularG_8" class="circularG">
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
            while (searchBoxCount < ${not empty taskList ? taskList.size() : "0"}) {
                addQueryToForm();
                searchBoxCount++;
            }

            ${empty taskList ? "addQueryToForm();" : ""}

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

            $(function () {
                var $document, didScroll, offset;
                offset = $('.head').position().top;
                $document = $(document);
                didScroll = false;
                $(window).on('scroll touchmove', function () {
                    return didScroll = true;
                });
                return setInterval(function () {
                    if (didScroll) {
                        $('.head').toggleClass('fixed', $document.scrollTop() > offset);
                        return didScroll = false;
                    }
                }, 250);
            });
        </script>
        <div class="container">
            <div id="customContainer"></div>
        </div>
        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-7073706-2', 'auto');
            ga('send', 'pageview');

        </script>
    </body>
</html>