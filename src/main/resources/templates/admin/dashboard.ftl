<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title><@spring.message "page.admin.dashboard.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<div class="row">
    <div class="col-md-3 profile">
        <div class="panel panel-default">
            <div class="panel-body">
                <button id="auth-via-ig" class="btn btn-default">
                    <i class="fa fa-instagram" aria-hidden="true"></i>
                    <@spring.message "page.admin.dashboard.button.authorize-via-instagram"/>
                </button>
            </div>
        </div>
    </div>
    <div class="col-md-9 posts-container">
    </div>
</div>

<script type="application/javascript">
    $(function () {
        $('#auth-via-ig').click(function () {
        });
    });
</script>
</#macro>

<@display_page/>