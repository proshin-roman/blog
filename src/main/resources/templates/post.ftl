<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title>${post.title} :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<h1>${post.title}</h1>
${content}
</#macro>

<@display_page/>