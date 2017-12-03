<#import "/spring.ftl" as spring>
<#include "../base.ftl">

<#macro title>${post.title} :: <@spring.message "page.admin.posts.editor.title"/> </#macro>
<#macro page_body>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

<textarea id="editor">
${post.content}
</textarea>

<script>
    var editor = new SimpleMDE({element: document.getElementById("editor")});
</script>
</#macro>

<@display_page/>