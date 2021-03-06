<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Edit Users</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="cover">
    <div class="navbar">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><span>Brand</span></a>
            </div>
            <div class="collapse navbar-collapse" id="navbar-ex-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active">
                        <a href="/">Home</a>
                    </li>
                    <li>
                        <a href="/editUsers">Edit Users</a>
                    </li>
                    <li>
                        <a href="/inviteAdmins">Invite Admins</a>
                    </li>
                    <li>
                        <a href="#">Contacts</a>
                    </li>

                    <li>
                        <form name="f" action="/logout" method="post">
                            <c:if test="${not empty pageContext.request.remoteUser}">
                                <div>
                                    <button type="submit" class="btn">Log out</button>
                                </div>

                            </c:if>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="cover-image" style="background-image: url(https://unsplash.imgix.net/photo-1418065460487-3e41a6c84dc5?q=25&amp;fm=jpg&amp;s=127f3a3ccf4356b7f79594e05f6c840e);"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-center" style="background-color: white">
                <form name="f" action="/sendInvite" method="post">
                    <h1>Invite Admin</h1>
                    <table align="center">
                        <th></th>
                        <th></th>
                        <tr>
                            <td><label> Email : </label></td>
                            <td><input type="text" name ="Email"></td>
                        </tr>
                        <tr>
                            <td><label> Password : </label></td>
                            <td><input type ="text" name ="Password"></td>
                        </tr>

                    </table>
                    <br>
                    <input type="submit" name ="saveButton"value="sendInvite">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
