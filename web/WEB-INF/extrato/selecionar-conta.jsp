<jsp:include page="/WEB-INF/header.jsp" />
<div class="container">
    <div class="row">
        <a class="col s12 waves-effect waves-light btn" 
           href="SaldoServlet?action=view&tipo=1">Conta corrente</a>
    </div>
    <div class="row">
        <a class="col s12 waves-effect waves-light btn" 
           href="SaldoServlet?action=view&tipo=2">Conta poupança</a>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />