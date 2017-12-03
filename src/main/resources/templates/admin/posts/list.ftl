<#-- @ftlvariable name="posts" type="java.util.List<org.proshin.blog.model.Post>"-->
<#-- @ftlvariable name="post" type="org.proshin.blog.model.Post"-->

<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro title><@spring.message "page.admin.posts.list.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<div class="row">
    <div class="col-md-12">
        <h1>
            <a href="/admin/posts/create" class="btn btn-primary pull-right">Создать новую</a>
            <@spring.message "page.admin.posts.list.title"/>
        </h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <table class="table table-striped table-condensed">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Создана</th>
                    <th>Опубликована</th>
                </tr>
            </thead>
            <tbody>
                <#list posts as post>
                    <tr>
                        <td>
                            <p>${post.id}</p>
                        </td>
                        <td>
                            <a href="/post/${post.id}" title="Просмотреть">${post.title}</a>
                            <br/>
                            <small>
                                <a href="<@spring.url "/admin/posts/${post.id}/edit" />">
                                    Редактировать
                                </a> |
                                <#if post.published >
                                    <a href="/admin/posts/${post.id}/unpublish">Снять с публикации</a> |
                                <#else>
                                    <a href="/admin/posts/${post.id}/publish">Опубликовать</a> |
                                </#if>
                                <a href="/admin/posts/${post.id}/delete">Удалить</a>
                            </small>
                        </td>
                        <td>${post.creationDate.format("HH:mm dd.MM.yyyy")}</td>
                        <td>
                        ${post.published?string("Да", "Нет")}
                            <#if post.publicationDate??>
                                / ${post.publicationDate.format("HH:mm dd.MM.yyyy")}
                            </#if>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#macro>

<@display_page/>