<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="/WEB-INF/header.jsp" />

<%@page import="java.util.List"%>
<div class="container">
    <div class="row">
        <table >

            <tr>
                <th>Data</th>
                <th>Descrição</th>                
                <th>Valor</th>
            </tr>

            <c:forEach var="operacao" items="${extrato}">
                <tr>
                    <td> ${operacao.data}</td>
                    <td> ${operacao.descricao}</td>
                    <td> ${operacao.valor}</td>
                </tr>

            </c:forEach>
            
        </table>
        <div class="row">
            <a class="col s12 waves-effect waves-light btn"            
               href="javascript: window.history.go(-2)">Voltar</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />