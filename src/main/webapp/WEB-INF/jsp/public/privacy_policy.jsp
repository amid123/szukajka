<%-- 
    Document   : index
    Created on : 2015-05-11, 12:33:26
    Author     : ringo99<amid123@gmail.com>
--%>

<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    </head>
    <body>
        <header class="container">
            <div class="row head">
                <div class="col-md-3 col-xs-12 col-sm-12 text-center logo-container">
                    <a href="<c:url value="/public/index" />" title="Strona główna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
                </div>
                <div class="col-md-9 col-xs-12 text-right menu">
                    <nav>
                        <ul>
                            <li><a href="<c:url value="/public/contact" />">Kontakt</a>	</li>
                            <li><a href="<c:url value="/public/about" />">O nas</a>	</li>
                            <li><a href="<c:url value="/search" />">Szukaj</a>	</li>
<!--                            <li><a href="<c:url value="/login" />">Zaloguj</a>	</li>-->
                            <li><a href="<c:url value="/public/registration" />">Rejestracja</a></li>
                        </ul>
                    </nav>				
                </div>
            </div>	
        </header>
        <section class="container">

        </section>
        <section class="container">

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
