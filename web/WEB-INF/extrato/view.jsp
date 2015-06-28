<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="models.Conta"%>
<jsp:include page="/WEB-INF/header.jsp" />
<%@page import="models.Operacao"%>
<%@page import="java.util.List"%>
<div class="container">
    <div class="row">
        <table class="responsive-table striped">
            <thead>
                <tr>
                    <th>Tipo da Operação</th>
                    <th>Data</th>
                    <th>Valor</th>
                </tr>
            </thead>
            <tbody>
                <% for(Operacao o : (List<Operacao>)request.getAttribute("operacoes")) {%>
                <tr>
                    <td> <%=o.getTipo().getDescricao() %></td>
                    <td> <%=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(o.getData()) %>
                    <td> <%=o.getValor() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <div class="row">
            <a class="col s12 waves-effect waves-light btn"            
               href="javascript: window.history.go(-2)">Voltar</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />