<jsp:include page="/WEB-INF/header.jsp" />


<div class="container">
    <h6 class="center">${msg}</h6>
    <form action="SaqueServlet" method="post">
        <div class="input-field col s12">
            <input class="validate" type="text" name="valor" id="valor"/>
            <label for="valor">Valor</label>
            
            <input type="hidden" name="action" value="confirm" />
        </div>
        <div class="row">
            <button class="btn waves-effect waves-light col offset-s5" type="submit">Confirmar
                <i class="mdi-content-send right"></i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/footer.jsp" />