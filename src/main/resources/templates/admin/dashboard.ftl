<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title><@spring.message "page.admin.dashboard.title"/></title>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
              integrity="sha256-eZrrJcwDc/3uDhsdt61sL2oOBY362qM3lon1gyExkL0=" crossorigin="anonymous"/>

        <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#"><@spring.message "page.admin.dashboard.title"/></a>
                    </div>
                </div>
            </nav>
            <div class="row">
                <div class="col-md-3 profile">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <button id="auth-via-ig" class="btn btn-default">
                                <i class="fa fa-instagram" aria-hidden="true"></i>
                            <@spring.message "page.admin.dashboard.button.authorize-via-instagram"/>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-9 posts-container">
                </div>
            </div>
        </div>

        <script type="application/javascript">
            var CLIENT_ID = 'e0586dc0ceae4dcc8a7e332b19877d4c';
            var REDIRECT_URI = 'http://localhost:8080/oauth/instagram/redirect';
            $(function () {
                $('#auth-via-ig').click(function () {
                    var authWindow = window.open(
                            'https://api.instagram.com/oauth/authorize/?' +
                            'client_id='
                            + CLIENT_ID
                            + '&redirect_uri=' + REDIRECT_URI
                            + '&response_type=code',
                            'Авторизация в Instagram');
                });
            });
        </script>
    </body>
</html>