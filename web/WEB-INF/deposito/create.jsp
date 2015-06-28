<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="../header.jsp" />
<div class="container">
    <form action="DepositoServlet" method="post">
        <div class="row">
            <div class="col s12">
                <div class="card-panel">
                    <h3 class="center">
                        Agencia:${operacao.conta.agencia.num_agencia}<br/>
                        ${operacao.conta.tipo.descricao}:${operacao.conta.num_conta}<br/>
                        
                        Valor:${operacao.valor}
                        <input type="hidden" name="action" value="create"/>
                    </h3>
                </div>
            </div>
        </div>
        <div class="row">
            <button class="btn waves-effect waves-light col offset-s5" type="submit">Confirmar
                <i class="mdi-content-send right"></i>
            </button>
        </div>
    </form>
</div>
<jsp:include page="../footer.jsp" />