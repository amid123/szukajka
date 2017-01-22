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

        <style>
            .error {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #a94442;
                background-color: #f2dede;
                border-color: #ebccd1;
            }

            .msg {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
                color: #31708f;
                background-color: #d9edf7;
                border-color: #bce8f1;
            }

            #login-box {
                width: 300px;
                padding: 20px;
                margin: 100px auto;
                background: #fff;
                -webkit-border-radius: 2px;
                -moz-border-radius: 2px;
                border: 1px solid #000;
            }
        </style>
    </head>
    <body>
<!--        <header class="container">
            <div class="row head">
                <div class="col-md-3 col-xs-12 col-sm-12 text-center logo-container">
                    <a href="<c:url value="/public/index" />" title="Strona główna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
                </div>
                <div class="col-md-9 col-xs-12 text-right menu">
                    <nav>
                        <ul>



                            <li><a href="<c:url value="/public/contact" />">Kontakt</a>	</li>

                            <li><a href="<c:url value="/public/about" />">O nas</a>	</li>
                            <li><a href="<c:url value="/login" />">Zaloguj</a>	</li>
                            <li><a href="<c:url value="/public/registration" />">Rejestracja</a></li>
                        </ul>
                    </nav>				
                </div>
            </div>	
        </header>-->
	<header class="container">
		<div class="row head-login">
			<div class="col-md-12 text-center logo-container">
				 <a href="<c:url value="/public/index" />" title="Strona główna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
			</div>
		</div>
	</header>
        <section class="container">
            <div class="row login">
                <div class="col-sm-4"></div>
                <div class="col-sm-4 login-cont clearfix">
                    <h3>Logowanie</h3>
                    <form name='loginForm' action="<c:url value='/login' />" method='POST'>
                        <div class="col-xs-12">
                            <div class="col-md-4 label">
                                <label for="login">Login</label>
                            </div>
                            <div class="col-md-8">
                     
                                <input id="login" type='text' style="width:180px;" name='username'>
                            </div>		
                        </div>
                        <div class="col-xs-12">
                            <div class="col-md-4 label">
                                <label for="psw">Hasło</label>
                            </div>
                            <div class="col-md-8">
                    
                                <input id="psw" type='password'style="width:180px;" name='password' />
                            </div>		
                        </div>	
                        <div class="col-xs-12 login-batn">
                            <input class="batn login-batn" type="submit" value="Zaloguj">
                        </div>	
                        <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                    </form>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </section>


<!--
        <section class="container">
            <div id="login-box">
                <h3>Zaloguj się przy pomocy nazwy użytkownika i hasła</h3>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <form name='loginForm'
                      action="<c:url value='/login' />" method='POST'>
                    <table>
                        <tr>
                            <td>Nazwa użtkownika:</td>
                            <td><input type='text' style="width:180px;" name='username'></td>
                        </tr>
                        <tr>
                            <td>Hasło:</td>
                            <td><input type='password'style="width:180px;" name='password' /></td>
                        </tr>
                        <tr>
                            <td colspan='2'><input  name="submit" type="submit"
                                                    value="Zaloguj" /></td>
                        </tr>
                    </table>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
        </section>-->

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
