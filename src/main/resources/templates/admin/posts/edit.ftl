<#-- @ftlvariable name="post" type="org.proshin.blog.page.admin.AdminPostsPagesController.ChangedPost"-->

<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro title>${post.title} :: <@spring.message "page.admin.posts.editor.title"/> </#macro>
<#macro page_body>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

<form action="/admin/posts/${post.url}/save" method="post">
    <@spring.bind "post"/>

    <div class="form-group">
        <label for="url"><@spring.message code="page.admin.posts.editor.label.url" /> </label>
        <@spring.formInput "post.url" "id=url class=form-control"/>
    </div>

    <div class="form-group">
        <label for="title"><@spring.message code="page.admin.posts.editor.label.title"/></label>
        <@spring.formInput "post.title" "id=title class=form-control"/>
    </div>

    <div class="form-group">
        <label for="shortcut"><@spring.message code="page.admin.posts.editor.label.shortcut"/></label>
        <@spring.formInput "post.shortcut" "id=shortcut class=form-control"/>
    </div>

    <div class="form-group">
        <label for="creationDate"><@spring.message code="page.admin.posts.editor.label.creation-date"/></label>
        <@spring.formInput "post.creationDate" "id=creationDate class=form-control" "datetime-local"/>
    </div>

    <div class="checkbox">
        <label>
            <@spring.formCheckbox "post.published" "id=published class=form-control" /> <@spring.message code="page.admin.posts.editor.label.published"/>
        </label>
    </div>

    <div class="form-group">
        <label for="publicationDate"><@spring.message code="page.admin.posts.editor.label.publication-date"/></label>
        <@spring.formInput "post.publicationDate" "id=publicationDate class=form-control" "datetime-local"/>
    </div>

    <@spring.formTextarea "post.content" "id=editor"/>

    <button type="submit" class="btn btn-primary"><@spring.message code="page.admin.posts.editor.button.save"/></button>
</form>

<script>
    new SimpleMDE({
        element: document.getElementById("content"),
        forceSync: true
    });
</script>
</#macro>

<@display_page/>