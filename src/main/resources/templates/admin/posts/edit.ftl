<#-- @ftlvariable name="post" type="org.proshin.blog.model.Post"-->

<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro title>${post.title} :: <@spring.message "page.admin.posts.editor.title"/> </#macro>
<#macro page_body>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

<form action="/admin/posts/${post.id}/save" method="post">
    <@spring.bind "post"/>
    <@spring.formHiddenInput "post.id" />

    <div class="form-group">
        <label for="title">Заголовок поста</label>
        <@spring.formInput "post.title" "id=title class=form-control"/>
    </div>

    <div class="form-group">
        <label for="creationDate">Дата создания</label>
        <@spring.formInput "post.creationDate" "id=creationDate class=form-control" "datetime-local"/>
    </div>

    <div class="checkbox">
        <label>
            <@spring.formCheckbox "post.published" "id=published class=form-control" /> Опубликован
        </label>
    </div>

    <div class="form-group">
        <label for="publicationDate">Дата публикации</label>
        <@spring.formInput "post.publicationDate" "id=publicationDate class=form-control" "datetime-local"/>
    </div>

    <@spring.formTextarea "post.content" "id=editor"/>

    <button type="submit" class="btn btn-primary">Сохранить</button>
</form>

<script>
    new SimpleMDE({
        element: document.getElementById("content"),
        forceSync: true
    });
</script>
</#macro>

<@display_page/>