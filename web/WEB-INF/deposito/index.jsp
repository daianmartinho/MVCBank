<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="../header.jsp" />
<div class="container">
    <div class="row">
        <h3>Depósito</h3>
        <form action="DepositoServlet" method="post">
            <input type="hidden" name="action" value="view"/>
            <div class="input-field col s12">
                <input class="validate" type="text" name="num_agencia" id="agencia"/>
                <label for="agencia">Agencia</label>
            </div>
            <div class="input-field col s12">
                <input class="validate" type="text" name="num_conta" id="conta"/>
                <label for="conta">Conta</label>
            </div>
            <input type="radio" value="1" name="tipo_conta" id="1" CHECKED />
            <label for="1">Conta corrente</label>
            <input type="radio" value="2" name="tipo_conta" id="2"/>
            <label for="2">Conta poupança</label>
            <div class="input-field col s12">
                <input class="validate" type="text" name="valor" id="valor"/>
                <label for="valor">Valor</label>
            </div>
            
            <button class="btn waves-effect waves-light col offset-s5" type="submit">Entrar
                <i class="mdi-content-send right"></i>
            </button>
        </form>
    </div>
</div>
<jsp:include page="../footer.jsp" />
