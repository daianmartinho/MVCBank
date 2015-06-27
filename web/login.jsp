<%-- 
    Document   : login
    Created on : 17/06/2015, 16:31:31
    Author     : Daian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Banco DW</title>
    </head>
    <body>
        <h3>Conta</h3>
        <form action="LoginServlet" method="post">
            <table>
                <tr>
                    <td>Agencia: <input type="text" name="agencia" /></td>
                    <td>Conta: <input type="text" name="conta" /></td>                    
                    <td><input type="submit" name="login" value="Entrar" /></td>
                </tr>
                    
            </table>
        </form>


    </body>
</html>
