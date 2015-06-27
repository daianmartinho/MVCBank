<%-- 
    Document   : confirm
    Created on : 25/06/2015, 22:29:49
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

        <form action="DepositoServlet" method="post">
            <table>
                <tr>        
                    <td>Agencia:${operacao.conta.agencia.num_agencia}</td>
                    <td>${operacao.conta.tipo.descricao}:${operacao.conta.num_conta}</td>                    
                    <td>Nome:${operacao.conta.usuario.nome}</td>
                    <td>Valor:${operacao.valor}</td>
                    <%HttpSession sessao = request.getSession();
                    sessao.setAttribute("action","create");%>
                    <td><input type="submit" name="confirma_deposito" value="Confirmar" /></td>
                </tr>

            </table>
        </form>

    </body>
</html>
