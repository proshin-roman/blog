<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro page_body>
<h1><@spring.message "page.error.any.header"/></h1>
<p><@spring.message "page.error.any.message"/></p>
</#macro>

<@display_page/>