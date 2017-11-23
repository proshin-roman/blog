<#import "/spring.ftl" as spring>
<#include "base.ftl">

<#macro page_body>
    <#list posts as post>
    <div class="panel panel-default">
        <div class="panel-heading">
            <a href="/post/${post.id}">
                <h3 class="panel-title">${post.title}</h3>
            </a>
        </div>
        <div class="panel-body">
            TO BE DONE LATER
            <div class="tags">
                <button type="button" class="btn btn-default btn-xs">tag #1</button>
                <button type="button" class="btn btn-default btn-xs">tag #2</button>
                <button type="button" class="btn btn-default btn-xs">tag #3</button>
            </div>
        </div>
    </div>
    </#list>
</#macro>

<@display_page/>