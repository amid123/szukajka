<%-- 
    Document   : basketpage
    Created on : 2015-05-22, 11:52:40
    Author     : ringo99<amid123@gmail.com>
--%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>        
        <meta charset="utf-8">
        <meta name="description" content="">
        <meta name="author" content="G3 IT Solutions">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Koszyk</title>

        <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
        <script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
        <script src="<c:url value="/resources/js/main.js"/>"></script>
        <script src="<c:url value="/resources/js/jquery.noty.packaged.min.js"/>"></script>
        <script src="<c:url value="/resources/js/notification_html.js"/>"></script>

        <link href="<c:url value="/resources/css/loader.css" />" rel="stylesheet"/>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:700,300,600&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/animate.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/buttons.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome.min.css" />">

    </head>
    <body>
        <header class="container">
            
            <div class="row head">
                <div class="col-md-3 col-xs-12 col-sm-12 text-center logo-container">
                    <a href="./search" title="Strona główna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
                </div>
                <div class="col-md-9 col-xs-12 text-right menu">
<!--                    <p>Zalogowany jako, <span>${logedUser}</span></p>-->
                    <nav>
                        <ul>
                            <!--                            <li><a href="./logout" class="menu_button">Wyloguj</a>	</li>	-->
                            <li><div id="basket_menu_elem"><a href="./basketp" id="basket_a" class="basket menu_button" >Koszyk</a></div>

                            </li>
                            <li><a href="#" class="menu_button" >Ustawienia</a></li>
                            <li><a href="./search" class="menu_button">Wyszukuj</a>	</li>	
                        </ul>
                    </nav>				
                </div>
            </div>	
        </header>
        <section class="container">


            <div class="row basket-page">
                <div class="col-xs-12 clearfix">
                    <h3>Twój koszyk</h3>

                    <div class="oneSeller">
                        <div class="col-xs-12 product thead clearfix">						
                            <div class="col-xs-7 border-r in-fl">
                                <div class="col-xs-12 va">
                                    <h3>Produkt</h3>
                                </div>
                            </div>
                            <div class="col-xs-5 va p0">
                                <div class="col-xs-6 border-r in-fl">
                                    <div class="col-xs-12 text-center va">
                                        <h3>Ilość sztuk</h3>
                                    </div>
                                </div>
                                <div class="col-xs-6 border-r in-fl">
                                    <div class="col-xs-12 text-center va">
                                        <h3>Cena</h3>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>


                    <c:forEach var="key" items="${basketMap.keySet()}">


                        <div class="oneSeller">

                            <c:forEach var="item"  items="${basketMap.get(key)}">

                                <div class="col-xs-12 product clearfix">
                                    <div class="col-xs-2 in-fl">
                                        <div class="col-xs-4 va">
                                            <img id="delete_${item.productId}" src="<c:url value="/resources/img/exit.png"  />">
                                        </div>
                                        <div class="col-xs-8 va">
                                            <img class="img-rwd" src="${item.imgLink}" alt="">
                                        </div>							
                                    </div>

                                    <script>
                                        $("#delete_${item.productId}").click(function () {
                                            $.post("./basketRemoveProduct.view", {remove: "${item.productId}"});
                                            location.reload();
                                        });
                                    </script>



                                    <div class="col-xs-5 border-r in-fl">
                                        <div class="col-xs-12 va">
                                            <h3>${item.productTitle}</h3>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 va p0">
                                        <div class="col-xs-6 border-r in-fl">
                                            <div class="col-xs-12 text-center va">

                                                <span>Ilość sztuk</span> ${item.quantity}


                                            </div>
                                        </div>
                                        <div class="col-xs-6 text-center">
                                            <p class="prize">
                                               
                                                
                                                 <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value=" ${item.priceBuyItNow}"/>
                                            </p>
                                            <p class="withshipping"><span>z dostawą</span> 
                                            
                                                         <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.priceWitchDelivery}"/>
                                                
                                            </p>
                                        </div>
                                    </div>
                                </div>


                            </c:forEach>

                            <div class="col-xs-12 break">
                                <div class="col-xs-7 text-right whose">
                                    <p>Przedmioty sprzedającego</p>
                                </div>
                                <div class="col-xs-5 text-right summary">
                                    <div class="innerbox">
                                        <p class="prize-sum"><span>${key}</span></p>
                                    </div>
                                </div>
                            </div>


                        </div>	

                    </c:forEach>


                    <div class="col-xs-12 basket-page-summary">
                        <!--                        <div class="col-xs-12">
                                                    <div class="col-sm-9 text-right">
                                                        <h3>Razem:</h3>
                                                    </div>
                                                    <div class="col-sm-3 text-right pR0">
                                                        <h3>123,13 zł</h3>
                                                    </div>
                                                </div>	
                                                <div class="col-xs-12">
                                                    <div class="col-sm-9 text-right">
                                                        <h3>Suma kosztu dostaw:</h3>
                                                    </div>
                                                    <div class="col-sm-3 text-right pR0">
                                                        <h3>123,13 zł</h3>
                                                    </div>
                                                </div>	-->
                        <div class="col-xs-12">
                            <div class="col-sm-9 text-right">
                                <h3>Do zapłaty:</h3>
                            </div>
                            <div class="col-sm-3 text-right pR0">
                                <p class="prize">
                                    
                                             <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${basket.getPriceSumString()}"/>
                               
                                    
                                    </h3>
                            </div>
                        </div>	
                    </div>
                    <div class="col-xs-12">
                        <div class="summary-batns text-right">
                            <a id="clear_batn" class="batn clear">Wyczyść</a>
                            <a id="bay_batn" class="batn buy">Kupuję</a>
                        </div>
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

        <script type="text/javascript">

            $("#clear_batn").click(function () {
                $.ajax({
                    type: "POST",
                    url: "./basketClear.view",
                    success: function (data)
                    {
                        location.reload();
                    }
                });
            });

            $("#bay_batn").click(function () {
                $.ajax({
                    type: "POST",
                    url: "./basketBuyAll.view",
                    success: function (data)
                    {
                        location.reload();
                    }
                });
            });
        </script>
    </body>
</html>
