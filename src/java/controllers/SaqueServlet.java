/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ContaDAO;
import daos.OperacaoDAO;
import daos.TipoDeOperacaoDAO;
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
import models.Conta;
import models.Operacao;
import models.Usuario;

/**
 *
 * @author Daian
 */
@WebServlet(name = "SaqueServlet", urlPatterns = {"/SaqueServlet"})
public class SaqueServlet extends HttpServlet {

    Conexao conn;

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        request.setAttribute("nomeServlet", "SaqueServlet");
        request.setAttribute("action", "create");
        
        request.getRequestDispatcher("WEB-INF/common/listar-contas.jsp").forward(request, response);

    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Operacao operacao = new Operacao();        
        operacao.setTipo(new TipoDeOperacaoDAO(conn).get(4));//4 é o id de saque no banco
        int id_tipo_conta = Integer.parseInt(request.getParameter("tipo"));
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario)sessao.getAttribute("usuario");
        operacao.setConta(usuario.getConta(id_tipo_conta));
        
        sessao.setAttribute("operacao", operacao);
       
        request.getRequestDispatcher("WEB-INF/saque/create.jsp").forward(request, response);
    }

    private void confirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario)sessao.getAttribute("usuario");
        Operacao operacao = (Operacao) sessao.getAttribute("operacao");        
        operacao.setValor(Double.parseDouble(request.getParameter("valor")));

        
        try {
            //INICIO DA TRANSAÇÃO
            conn.getConexao().setAutoCommit(false);

            //realizando o saque
            Conta conta = new ContaDAO(conn).getObject(operacao.getConta().getAgencia().getNum_agencia(),
                    operacao.getConta().getNum_conta(), ""+operacao.getConta().getTipo().getId());
            double novoSaldo = new OperacaoDAO(conn).doSaque(conta, operacao.getValor());

            //seta timestamp da operação           
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
            operacao.setData(timestamp);

            //insere a operação na base
            new OperacaoDAO(conn).doInsert(usuario,operacao);

            conn.getConexao().commit();
            //FIM DA TRANSAÇÃO

            //operação comitada, posso atualizar o objeto
            operacao.getConta().setSaldo(novoSaldo);
            operacao.getConta().addOperacao(operacao);
            
            request.setAttribute("msg", "O saque foi realizado com sucesso! Seu novo Saldo é R$" + novoSaldo);
            request.getRequestDispatcher("WEB-INF/common/message.jsp").forward(request, response);
        } catch(SQLException e){
            conn.getConexao().rollback();
            request.setAttribute("msg", "Erro no saque.");
            request.getRequestDispatcher("WEB-INF/common/message.jsp").forward(request, response);
        }

        

    }

    protected void controle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        
        
        //tenta pegar action vindo de um jsp
        String action = request.getParameter("action");
        //se não encontrar, é pq o servlet foi chamado pelo controlador, neste caso busca nos atributos do request
        if (action == null) {
            action = (String) request.getAttribute("action");
        }
        System.out.println("action = "+action);
        switch (action) {
            case "index":
                index(request, response);
                break;
            case "create":
                create(request, response);
                break;
            case "confirm":
                confirm(request, response);
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
        //request.setAttribute("tipo", request.getParameter("tipo"));
        //request.setAttribute("action", request.getParameter("action"));
        conn = new Conexao();
        try {
            controle(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SaqueServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SaqueServlet.class.getName()).log(Level.SEVERE, null, ex);
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
