var lastUsedFilterId = null;
/**
 * 
 * @param {type} id
 * @returns {undefined}
 * 
 * Rejestruje handler dla zmiany stanu kontenera 
 */
function refreshFilter(id) {

    var idNumb;
    if (id === "search_query_0") {
        idNumb = 0;
    } else if (id === "search_query_1") {
        idNumb = 1;
    } else if (id === "search_query_2") {
        idNumb = 2;
    } else if (id === "search_query_3") {
        idNumb = 3;
    } else if (id === "search_query_4") {
        idNumb = 4;
    }

    // lastUsedFilterId = idNumb;

    $("#queryId").val(id);

    $("#filter_form").change(function () {

        document.getElementById('page_loader').style.display = "block";
        //$("#page_loader").show();
        $.ajax({
            type: "POST",
            data: $("#filter_form").serialize(),
            url: "./filterGenerator" + idNumb + ".view",
            cache: false,
            // async: false,
            success: function (data)
            {
                document.getElementById('page_loader').style.display = "none";
                //$("#page_loader").hide();

                if (lastUsedFilterId === null) {
                    document.getElementById("filter_creator_content").innerHTML = data;
                } else {

                    if (lastUsedFilterId != idNumb)
                        document.getElementById("filter_creator_content").innerHTML = data;
                }


                refreshFilter(id);
                $("#search").hide();
                $("#lable_search").hide();
                $(".filter_title_search").hide();
                $(".filter_title_distance").hide();
                $(".filter_title_postCode").hide();
                $("#distance").hide();
                $("#postCode").hide();
            }
        });
    });
}

function getFilters(id) {

    var idNumb;
    if (id === "search_query_0") {
        idNumb = 0;
    } else if (id === "search_query_1") {
        idNumb = 1;
    } else if (id === "search_query_2") {
        idNumb = 2;
    } else if (id === "search_query_3") {
        idNumb = 3;
    } else if (id === "search_query_4") {
        idNumb = 4;
    }

    document.getElementById('page_loader').style.display = "block";
    //$("#page_loader").show();

    $.ajax({
        type: "POST",
        url: "./filterGenerator" + idNumb + ".view",
        cache: false,
        //async: false,
        success: function (data)
        {
            if (lastUsedFilterId === null) {
                document.getElementById("filter_creator_content").innerHTML = data;
            } else {

                if (lastUsedFilterId != idNumb)
                    document.getElementById("filter_creator_content").innerHTML = data;
            }

            // $("#page_loader").hide();
            document.getElementById('page_loader').style.display = "none";
            refreshFilter(id);
            $("#search").hide();
            $("#lable_search").hide();
            $(".filter_title_search").hide();
            $(".filter_title_category").hide();
            $(".filter_title_distance").hide();
            $(".filter_title_postCode").hide();
            $("#distance").hide();
            $("#postCode").hide();



            if ($("#search").val() == "") {
                $("#category").hide();
                $("#lable_search").hide();
                $(".filter_title_search").hide();
                $(".filter_title_distance").hide();
                $(".filter_title_postCode").hide();
                $("#distance").hide();
                $("#postCode").hide();

            }
        }
    });
}

var incrementVar = 0;
function increment() {
    incrementVar = incrementVar + 1;
}
function decrement() {
    if (incrementVar > 0)
        incrementVar = incrementVar - 1;
}

/**
 * 
 * Funkcja oblokowuje buttony z wyjątkiem tego o id podanym w id
 */
function enableOtherButtons(id) {

    // alert("button do zablokowania: " + id);

    if ($("#add_query_batn_0") !== null && id != 0) {
        $("#add_query_batn_0").removeAttr("disabled");
        //alert("odblokowuje: " + id);
    }
    if ($("#add_query_batn_1") !== null && id != 1) {
        $("#add_query_batn_1").removeAttr("disabled");
    }
    if ($("#add_query_batn_2") !== null && id != 2) {
        $("#add_query_batn_2").removeAttr("disabled");
    }
    if ($("#add_query_batn_3") !== null && id != "3") {
        $("#add_query_batn_3").removeAttr("disabled");
    }
    if ($("#add_query_batn_4") !== null && id != "4") {
        $("#add_query_batn_4").removeAttr("disabled");
    }
}
/**
 * funkcja blokujaca odpowiednie inputy
 */

function lockQueryInputs(id) {
    /**
     * Jesli kliknieto na dany guzik to musimy go zablokować
     * 
     */

    // odblokowujemy wszystki inne przyciski
    // za wyjatkiem przycisku o id id ;]
    enableOtherButtons(id);

    //$("#add_query_batn_" + id).attr("disabled");
    //this.disabled = "disabled";
    $("#add_query_batn_" + id).prop('disabled', true);
    /**
     * Blokujemy wszystkie inputy prócz 
     * tego ktorego dotyczy filtr
     */

    $("#search_query_" + id).prop('disabled', false);

    if ($("#search_query_0") && id != 0) {
        $("#search_query_0").prop('disabled', true);
    }
    if ($("#search_query_1") && id != 1) {
        $("#search_query_1").prop('disabled', true);
    }
    if ($("#search_query_2") && id != 2) {
        $("#search_query_2").prop('disabled', true);
    }
    if ($("#search_query_3") && id != 3) {
        $("#search_query_3").prop('disabled', true);
    }
    if ($("#search_query_4") && id != 4) {
    }
}
/**
 * 
 * funkcja rejestruje zapytanie, kopiuje fraze, wywoluje odpowiedni event 
 * i rejestruje obsluge klikniecia;
 * 
 */
function registerQuery(id) {
    /**
     * Obsluga guzika przywoływania filtra
     */
    $("#add_query_batn_" + id).click(function () {


        if (lastUsedFilterId != null) {
            postLastFilter(lastUsedFilterId);

        }

        lockQueryInputs(id);
        getFilters("search_query_" + id);

        lastUsedFilterId = null;

    });
    /**
     * ustawiamy ostatnio urzywany filtr na ten w ktorym ostatnio pisalismy
     */
    $("#search_query_" + id).keyup(function () {
        lastUsedFilterId = id;
    });
}

var delay = (function () {
    var timer = 0;
    return function (callback, ms) {
        clearTimeout(timer);
        timer = setTimeout(callback, ms);
    };
})();

/**
 * Dodawanie nowego zapytania do wyszukiwarki, tworzy inputy, odpowiednie divy i button
 * rejestruje zapytanie - umozliwia zarzadzanie guzikami i kopiowaniem wartosci
 * do prawdziwego inputu z filtrów
 */


function addQueryToForm(first) {

    var placeholderText = "Wpisz tutaj frazę której szukasz...";
    var dynamic_form_divV = document.getElementById("form_dynamic_content");
    var inputV = document.createElement("input");
    var divV = document.createElement("div");

    if (incrementVar < 5) {

        inputV.setAttribute("type", "text");
        inputV.setAttribute("placeholder", placeholderText);
        inputV.setAttribute("name", "search_query_" + incrementVar);
        inputV.setAttribute("id", "search_query_" + incrementVar);
        inputV.setAttribute("class", "search_query_input");

        $("#filter_box").append("<div class=\"col-xs-12\"><div class=\"col-sm-2\"> <button id=\"add_query_batn_" + incrementVar +
                "\" class=\"batn\" type=\"button\">Przedmiot " + (incrementVar + 1) +
                "</button></div><div id=\"input" + incrementVar + "\" class=\"col-sm-8\">" +
                "</div> <div class=\"col-sm-2\"><img id=\"akceptuj_batn_" + incrementVar + "\" src=\"/AllegroMv/resources/img/akceptuj.png\"/></div>    </div>");
        $("#input" + incrementVar).append(inputV);
        increment();
        var id = (incrementVar - 1).toLocaleString();
        registerQuery(id);
    }
    else {
        alert("nie mozna dodać więcej !");
    }
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
}

