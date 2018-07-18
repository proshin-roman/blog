<#-- @ftlvariable name="posts" type="java.util.List<org.proshin.blog.model.Post>"-->
<#-- @ftlvariable name="post" type="org.proshin.blog.model.Post"-->

<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro page_body>
    <#list posts as post>
    <dl>
        <dt>
            <a href="/post/${post.url().encoded()}">
                <h2>${post.title()}</h2>
            </a>
        </dt>
        <dd>
            ${post.publicationDate().get().format("dd.MM.yyyy")}
            <#if post.shortcut().present>
            <blockquote>
                <p>${post.get().shortcut()}</p>
            </blockquote>
            </#if>
        </dd>
    </dl>
    <#else>
    <p class="lead"><@spring.message "page.index.there-is-no-posts"/></p>
    </#list>
</#macro>

<@display_page/>