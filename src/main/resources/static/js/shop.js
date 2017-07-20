
$(document).ready(function () {

    $("#catalog-area").hide();
    $("#create-area").hide();
    $("#search-area").hide();


    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        search_order();

    });

    $("#create-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        create_order();

    });

    $(document).on("click", '.js-browse-catalog', function(event) {
        $("#catalog-area").show();
        $("#create-area").hide();
        $("#search-area").hide();
    });

    $(document).on("click", '.js-list-orders', function(event) {
        $("#catalog-area").hide();
        $("#create-area").show();
        $("#search-area").show();
    });

    $(document).on("click", '.js-create', function(event) {
        create_order();
        $("#cakeModal").modal('toggle');
    });

    $(document).on("click", '.js-delete', function(event) {
        var orderid = event.target.getAttribute("orderid");
        delete_order(orderid);
    });

    $(document).on("click", '.js-validate', function(event) {
        var orderid = event.target.getAttribute("orderid");
        validate_order(orderid);
    });

    $(document).on("click", '.js-catalog-item', function(event) {
        $("#create-modal-form-cake").val($(this).attr('id'));
        $("#create-modal-form-message").val($(this).text().trim());
    });

});

function search_order() {

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

                trHTML += '<tr id="tr'+ data[i].id + '">'+
                    '<td>' + data[i].id + '</td><td>' + data[i].shop + '</td>'+
                    '<td>' + data[i].cake + '</td><td>' + data[i].quantity + '</td>'+
                    '<td>' + data[i].message + '</td><td>' + data[i].created + '</td>'+
                    '<td>' + data[i].due + '</td><td>' + data[i].status + '</td>'+
                    '<td><button type="button" class="btn btn-default js-delete glyphicon glyphicon-remove-circle" orderid=' + data[i].id + '></button></td>'+
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

function create_order() {

    var order = {};
    order["shop"] = $("#create-modal-form-shop").val();
    order["cake"] = $("#create-modal-form-cake").val();
    order["quantity"] = $("#create-modal-form-quantity").val();
    order["message"] = $("#create-modal-form-message").val();
    order["created"] = $("#create-modal-form-createdOn").val();
    order["due"] = $("#create-modal-form-dueOn").val();
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

function validate_order(orderid) {

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/api/orders/" + orderid ,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            console.log($('#cakeFullTable tr').eq(orderid));

            $('#tr'+orderid).find("td").eq(7).html('DONE');

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

function delete_order(orderid) {

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/api/orders/" + orderid ,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

          /* nothing to do :
             actually a cod 200 is interpreted as error
             and swagger says I should receive a 204
             ...pb jQuery?
          */
        },
        error: function (e) {
            $('#tr'+orderid).find("td").remove();

        }
    });

}