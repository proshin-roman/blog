<#import "/spring.ftl" as spring>
<#macro title><@spring.message "blog-title"/></#macro>
<#macro page_body></#macro>

<#macro display_page>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title><@title/></title>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
              integrity="sha256-eZrrJcwDc/3uDhsdt61sL2oOBY362qM3lon1gyExkL0=" crossorigin="anonymous"/>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <link rel="stylesheet" href="/css/custom.css" crossorigin="anonymous">
    </head>
    <body>
        <!-- Yandex.Metrika counter -->
        <script type="text/javascript">
            (function (d, w, c) {
                (w[c] = w[c] || []).push(function () {
                    try {
                        w.yaCounter46951194 = new Ya.Metrika({
                            id: 46951194,
                            clickmap: true,
                            trackLinks: true,
                            accurateTrackBounce: true,
                            webvisor: true,
                            trackHash: true
                        });
                    } catch (e) {
                    }
                });

                var n = d.getElementsByTagName("script")[0],
                    s = d.createElement("script"),
                    f = function () {
                        n.parentNode.insertBefore(s, n);
                    };
                s.type = "text/javascript";
                s.async = true;
                s.src = "https://mc.yandex.ru/metrika/watch.js";

                if (w.opera == "[object Opera]") {
                    d.addEventListener("DOMContentLoaded", f, false);
                } else {
                    f();
                }
            })(document, window, "yandex_metrika_callbacks");
        </script>
        <noscript>
            <div><img src="https://mc.yandex.ru/watch/46951194" style="position:absolute; left:-9999px;" alt=""/></div>
        </noscript>
        <!-- /Yandex.Metrika counter -->
        <div class="container">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="/"><@spring.message "blog-title"/></a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="/login">Вход</a></li>
                        </ul>
                    </div><!-- /.navbar-collapse -->
                </div>
            </nav>
            <div class="row">
                <div class="col-sm-3 profile">
                    <img src="/img/avatar.jpg" class="img-responsive img-rounded avatar"/>
                    <h2><@spring.message "my-name"/></h2>
                    <p><@spring.message "profession"/></p>
                    <p class="social-links">
                        <a href="https://vk.com/proshin_roman" title="VKontakte" class="icon">
                            <i class="fa fa-vk" aria-hidden="true"></i>
                        </a>
                        <a href="https://www.facebook.com/proshin.roman" title="Facebook" class="icon">
                            <i class="fa fa-facebook-square" aria-hidden="true"></i>
                        </a>
                        <a href="https://www.instagram.com/proshin_roman/" title="Instagram" class="icon">
                            <i class="fa fa-instagram" aria-hidden="true"></i>
                        </a>
                        <a href="https://github.com/proshin-roman" title="GitHub" class="icon">
                            <i class="fa fa-github" aria-hidden="true"></i>
                        </a>
                        <a href="https://stackoverflow.com/users/2265056/roman-proshin" title="StackOverflow"
                           class="icon">
                            <i class="fa fa-stack-overflow" aria-hidden="true"></i>
                        </a>
                        <a href="https://twitter.com/proshin_roman" title="Twitter" class="icon">
                            <i class="fa fa-twitter-square" aria-hidden="true"></i>
                        </a>
                    </p>
                </div>
                <div class="col-sm-9 posts-container">
                    <@page_body/>
                </div>
            </div>
        </div>

    <#-- TODO: uncomment it when JS is really needed -->
    <#--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>-->
    </body>
</html>
</#macro>