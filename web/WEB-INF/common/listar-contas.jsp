<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/header.jsp" />
<div class="container">

    <c:forEach var="conta" items="${usuario.contas}">

        <div class="row">
            <a class="col s12 waves-effect waves-light btn" 
               href="${nomeServlet}?action=${action}&id=${conta.id}">${conta.tipo.descricao}</a>
        </div>
        <div class="card-panel">
            <h3 class="center">${msg}</h3>
        </div>
    </c:forEach>


</div>
<jsp:include page="/WEB-INF/footer.jsp" />