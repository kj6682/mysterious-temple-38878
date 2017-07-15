$(document).ready(function () {

    $("#search-all-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        search_all_submit();

    });

    $("#search-shop-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        search_shop_submit();

    });

});

function search_all_submit() {

    var search = {}
    search["username"] = $("#username").val();

    $("#btn-search-all").prop("disabled", true);

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/orders",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search-all").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search-all").prop("disabled", false);

        }
    });

}

function search_shop_submit() {

    var search = {}
    search["shop"] = $("#shop").val();

    $("#btn-search-shop").prop("disabled", true);

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/"+search.shop+"/orders",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search-shop").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search-shop").prop("disabled", false);

        }
    });

}