function generate_info_clr(type, text) {

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


function postLastFilter(id) {
    if (id !== null)
        if ($(document).find("#search")) {
            $("#search").removeAttr("value");
            $("#search").val($("#search_query_" + (id)).val());
            // odpalamy event

            $.ajax({
                type: "POST",
                data: $("#filter_form").serialize(),
                url: "./filterGenerator" + id + ".view"

            });
        }
}

/**
 * Gdy dokument jest gotowy sterowanie ląduje tutaj;
 * wrzucamy tu wszsytkie gówna które mają się wykonać na początku;
 */
$(document).ready(function () {

    $('#loading_msg').each(function () {
        $(this).show();
    })

    $("#search_button").click(function () {

        if (lastUsedFilterId != null)
            postLastFilter(lastUsedFilterId);
        $("#page_loader").show();


        lastUsedFilterId = null;

        $("#page_loader").show();

        setTimeout(function () {
            $(location).attr('href', "./dosearch");
        }, 1000);
    });
    //$("#page_loader").hide();
    /**
     * event handler dla buttona dodajacego nowe wyszukiwanie
     * dodaje inputy, pobiera odrazu dla niego filtr i zmienia aktywny input
     */

    $("#add_query_button").click(function () {


        $("#page_loader").show();
        postLastFilter(lastUsedFilterId);


        addQueryToForm(false);
        //window.setTimeout( getFilters("search_query_" + (incrementVar - 1)), 1000 );
        getFilters("search_query_" + (incrementVar - 1));
        lockQueryInputs((incrementVar - 1));
    });
    $("#basket_a").mouseenter(function () {

        $.ajax({
            url: "./basketView.view",
            cache: false,
            context: document.body

                    /**
                     * Tu działamy gdy załaduje się koszyk
                     */
        }).done(function (data) {


            $("#basket_container").empty();
            $("#basket_container").append(data);
            //document.getElementById("basket_container").innerHTML = data;

            $("#basket_container").show();


            if ($("#sticky-basket"))
                $("#sticky-basket").show();

            /**
             * Rejestrujemy obsluge wyjscia z diva - dodajemy mu display none
             */
            $("#sticky-basket").mouseleave(function () {
                if ($("#sticky-basket"))
                    $("#sticky-basket").hide();
                //document.getElementById("sticky-basket").style = "display:none!important;";
            });

            /**
             * Obsluga zdarzenia klikniecia w button clear w koszyku
             * wysyłamy posta do kontrolera aby wyczyscil nasz skoszyk
             */
            $("#clear_batn").click(function () {
                $.ajax({
                    type: "POST",
                    url: "./basketClear.view",
                    cache: false,
                    success: function (data)
                    {
                        $("#basket_a").trigger("click");
                        generate_info_clr('information', 'Koszyk został wyczyszczony');
                    }
                });
            });

            $("#bay_batn").click(function () {
                /***
                 * 
                 * NIE USUWAC - obsluga kupna z koszyka !!!!
                 */
//                if (!$("#empty_basket")) {
//                    generate_info_clr('information', 'Przetwarzanie zamówienia...');
//                    if ($("#sticky-basket"))
//                        $("#sticky-basket").hide();
//
//                    $.ajax({
//                        type: "POST",
//                        url: "./basketBuyAll.view",
//                        success: function (data)
//                        {
//
//                            generate_info_clr('information', 'Twoje zamówienie zostało pomyślnie zrealizowane!');
//
//                        }
//                    });
//                } else {
//                    generate_info_clr('information', 'Twój koszyk jest pusty');
//                }

                alert("Ta opcja jeszcze nie działa, przepraszamy.");
            });

            var $sliders = $('.slider');

            $sliders.each(function () {
                var $current_slider = $(this);
                var $lista = $('.list', $current_slider);
                var $slides = $lista.children('div.product');

                if ($slides.length > 4) {
                    //wyliczamy odległość pojedynczego przesunięcia
                    var odleglosc = $slides.eq(0).outerHeight() + parseInt($slides.eq(0).css('margin-top')) + parseInt($slides.eq(0).css('margin-bottom'));

                    //po kliknięciu next przesówamy listę w lewo. Przed 3 ostatnimi elementami na liście musimy ją zatrzymać tak, by nie robiła się "pusta dziura".
                    //Wyliczamy więc maksymalne przesuniecie w lewo
                    var maxLeft = odleglosc * $slides.length - 4 * odleglosc;

                    //po kliknięciu na przycisk Poprzednie, przesówamy naszą listę w lewo
                    $('.down-arrow', $current_slider).click(function () {
                        if ($lista.position().top > -maxLeft) {
                            $($lista).not(':animated').animate({
                                'top': '-=' + 150
                            }, 500);
                        }
                    });

                    $('.up-arrow', $current_slider).click(function () {
                        if ($lista.position().top < 0) {
                            $($lista).not(':animated').animate({
                                'top': '+=' + 150
                            }, 500);
                        }
                    });

                } else {

                    //jeżeli dla danego slidera przewijanie ma nie działać, wyłączmy działanie jego next i prev
                    $('.next, .prev', $current_slider).click(function () {
                        $(this).preventDefault();
                        return false;
                    });
                }
            });

        });
    });

    /**
     * 
     * Po pobraniu dokumentu chowamy diva z info o produkcie
     */
    $("#detailed_product_info").hide();
    /**
     * Obsługa zamykania diva z informacja o danym produkcie
     * ukrywamy diva i usuwamy jego tresc
     */
    $("#close_detailed_span").click(function () {
        $("#detailed_product_info").hide();
        $('#detailed_product_info_content').html("");
    });
});