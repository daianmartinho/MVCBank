<%-- 
    Document   : message
    Created on : 27/06/2015, 22:53:14
    Author     : Daian
--%>

<jsp:include page="/WEB-INF/header.jsp"/>
<div class="container">
    <div class="row">
        <h6 class="center">${msg}</h6>

    </div>
    <div class="row">
        <a class="col s12 waves-effect waves-light btn"            
           href="javascript: window.history.go(-3)">Voltar</a>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp"/>