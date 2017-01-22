<%-- 
    Document   : filters
    Created on : 2015-04-22, 16:05:38
    Author     : ringo99<amid123@gmail.com>
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="https.webapi_allegro_pl.service.FiltersListType"%>
<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="slider">
    <div id="sticky-basket" class="sticky-basket">
        <h2>Twój koszyk</h2>
        <img class="down-arrow" src="<c:url value="/resources/img/down.png"/>">
        <img class="up-arrow" src="<c:url value="/resources/img/up.png"/>">
        <div class="list-container">
            <div class="list">			

                <c:if test="${empty productList}"> 
                    <h4 id="empty_basket">Koszyk jest pusty.</h4>
                </c:if>
                <c:if test="${not empty productList}"> 
                    <c:forEach var="item" items="${productList}">
                        <div class="product clearfix">
                            <img id="delete_${item.productId}" class="delete" src="<c:url value="/resources/img/exit.png"/>">
                            <%--wyswietlamy fotke--%>
                            <img class="col-xs-4 basket_image_min" src="${item.imgLink}" alt="Smiley face"/> 
                            <aside class="col-xs-8">

                                <div class="basket_title_class">
                                    <p>${item.productTitle} <h5 class="basket_items_count">x${item.quantity}</h5></p>
                                </div>
                                <p class="withshipping"><span>z dostawą</span>
                                    
                                    <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.priceWitchDelivery}"/>
                                    zł</p>
                            </aside>
                        </div>

                        <script>
                            $("#delete_${item.productId}").click(function () {

                                function generate_info(type, text) {

                                    var n = noty({
                                        text: text,
                                        type: type,
                                        dismissQueue: true,
                                        timeout: '1000',
                                        layout: 'topLeft',
                                        closeWith: ['click', 'button', 'hover', 'backdrop'],
                                        theme: 'relax',
                                        maxVisible: 10,
                                        animation: {
                                            open: 'animated bounceInLeft',
                                            close: 'animated bounceOutLeft',
                                            easing: 'swing',
                                             timeout: '1000',
                                            speed: 500
                                        }
                                    });
                                    console.log('html: ' + n.options.id);
                                }
                                generate_info('information','Produkt został usunięty');
                                //alert("Pomyślnie usunięto produkt: ${item.productTitle}x${item.quantity}");
                                $.post("./basketRemoveProduct.view", {remove: "${item.productId}"});
                                $("#basket_a").trigger("click");
                            });
                        </script>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="clearfix basket-buttons">
            <a id="clear_batn" class="batn clear">Wyczyść</a>
            <a  class="sumPrice">
                Suma: 
                <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${basket.getPriceSumString()}"/>
                zł </a>
            <a id="bay_batn" class="batn buy">Kupuję</a>
        </div>
    </div>
</div>
