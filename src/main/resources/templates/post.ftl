<#-- @ftlvariable name="post" type="org.proshin.blog.model.Post"-->
<#-- @ftlvariable name="content" type="java.lang.String"-->
<#-- @ftlvariable name="authInfo" type="org.proshin.blog.configuration.FreemarkerConfig.AuthInfo"-->

<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro title>${post.title()} :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
    <#if authInfo.authorized() >
        <div class="alert alert-warning" role="alert">
            <@spring.message "page.post.not-published"/>
        </div>
    </#if>
<h1>${post.title()}</h1>
    ${content}
</#macro>

<@display_page/>