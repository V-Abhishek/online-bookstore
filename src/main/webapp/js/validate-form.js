/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
document.addEventListener("DOMContentLoaded", function () {
    // JavaScript form validation
    var checkPassword = function (str)
    {
        var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
        return re.test(str);
    };

    var checkForm = function (e)
    {
        if (this.pwd1.value != "" && this.pwd1.value == this.pwd2.value) {
            if (!checkPassword(this.pwd1.value)) {
                alert("The password you have entered is not valid!");
                this.pwd1.focus();
                e.preventDefault();
                return;
            }
        } else {
            alert("Error: Please ensure that you have confirmed the same password!");
            this.pwd1.focus();
            e.preventDefault();
            return;
        }
        //alert("Both username and password are VALID!");
    };

    var myForm = document.getElementById("form_login");
    myForm.addEventListener("submit", checkForm, true);

    // HTML5 form validation
    var supports_input_validity = function ()
    {
        var i = document.createElement("input");
        return "setCustomValidity" in i;
    };

    if (supports_input_validity()) {
        var pwd1Input = document.getElementById("field_pwd1");
        pwd1Input.setCustomValidity(pwd1Input.title);
        var pwd2Input = document.getElementById("field_pwd2");
        // input key handlers
        pwd1Input.addEventListener("keyup", function (e) {
            this.setCustomValidity(this.validity.patternMismatch ? pwd1Input.title : "");
            if (this.checkValidity()) {
                pwd2Input.pattern = RegExp.escape(this.value);
                pwd2Input.setCustomValidity(pwd2Input.title);
            } else {
                pwd2Input.pattern = this.pattern;
                pwd2Input.setCustomValidity("");
            }
        }, false);

        pwd2Input.addEventListener("keyup", function (e) {
            this.setCustomValidity(this.validity.patternMismatch ? pwd2Input.title : "");
        }, false);
    }
}, false);


