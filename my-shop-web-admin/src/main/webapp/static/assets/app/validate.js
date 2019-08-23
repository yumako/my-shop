/**
 * 函数对象
 */
var Validate = function () {


    /**
     * 初始化 jquery validate
     */
    var handlerInitValidate = function () {
        $("#inputForm").validate({
            errorElement: 'span',
            errorClass: 'help-block',

            errorPlacement: function (error, element) {
                element.parent().parent().attr("class", "form-group has-error");
                error.insertAfter(element);
            }
        });
    };
    /**
     * 增加自定义验证规则
     */
    var handlerInitCustomValidate = function () {
        $.validator.addMethod("mobile", function(value, element) {
            var length = value.length;
            var mobile =  /^[1][3,4,5,7,8][0-9]{9}$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "手机号码格式错误，请输入11位手机号码");

        $.validator.addMethod("email",function(value,element){
            var email = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
            return this.optional(element) || email.test(value);
        },"邮箱格式错误，请输入带@格式的邮箱");

        $.validator.addMethod("password",function(value,element){
            var password = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
            return this.optional(element) || password.test(value);
        },"密码格式错误，请输入由6-21位字母和数字组成，不能是纯数字或纯英文的密码");

        $.validator.addMethod("username",function(value,element){
            var username = /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4E00-\u9FA5]{1,15}$/;
            return this.optional(element) || username.test(value);
        },"用户名格式错误，请输入由字母、数字、下划线和中文组成，以中文或字母开头，长度为2-16位的用户名");
    };
    return {
        /**
         * 初始化
         */
        init:function () {
            handlerInitValidate();
            handlerInitCustomValidate();
        }
    }
}();

$(document).ready(function () {
   Validate.init();
});