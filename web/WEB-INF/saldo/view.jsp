<jsp:include page="/WEB-INF/header.jsp" />
<div class="container">
    <div class="row">
        ${msg}
    </div>
    <div class="row">
        <a class="col s12 waves-effect waves-light btn"            
           href="ControladorOperacoesServlet?servlet=MenuServlet&action=index">Voltar</a>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />