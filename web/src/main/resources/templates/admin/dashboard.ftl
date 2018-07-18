<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title><@spring.message "page.admin.dashboard.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<div class="row">
    <div class="col-lg-3">
        <a href="/admin/posts/"><@spring.message "page.admin.posts.list.title"/></a>
    </div>
</div>
</#macro>

<@display_page/>