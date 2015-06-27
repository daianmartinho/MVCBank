<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/header.jsp" />
<div class="container">
   
    <c:forEach var="conta" items="${usuario.contas}">
        
        <div class="row">
            <a class="col s12 waves-effect waves-light btn" 
               href="SaqueServlet?action=create&tipo=${conta.tipo.id}">${conta.tipo.descricao}</a>
        </div>
    </c:forEach>


</div>
<jsp:include page="/WEB-INF/footer.jsp" />