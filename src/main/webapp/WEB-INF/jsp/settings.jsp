<%-- 
    Document   : index
    Created on : 2015-05-11, 12:33:26
    Author     : ringo99<amid123@gmail.com>
--%>

<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="pl">
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
        <style>
            .error {
                color: #ff0000;
                font-style: italic;
                font-weight: 100;
            }
        </style>
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
                            <li><a href="./settings" class="menu_button" >Ustawienia</a></li>
                            <li><a href="./search" class="menu_button">Wyszukuj</a>	</li>	
                        </ul>
                    </nav>				
                </div>
            </div>	
        </header>
        <section class="container">




            <div class="row register">
                <div class="col-md-12 reg-cont clearfix">
                    <h3>Ustawienia użytkownika:</h3>
                    <form:form action="./updatesettings" modelAttribute="settingsDto" method="post">

                        <div class="col-xs-12">
                            <div class="col-md-2 label">
                                <form:label path="itemsOnOnePage">
                                    Ilość przedmiotów na stronie
                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:input path="itemsOnOnePage" />
                                <form:errors path="itemsOnOnePage" cssClass="error" />

                            </div>


                        </div>



                        <div class="col-xs-12">


                            <div class="col-md-2 label">
                                <form:label path="allegroLogin">
                                    Login Allegro:
                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:input path="allegroLogin" />
                                <form:errors path="allegroLogin" cssClass="error" />

                            </div>

                        </div>



                        <div class="col-xs-12">
                            <div class="col-md-2 label">
                                <form:label path="allegroPassword">
                                    Hasło Allegro:
                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:password path="allegroPassword" />
                                <form:errors path="allegroPassword" cssClass="error" />

                            </div>
                        </div>
                                
                        <div class="col-xs-12 batns-cont">
                            <div class="col-md-3 con_status">
                                <c:if test="${settingsDto.accountConnectedToAllegro ==true}">
                                    <p class="connection_status_ok">
                                        Konto jest połączone
                                    </p>
                                </c:if>
                                <c:if test="${settingsDto.accountConnectedToAllegro == false}">
                                    <p class="connection_status_error">
                                        Konto nie jest połączone
                                    </p>
                                </c:if>
                            </div>

                            <div class="col-md-9 reg-batns">



                                <input class="batn login-batn" type="submit" value="Zapisz ustawienia">

                            </div>

                        </div>	

                    </form:form>

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
                    <p>copyright &copy; by blabla.cc all rights reserverd 2014-2015</p>
                </div>
            </div>
        </footer>

    </body>
</html>
