/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var email = document.querySelector('input[name="email"]');
var conditionChecked1 = true;
var conditionChecked2 = true;

email.addEventListener("change", function () {
    if (email.value) {
        $.ajax({
            url: 'useraction.htm',
            type: "POST",
            data: {
                action: "validateCustomer",
                email: email.value
            },
            dataType: 'json',
            success: function (data) {
                if (data.exists === true) {
                    conditionChecked1 = false;
                } else {
                    conditionChecked1 = true;
                }
            },
            error: function (error) {
                console.log('Error ${error}');
            }
        });
        $.ajax({
            url: 'sellermanagement.htm',
            type: "POST",
            data: {
                action: "validateSeller",
                email: email.value
            },
            dataType: 'json',
            success: function (data) {
                if (data.exists === true) {
                    conditionChecked2 = false;
                } else {
                    conditionChecked2 = true;
                }
            },
            error: function (error) {
                console.log('Error ${error}');
            }
        });
    }
});

function validateForm() {
    if (conditionChecked1 && conditionChecked2) {
        return true;
    } else {
        alert(email.value + ' already exits!!!');
        return false;
    }
}

