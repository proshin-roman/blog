<#-- @ftlvariable name="posts" type="java.util.List<org.proshin.blog.model.Post>"-->
<#-- @ftlvariable name="post" type="org.proshin.blog.model.Post"-->

<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro title><@spring.message "page.admin.posts.list.title"/> :: <@spring.message "blog-title"/></#macro>
<#macro page_body>
<div class="row">
    <div class="col-md-12">
        <h1>
            <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#dlgCreatePost">
                <@spring.message code="page.admin.posts.list.create-new-post.title"/>
            </button>
            <@spring.message "page.admin.posts.list.title"/>
        </h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <table class="table table-striped table-condensed">
            <thead>
                <tr>
                    <th><@spring.message code="page.admin.posts.list.column.title"/></th>
                    <th><@spring.message code="page.admin.posts.list.column.created-at"/></th>
                    <th><@spring.message code="page.admin.posts.list.column.published"/></th>
                </tr>
            </thead>
            <tbody>
                <#list posts as post>
                    <tr>
                        <td>
                            <b>
                                <a href="/post/${post.url().encoded()}"
                                   title="<@spring.message code="page.admin.posts.list.title.title"/>">
                                    ${post.title()}
                                </a>
                            </b>
                            <br/>
                            <small>
                                <a href="/post/${post.url().encoded()}"
                                   title="<@spring.message code="page.admin.posts.list.title.url"/>">
                                    /post/<b>${post.url().decoded()}</b>
                                </a>
                                <br/>
                                <a href="<@spring.url "/admin/posts/${post.url().encoded()}/edit" />">
                                    <@spring.message code="page.admin.posts.list.action.edit"/>
                                </a> |
                                <#if post.published() >
                                    <a href="/admin/posts/${post.url().encoded()}/unpublish">
                                        <@spring.message code="page.admin.posts.list.action.unpublish"/>
                                    </a> |
                                <#else>
                                    <a href="/admin/posts/${post.url().encoded()}/publish">
                                        <@spring.message code="page.admin.posts.list.action.publish"/>
                                    </a> |
                                </#if>
                                <a href="/admin/posts/${post.url().encoded()}/delete">
                                    <@spring.message code="page.admin.posts.list.action.delete"/>
                                </a>
                            </small>
                        </td>
                        <td>${post.creationDate().format("HH:mm dd.MM.yyyy")}</td>
                        <td>
                            <#if post.published()>
                                <@spring.message code="page.admin.posts.list.column.published.yes"/>
                            <#else>
                                <@spring.message code="page.admin.posts.list.column.published.no"/>
                            </#if>
                            <#if post.publicationDate()??>
                                / ${post.publicationDate().format("HH:mm dd.MM.yyyy")}
                            </#if>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

<div id="dlgCreatePost" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title"><@spring.message code="page.admin.posts.list.modal.title"/></h4>
            </div>
            <div class="modal-body">
                <form id="fmCreatePost" action="/admin/posts/create" method="post">
                    <div class="form-group">
                        <label for="url"><@spring.message code="page.admin.posts.list.modal.label.url"/></label>
                        <input type="text" class="form-control" id="url" name="url" placeholder="URL" required>
                    </div>
                    <div class="form-group">
                        <label for="title"><@spring.message code="page.admin.posts.list.modal.label.title"/></label>
                        <input type="text" class="form-control" id="title" name="title" placeholder="Title">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default" form="fmCreatePost" data-dismiss="modal">
                    <@spring.message code="page.admin.posts.list.modal.button.cancel"/>
                </button>
                <button type="submit" class="btn btn-primary" form="fmCreatePost">
                    <@spring.message code="page.admin.posts.list.modal.button.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
</#macro>

<@display_page/>