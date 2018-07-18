<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title><@spring.message "page.login.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<script src="https://www.google.com/recaptcha/api.js"></script>

<style>
    .input-group {
        margin-bottom: 25px;
    }

    .g-recaptcha {
        margin-bottom: 25px;
        width: 100%
    }

    .g-recaptcha > div {
        margin: 0 auto;
    }
</style>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><@spring.message "page.login.title"/></h3>
    </div>

    <div style="padding-top:30px" class="panel-body">

        <form class="form-horizontal" role="form" method="post">

            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="login-username" type="text" class="form-control" name="username"
                       value="admin" placeholder="username or email">
            </div>

            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="login-password" type="password" class="form-control" name="password"
                       value="123456" placeholder="password">
            </div>

            <div class="input-group">
                <div class="checkbox">
                    <label>
                        <input id="login-remember" type="checkbox" name="remember-me" checked>
                        <@spring.message "page.login.form.remember-me"/>
                    </label>
                </div>
            </div>

            <div class="g-recaptcha input-group" data-sitekey="${reCaptchaKey}"></div>

            <div style="margin-top:10px" class="form-group">
                <div class="col-sm-12 controls">
                    <button id="btn-login" href="#" class="btn btn-primary btn-block btn-lg" type="submit">
                        <@spring.message "page.login.form.submit"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</#macro>

<@display_page/>