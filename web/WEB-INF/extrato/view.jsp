<jsp:include page="/WEB-INF/header.jsp" />
<%@page import="models.Operacao"%>
<%@page import="java.util.List"%>
<div class="container">
    <div class="row">
        <table>
            <thead></thead>
            <tbody>
                <% for(Operacao o : (List<Operacao>)request.getAttribute("operacoes")) {%>
                <tr>
                    <td> <%=o.getTipo().getDescricao() +" " +o.getValor() %>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />