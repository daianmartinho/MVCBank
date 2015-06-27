<jsp:include page="/WEB-INF/header.jsp"/>
    <div class="container">
        <div class="row">
            <form class="col s12" method="post" action="LoginServlet">
                 <%HttpSession sessao = request.getSession();
                    sessao.setAttribute("action","userCheck");%>
                <div class="input-field col s12">
                    <input class="validate" type="text" name="agencia" id="agencia" required/>
                    <label for="agencia">Agencia</label>
                </div>
                <div class="input-field col s12">
                    <input class="validate" type="text" name="conta" id="conta" required/>
                    <label for="conta">Conta</label>
                </div>
                <button class="btn waves-effect waves-light col offset-s5" type="submit">Entrar
                    <i class="mdi-content-send right"></i>
                </button>
            </form>			
        </div>
    </div>
<jsp:include page="/WEB-INF/footer.jsp"/>
