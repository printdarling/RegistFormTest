$(function(){

    //实现页面切换
    $(".goRegist").click(function(){
        $("#loginForm").css({"display":"none"});
        $("#registForm").css({"display":"block"});
    });
    $(".goLogin").click(function(){
        $("#registForm").css({"display":"none"});
        $("#loginForm").css({"display":"block"});
    });


    //实现鼠标自动聚焦
    $("input").mouseover(function(){
        $(this).focus();
    });



    //登录
    $("#loginbtn").click(function(){
        let email = $("#loginForm .email input")[0].value;
        let password = $("#loginForm .pwd input")[0].value;
        if(email.length == 0 || password.length == 0){
            alert("邮箱和密码不能为空！");
            return;
        }

        let user = {
            email: email,
            password: password
        };
        const json = JSON.stringify(user);

        $.ajax({
            type: "post",
            url: "./LoginServlet",
            data:json,
            dataType: "json",
            success: function(response){
                if (response) {
                    alert("登录成功！");
                    window.location.href= "success.html";
                }else{
                    alert("邮箱或密码错误！");
                }
            }
        });

    });





    //注册
    $("#registbtn").click(function(){
        let email = $("#registForm .email input")[0].value;
        let password = $("#registForm .pwd1 input")[0].value;
        let password2 = $("#registForm .pwd2 input")[0].value;

        let reg = /.+@\\w+\\.\\w+/;
        if (reg.test(email)) {
            alert("邮箱格式有误!");
          $("#registForm .email input").val('');
          return ;
        }

        if(email.length == 0 || password.length == 0){
            alert("邮箱和密码不能为空！");
            return;
        }

        if(password != password2){
            alert("两次输入密码不一致！");
            $("#registForm .pwd1 input").val('');
            $("#registForm .pwd2 input").val('');
            return;
        }
        if(password.length < 6){
            alert("密码太短！");
            $("#registForm .pwd1 input").val('');
            $("#registForm .pwd2 input").val('');
            return;
        }
        if(password.length > 54){
            alert("密码太长！");
            $("#registForm .pwd1 input").val('');
            $("#registForm .pwd2 input").val('');
            return;
        }

        let user = {
            email: email,
            password: password
        };

        console.log(user);

        const json = JSON.stringify(user);


        $.ajax({
            type: "post",
            url: "./RegistServlet",
            data:json,
            dataType: "json",
            success: function(response){
                console.log(response);

                if(response){
                    alert("注册成功！");
                    $("#registForm").css({"display":"none"});
                    $("#loginForm").css({"display":"block"});
                }
                
                if(!response){
                    alert("该邮箱已被注册！");
                }
            },
        });

    });


});