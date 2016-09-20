
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">           
        </script>
        <script>
            $(document).ready(function(){
                $('#all_routes').change(function(){
                    var routeId = parseInt($(this).val());
                    console.log(routeId);
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8080/check_routes/api/get_sections?route_id=' + routeId      
                    }).done(function(data) {
                        console.log(data);
                    });
                });
            });
        </script>
    </head>
    <body>                 
        <h1>Hello World!</h1>
        <br>
        <p>Select routes:</p>
        <!-- -->
        <select id="all_routes">
            <c:forEach items = "${marshruts}" var="marshrut">
                <option value="${marshrut.routeId}">                
                       ${marshrut.routeName}
                </option>
            </c:forEach>
        </select>             
    </body>
</html>
