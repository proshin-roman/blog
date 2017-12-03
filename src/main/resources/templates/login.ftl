<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title><@spring.message "page.login.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<div class="panel panel-info">
    <div class="panel-heading">
        <div class="panel-title"><@spring.message "page.login.title"/></div>
    </div>

    <div style="padding-top:30px" class="panel-body">

        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

        <form id="loginform" class="form-horizontal" role="form" method="post">

            <div style="margin-bottom: 25px" class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input id="login-username" type="text" class="form-control" name="username"
                       value="admin" placeholder="username or email">
            </div>

            <div style="margin-bottom: 25px" class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input id="login-password" type="password" class="form-control" name="password"
                       value="123456" placeholder="password">
            </div>

            <div class="input-group">
                <div class="checkbox">
                    <label>
                        <input id="login-remember" type="checkbox" name="remember" value="1">
                        <@spring.message "page.login.form.remember-me"/>
                    </label>
                </div>
            </div>

            <div style="margin-top:10px" class="form-group">
                <div class="col-sm-12 controls">
                    <button id="btn-login" href="#" class="btn btn-success" type="submit">
                        <@spring.message "page.login.form.submit"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</#macro>

<@display_page/>