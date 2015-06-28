<jsp:include page="/WEB-INF/header.jsp" />
<div class="container">
    <div class="row">

        <div class="col s12">
            <div class="card-panel">
                <h3 class="center">${msg}</h3>
            </div>
        </div>

    </div>
    <div class="row">
        <a class="col s12 waves-effect waves-light btn"            
           href="javascript: window.history.go(-2)">Voltar</a>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />