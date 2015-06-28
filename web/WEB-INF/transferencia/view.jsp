<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="../header.jsp" />
<div class="container">
    <form action="TransferenciaServlet" method="post">
        <div class="row">
            <div class="col s12">
                <div class="card-panel">
                    <h3 class="center">
                        Agencia:${operacaoTo.conta.agencia.num_agencia}<br/>
                        ${operacaoTo.conta.tipo.descricao}:${operacaoTo.conta.num_conta}<br/>
                        Nome:${nomeDestinatario}<br/><br/>

                        Valor:${operacaoTo.valor}
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