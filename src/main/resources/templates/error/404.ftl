<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro page_body>
<h1><@spring.message "page.error.404.header"/></h1>
</#macro>

<@display_page/>