$(document).ready(function () {

    $("#caption-area").hide();
    $("#products-area").hide();
    $("#orders-area").show();
    list_orders();

    $(document).on("click", '.js-about', function(event) {
        event.preventDefault();
        $("#caption-area").show();
        $("#products-area").hide();
        $("#orders-area").hide();
    });

    $(document).on("click", '.js-list-orders', function(event) {
        event.preventDefault();
        $("#caption-area").hide();
        $("#products-area").hide();
        $("#orders-area").show();
        list_orders();
    });

    $(document).on("click", '.js-create-order', function(event) {
        event.preventDefault();
        create_order();
        $("#create-order-modal").modal('toggle');
    });

    $(document).on("click", '.js-delete-order', function(event) {
        event.preventDefault();
        var orderid = event.target.getAttribute("orderid");
        delete_order(orderid);
    });

    $(document).on("click", '.js-validate-order', function(event) {
        event.preventDefault();
        var orderid = event.target.getAttribute("orderid");
        validate_order(orderid);
    });

    $(document).on("click", '.js-list-products', function(event) {
        event.preventDefault();
        $("#caption-area").hide();
        $("#products-area").show();
        $("#orders-area").hide();
        list_products();
    });

    $(document).on("click", '.js-create-product', function(event) {
        event.preventDefault();
        create_product();
    });

    $(document).on("click", '.js-edit-product', function(event) {
        event.preventDefault();
        update_product();
        $("#edit-product-modal").modal('toggle');
    });

    $(document).on("click", '.js-select-product', function(event) {
        event.preventDefault();

        console.log(event);
        console.log(event.target);

        $("#delete-product-modal-form-id").val($(this).closest("tr").find("td:eq(0)").text().trim());
        $("#delete-product-modal-form-name").val($(this).closest("tr").find("td:eq(1)").text().trim());
        $("#delete-product-modal-form-label").val($(this).closest("tr").find("td:eq(2)").text().trim());
    });

    $(document).on("click", '.js-delete-product', function(event) {
        event.preventDefault();

        var productid = $("#delete-product-modal-form-id").val();

        delete_product(productid);

        $("#delete-product-modal").modal('toggle');
    });
});

function create_order() {

    var DEBUG_json = false;

    var order = {};
    order["shop"] = $("#create-order-form-shop").val();
    order["product"] = $("#create-order-form-product").val();
    order["quantity"] = $("#create-order-form-quantity").val();
    order["message"] = $("#create-order-form-message").val();
    order["created"] = $("#create-order-form-createdOn").val();
    order["due"] = $("#create-order-form-dueOn").val();
    order["status"] = "NEW";

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/"+order.shop+"/orders",
        data: JSON.stringify(order),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            if(DEBUG_json) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#error-area').html(json);
            }

            console.log("SUCCESS : ", data);
            $("#btn-create").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#error-area').html(json);

            console.log("ERROR : ", e);
            $("#btn-create-create").prop("disabled", false);

        }
    });

}

function list_orders() {

    var DEBUG_json = false;

    var search = {};
    search["shop"] = $("#search-orders-form-shopname").val();
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
                $('#error-area').html(json);
            }

            var trHTML = '';

            $.each(data, function (i, item) {

                trHTML += '<tr id="tr'+ data[i].id + '" class="js-select-product">'+
                    '<td>' + data[i].id + '</td><td>' + data[i].shop + '</td>'+
                    '<td>' + data[i].product + '</td><td>' + data[i].quantity + '</td>'+
                    '<td>' + data[i].message + '</td><td>' + data[i].created + '</td>'+
                    '<td>' + data[i].due + '</td><td>' + data[i].status + '</td>'+
                    '<td><button type="button" class="btn btn-default js-validate-order glyphicon glyphicon-ok-circle" orderid=' + data[i].id + '></button></td>'+
                    '<td><button type="button" class="btn btn-default js-delete-order glyphicon glyphicon-remove-circle" orderid=' + data[i].id + '></button></td>'+
                    +'</tr>';
            });

            $('#orders-table-body').html(trHTML);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#error-area').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}

function validate_order(orderid) {

    var DEBUG_json = false;

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/api/orders/" + orderid ,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            console.log($('#orders-table tr').eq(orderid));

            $('#tr'+orderid).find("td").eq(7).html('DONE');

            if(DEBUG_json) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#error-area').html(json);
            }
            console.log("SUCCESS : ", data);
            $("#btn-create").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#error-area').html(json);

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

function list_products(){

    var DEBUG_json = false;

    var search = {};
    search["shop"] = $("#search-orders-form-shopname").val();
    if(search.shop === "all"){
        search["shop"] = "";
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/products",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            if(DEBUG_json) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#error-area').html(json);
            }

            var trHTML = '';


            $.each(data, function (i, item) {

                trHTML += '<tr id="tr'+ data[i].id + '">'+
                    '<td>' + data[i].id + '</td><td>' + data[i].name + '</td>'+
                    '<td>' + data[i].label + '</td><td>' + data[i].created + '</td>'+
                    '<td>' + data[i].status + '</td>' +
                    '<td><button type="button" class="btn btn-default glyphicon glyphicon-pencil" data-toggle="modal" data-target="#edit-product-modal" productid=' + data[i].id + '></button></td>'+
                    '<td><button type="button" class="btn btn-default glyphicon glyphicon-remove-circle" data-toggle="modal" data-target="#delete-product-modal"  productid=' + data[i].id + '></button></td>'+
                    +'</tr>';
            });


            $('#products-table-body').html(trHTML);

            console.log("SUCCESS : ", data);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#error-area').html(json);

            console.log("ERROR : ", e);

        }
    });

}

function create_product() {

    var DEBUG_json = false;

    var product = {};
    product["name"] = $("#create-product-form-name").val();
    product["label"] = $("#create-product-form-label").val();
    product["created"] = $("#create-order-form-created").val();
    product["status"] = "NEW";

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/products",
        data: JSON.stringify(product),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            if(DEBUG_json) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#error-area').html(json);
            }

            console.log("SUCCESS : ", data);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#error-area').html(json);

            console.log("ERROR : ", e);

        }
    });

}

function update_product(){
    alert ("update ciccio");
}

function delete_product(productid){
    alert ("delete ciccio");

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/api/products/" + productid ,
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