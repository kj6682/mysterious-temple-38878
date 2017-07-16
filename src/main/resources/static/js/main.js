$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        search_submit();

    });

    $("#create-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        create_submit();

    });

});

function search_submit() {

    var search = {}
    search["shop"] = $("#search-form-shop").val();
    if(search.shop === "all"){
        search["shop"] = "";
    }

    $("#btn-search").prop("disabled", true);

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
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}

function create_submit() {

    var order = {};
    order["shop"] = $("#create-form-shop").val();
    order["cake"] = $("#create-form-cake").val();
    order["quantity"] = $("#create-form-quantity").val();
    order["message"] = $("#create-form-message").val();
    order["created"] = $("#create-form-createdOn").val();
    order["due"] = $("#create-form-dueOn").val();
    order["status"] = "NEW";

    $("#btn-create").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/"+order.shop+"/orders",
        data: JSON.stringify(order),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback-create').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-create").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback-create').html(json);

            console.log("ERROR : ", e);
            $("#btn-create-create").prop("disabled", false);

        }
    });

}