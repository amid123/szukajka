<%-- 
    Document   : index
    Created on : 2015-05-11, 12:33:26
    Author     : ringo99<amid123@gmail.com>
--%>

<%@page trimDirectiveWhitespaces="true" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-2"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="pl">
    <head>

        <meta name="description" content="">
        <meta name="author" content="G3 IT Solutions">
        <meta name="viewport" content="width=device-width, initial-scale=1">

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
                    <a href="<c:url value="/public/index" />" title="Strona g³ówna"><img src="<c:url value="/resources/img/logo.png"/>" alt="Kupujemy od jednego"></a>
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
            <div class="row register">
                <div class="col-md-12 reg-cont clearfix">
                    <h3>Rejestracja</h3>
                    <form:form id="reg_form" action="./register" commandName="userRegistrationModel" modelAttribute="userRegistrationModel" method="post">
                        <div class="col-xs-12">
                            <div class="col-md-2 label">
                                <form:label path="username">
                                    <s:message code="userRegistrationModel.username" />: 

                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:input path="username" />
                                <form:errors path="username" cssClass="error" />
                            </div>		
                        </div>



                        <div class="col-xs-12">
                            <div class="col-md-2 label">
                                <form:label path="password">
                                    <s:message code="userRegistrationModel.password" />: 

                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:password id="pwd_main" path="password" />
                                <form:errors path="password" cssClass="error" />
                            </div>		
                        </div>	

                        <div class="col-xs-12">
                            <div class="col-md-2 label">

                                <label for="pwd_repeat">
                                    Powtórz has³o
                                </label>

                            </div>
                            <div class="col-md-10">
                                <input id="pwd_repeat" type="password"></input>
                                <span id="confirmMessage" class="confirmMessage"></span>
                            </div>		
                        </div>	
                        <script>


                            var isPasswordCorrect = false;

                            function checkPass()
                            {
                                var pass1 = document.getElementById('pwd_main');
                                var pass2 = document.getElementById('pwd_repeat');
                                var message = document.getElementById('confirmMessage');


                                if ($("#pwd_main").val() == $("#pwd_repeat").val()) {
                                    $("#pwd_repeat").css("background-color", "#66cc66");
                                    $("#confirmMessage").css("color", "#66cc66");
                                    $("#confirmMessage").empty();
                                    $("#confirmMessage").append("Wprowadzone has³a pokrywaj± siê");

                                    isPasswordCorrect = true;
                                    //message.style.color = goodColor;
                                    // message.innerHTML = "Wprowadzone has³a pokrywaj± siê"
                                } else {

                                    $("#pwd_repeat").css("background-color", "#ff6666");
                                    $("#confirmMessage").css("color", "#ff6666");
                                    $("#confirmMessage").empty();
                                    $("#confirmMessage").append("Wprowadzone has³a nie pokrywaj± siê!");
                                    isPasswordCorrect = false;
                                }
                            }


                            $("#pwd_repeat").keyup(function () {
                                checkPass();

                            });

                        </script>




                        <div class="col-xs-12">
                            <div class="col-md-2 label">
                                <form:label path="email">
                                    <s:message code="userRegistrationModel.email" />: 

                                </form:label>
                            </div>
                            <div class="col-md-10">
                                <form:input path="email" />
                                <form:errors path="email" cssClass="error" />
                            </div>		
                        </div>

                    </form:form>
                    <span id="register_error"></span>
                    <div class="col-xs-12 batns-cont">
                        <div class="col-md-3 reg-accept">
                            <input id="regacc" type="checkbox" name=""><label for="regacc"><span></span>O¶wiadczam ¿e zapozna³em siê z regulaminem i akceptujê go. Odno¶nik do regulaminu: <a  href="./reg">regulamin</a></label>
                            <p id="regacc_error"></p>
                        </div>
                        <div class="col-md-9 reg-batns">
                            <!--                                <input class="batn cancel-batn" type="button" value="Anuluj">-->
                            <input id="post_button" class="batn login-batn" type="button" value="Zarejestruj">

                            <script>

                                $("#post_button").click(function () {


                                    if ($("#regacc").attr('checked')) {
                                        if (isPasswordCorrect == true) {
                                            $("#reg_form").trigger("submit");
                                        } else {
                                            $("#register_error").empty();
                                            $("#register_error").css("color", "#ff6666");
                                            $("#register_error").append("Formularz nie zosta³ prawid³owo wype³niony!");
                                        }

                                    } else {

                                        $("#regacc_error").empty();
                                        $("#regacc_error").css("color", "#ff6666");
                                        $("#regacc_error").append("Musisz zaakceptowaæ regulamin!");
                                    }
                                });

                            </script>
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
                    <p>copyright &copy; by blabla.cc all rights reserverd 2014-2015</p>
                </div>
            </div>
        </footer>
    </body>
</html>
