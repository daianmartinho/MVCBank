<%-- 
    Document   : index
    Created on : 26/06/2015, 18:14:42
    Author     : Daian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="SaqueServlet" method="post">
            <table>
                <tr>                    
                    <td>Valor: <input type="text" name="valor" /></td> 
                     <%HttpSession sessao = request.getSession();
                    sessao.setAttribute("action","confirm");%>
                    <td><input type="submit" name="saque" value="Confirmar" /></td>
                </tr>
                    
            </table>
        </form>
    </body>
</html>
