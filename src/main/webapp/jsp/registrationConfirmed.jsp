<html>
    <head>
        <title>
            Registration Confirmation
        </title>

    </head>

    <body>
        <h1> Your Account has been confirmed. Please login. </h1>

        <form name="f" action="/login" method="post">
            <button type="submit" class="btn">Login</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </body>
</html>
