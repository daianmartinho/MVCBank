    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ContaDAO;
import daos.OperacaoDAO;
import daos.TipoDeOperacaoDAO;
import daos.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Conexao;
import models.Operacao;
import models.Usuario;

/**
 *
 * @author Daian
 */
@WebServlet(name = "TransferenciaServlet", urlPatterns = {"/TransferenciaServlet"})
public class TransferenciaServlet extends HttpServlet {

    Conexao conn;

    private void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        Operacao operacaoFrom = (Operacao) sessao.getAttribute("operacaoFrom");
        Operacao operacaoTo = (Operacao) sessao.getAttribute("operacaoTo");

        String sNumAgencia = request.getParameter("num_agencia");
        String sNumConta = request.getParameter("num_conta");
        String sTipoDeContaFrom = request.getParameter("id_tipo_conta_from");
        String sTipoDeContaTo = request.getParameter("id_tipo_conta_to");
        String sValor = request.getParameter("valor");
        System.out.println("tipo from" + sTipoDeContaFrom);
        System.out.println("tipo to" + sTipoDeContaTo);
        if (!sNumAgencia.isEmpty() && !sNumConta.isEmpty() && !sTipoDeContaFrom.isEmpty() && !sTipoDeContaTo.isEmpty() && !sValor.isEmpty()) {

            operacaoFrom.setConta(usuario.getConta(Integer.parseInt(sTipoDeContaFrom)));
            operacaoFrom.setValor(-Double.parseDouble(sValor));
            operacaoTo.setConta(new ContaDAO(conn).getObject(sNumAgencia, sNumConta, sTipoDeContaTo));
            operacaoTo.setValor(Double.parseDouble(sValor));
            String nomeDestinatario = new UsuarioDAO(conn).getNome(sNumAgencia, sNumConta);
            //continuo passando a operaçao adiante
            sessao.setAttribute("operacaoFrom", operacaoFrom);
            sessao.setAttribute("operacaoTo", operacaoTo);
            request.setAttribute("nomeDestinatario", nomeDestinatario);

            request.getRequestDispatcher("WEB-INF/transferencia/create.jsp").forward(request, response);
        }

    }

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Operacao operacaoFrom = new Operacao();
        Operacao operacaoTo = new Operacao();
        operacaoFrom.setTipo(new TipoDeOperacaoDAO(conn).get(5));//5 é o id de transferencia no banco
        operacaoTo.setTipo(new TipoDeOperacaoDAO(conn).get(5));//5 é o id de transferencia no banco

        sessao.setAttribute("operacaoFrom", operacaoFrom);
        sessao.setAttribute("operacaoTo", operacaoTo);
        request.getRequestDispatcher("WEB-INF/transferencia/index.jsp").forward(request, response);

    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();

        Operacao operacaoFrom = (Operacao) sessao.getAttribute("operacaoFrom");
        Operacao operacaoTo = (Operacao) sessao.getAttribute("operacaoTo");
        Usuario usuario = (Usuario) sessao.getAttribute("usuario");

        try {
            //INICIO DA TRANSAÇÃO
            conn.getConexao().setAutoCommit(false);
            //realizando o débito de operacaoFrom
            double novoSaldoFrom = new OperacaoDAO(conn).doDebito(operacaoFrom.getConta(), operacaoFrom.getValor());
            if (novoSaldoFrom < 0) {
                request.setAttribute("msg", "Saldo insuficiente");
                request.getRequestDispatcher("WEB-INF/transferencia/index.jsp").forward(request, response);
            } else {
                //realizando o crédito em operacaoTo
                double novoSaldoTo = new OperacaoDAO(conn).doCredito(operacaoTo.getConta(), operacaoTo.getValor());

                //seta timestamp da operação           
                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
                operacaoFrom.setData(timestamp);
                operacaoTo.setData(timestamp);
                //insere as operações na base
                new OperacaoDAO(conn).doInsert(usuario, operacaoFrom);
                new OperacaoDAO(conn).doInsert(new UsuarioDAO(conn).getObject(operacaoTo.getConta().getAgencia().getNum_agencia(), operacaoTo.getConta().getNum_conta()), operacaoTo);

                conn.getConexao().commit();
                //FIM DA TRANSAÇÃO

                //operação comitada, posso atualizar o saldo do objeto
                operacaoFrom.getConta().setSaldo(novoSaldoFrom);
                operacaoTo.getConta().setSaldo(novoSaldoTo);
                request.setAttribute("msg", "Transferencia realizada com sucesso");
                request.getRequestDispatcher("WEB-INF/common/message.jsp").forward(request, response);
                sessao.setAttribute("operacao", null);
            }
        } finally {
            conn.getConexao().setAutoCommit(true);

        }

    }

    protected void controle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //tenta pegar action vindo de um jsp
        String action = request.getParameter("action");
        //se não encontrar, é pq o servlet foi chamado pelo controlador, neste caso busca nos atributos do request
        if (action == null) {
            action = (String) request.getAttribute("action");
        }
        switch (action) {
            case "view":
                view(request, response);
                break;
            case "index":
                index(request, response);
                break;
            case "create":
                create(request, response);
                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        conn = new Conexao();
        try {
            controle(request, response);
        } catch (SQLException ex) {
            System.out.println("caiu 2");
            try {
                conn.getConexao().rollback();
            } catch (SQLException ex1) {
                System.out.println("caiu 3");
                Logger.getLogger(TransferenciaServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            conn.fechar();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        conn = new Conexao();
        try {
            controle(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(TransferenciaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.fechar();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
