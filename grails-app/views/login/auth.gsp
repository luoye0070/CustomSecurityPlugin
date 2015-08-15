<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
    <title>后台登录</title>
   <link rel="stylesheet" href="${resource(dir:'css/login/css',file:'style.css')}">
  <link rel="stylesheet" href="${resource(dir:'css/login/css',file:'demo.css')}">

</head>

<body>
<div class="container">


    <section>
        <div id="container_demo" >
            <div id="wrapper">
                <div id="login" class="animate form">
                    <form  action='${postUrl}' method='POST' autocomplete="on" id="loginForm">
                        <h1>系统登录</h1>
    <g:if test='${flash.message}'>
                        <p style="color:red;text-align: center">
                            ${flash.message}
                        </p>
    </g:if>
                        <p>
                            <label for="username" class="uname" data-icon="u" > 用户名 </label>
                            <input id="username" name="username" required="required" type="text" />
                        </p>
                        <p>
                            <label for="password" class="youpasswd" data-icon="p"> 密码</label>
                            <input id="password" name="password" required="required" type="password" />
                        </p>
                        <p class="keeplogin">
                            <input type="checkbox" name="${rememberMeParameter}" <g:if test='${hasCookie}'>checked='checked' </g:if>/>
                            <label for="${rememberMeParameter}">记住我</label>
                        </p>
                        <p class="login button">
                            <input type="submit" value="登录" />
                        </p>

                    </form>
                </div>


            </div>
        </div>
    </section>
</div>

<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['username'].focus();
    })();
    // -->
</script>

</body>
</html>
