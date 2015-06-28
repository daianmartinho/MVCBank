<jsp:include page="/WEB-INF/header.jsp"/>
<div class="container">
    <div class="row">
        <h2 class="center">${msg}</h2>
        <form action="LoginServlet" method="post">
            <input type="hidden" name="action" value="passCheck"/>
            <div class="input-field col s12">
                <input class="validate" type="password" name="senha" id="senha" required/>
                <label for="senha">Senha</label>
            </div>
            <button class="btn waves-effect waves-light col offset-s5" type="submit">Entrar
                <i class="mdi-content-send right"></i>
            </button>
        </form>
    </div>
</div>
<jsp:include page="/WEB-INF/footer.jsp"/>