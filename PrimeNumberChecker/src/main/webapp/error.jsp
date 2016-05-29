<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  String error = (String) request.getAttribute("error");%>

<html>
<head>

    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="icon" href="http://math-games.sweb.cz/greek-alphabet-coloring-pages/letter/pi.png">
    <title>Error</title>

</head>
<body>
    <div class="wrapper">
        <h1><%=error%></h1>
        <p>Gyldig input er tall i mellom 0-9 og kan ikke være større enn 2147483647.</p>
        <form>
            <input type="button" value="Gå tilbake"
                   onClick="history.go(-1); return true;">
        </form>
    </div>
</body>
</html>
