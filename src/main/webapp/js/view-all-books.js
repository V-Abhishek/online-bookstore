/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var orderItems = [];
var ordered = false;

$('input[type=submit]').on('click', function () {
    if (orderItems.length > 0) {
        $("input[name='bookIds']").val(orderItems);
        ordered = true;
    } else {
        ordered = false;
    }

});

function placeOrder() {
    if (ordered) {
        return true;
    } else {
        alert("Sorry, your cart is empty");
        return false;
    }
}

$('p').click(function () {
    $(this).closest('tr').find("input").each(function () {
        var productId = $(this).closest('tr').attr('id');
        var quantity = this.value;
        orderItems.push(productId);
        orderItems.push(quantity);
        $(this).closest('tr').hide();
    });
});


