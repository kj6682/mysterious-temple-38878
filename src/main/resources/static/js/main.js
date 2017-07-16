
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

    $(document).on("click", '.js-validate', function(event) {
        var my = event.target.getAttribute("mytarget");
        console.log(my);
    });

    $(document).on("click", '.js-delete', function(event) {
        console.log(event.target.getAttribute("mytarget"));
    });



});

function search_submit() {

    var DEBUG_json = false;

    var search = {};
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

            if(DEBUG_json) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);
            }

            var trHTML = '';

            $.each(data, function (i, item) {

                trHTML += '<tr>'+
                    '<td>' + data[i].id + '</td><td>' + data[i].shop + '</td>'+
                    '<td>' + data[i].cake + '</td><td>' + data[i].quantity + '</td>'+
                    '<td>' + data[i].message + '</td><td>' + data[i].created + '</td>'+
                    '<td>' + data[i].due + '</td><td>' + data[i].status + '</td>'+
                    '<td><button type="button" class="btn btn-default js-validate"> <span class="glyphicon glyphicon-ok-circle" mytarget=' + data[i].id + '></span></button></td>'+
                    '<td><button type="button" class="btn btn-default js-delete"> <span class="glyphicon glyphicon-remove-circle" mytarget=' + data[i].id + '></span></button></td>'+
                    +'</tr>';
            });

            $('#cakeTable').html(trHTML);

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