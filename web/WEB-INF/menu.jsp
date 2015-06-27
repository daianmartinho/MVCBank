<%-- 
    Document   : menu
    Created on : 24/06/2015, 20:04:07
    Author     : igorsfa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp" />
<div class="container">
    <div class="row">
        <div class="col s12">
            <div class="card-panel">
                <h3 class="center">${msg}</h3>
            </div>
        </div>
        <div class="row">
            <a class="col s12 m6 waves-effect waves-light btn" 
               href="ControladorOperacoesServlet?servlet=SaldoServlet&action=index">Saldo</a>
            <a class="col s12 m6 waves-effect waves-light btn"
               href="ControladorOperacoesServlet?servlet=ExtratoServlet&action=index">Extrato</a>
            <a class="col s12 m6 waves-effect waves-light btn" 
               href="ControladorOperacoesServlet?servlet=DepositoServlet&action=index">Deposito</a>
            <a class="col s12 m6 waves-effect waves-light btn"
           href="ControladorOperacoesServlet?servlet=SaqueServlet&action=index">Saque</a>
            <a class="col s12 m6 waves-effect waves-light btn">Transferencia</a>
            <a class="col s12 m6 waves-effect waves-light btn">Investimento</a>
        </div>
</div>
<jsp:include page="footer.jsp" />