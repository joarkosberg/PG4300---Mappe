<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int number = (Integer) request.getAttribute("number");
    boolean isPrime = (boolean)request.getAttribute("prime");
%>

<html>
<head>

    <link rel="stylesheet" type="text/css" href="style.css">
    <link rel="icon" href="http://math-games.sweb.cz/greek-alphabet-coloring-pages/letter/pi.png">
    <title>Checker</title>

</head>
<body>
    <div class="wrapper">
        <h1>Er  <%=number%>   ett primtall?</h1>
        <%if(isPrime){%>
        <p>Ja det er ett primtall</p>
        <%}else{%>
        <p>Nei, ikke ett primtall</p>
        <%}%>


        <form>
            <input type="button" value="Gå tilbake"
                   onClick="history.go(-1); return true;">
        </form>
    </div>
</body>
</html>
