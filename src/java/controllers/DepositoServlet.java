/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ContaDAO;
import daos.DepositoDAO;
import daos.OperacaoDAO;
import daos.TipoDeOperacaoDAO;
import daos.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "DepositoServlet", urlPatterns = {"/DepositoServlet"})
public class DepositoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Operacao operacao = (Operacao) sessao.getAttribute("operacao");

        String sNumAgencia = request.getParameter("num_agencia");
        String sNumConta = request.getParameter("num_conta");
        String sTipoDeConta = request.getParameter("tipo_conta");
        String sValor = request.getParameter("valor");

        if (!sNumAgencia.isEmpty() && !sNumConta.isEmpty() && !sTipoDeConta.isEmpty() && !sValor.isEmpty()) {

            operacao.setConta(new ContaDAO().get(sNumAgencia, sNumConta, sTipoDeConta));
            operacao.setValor(Double.parseDouble(sValor));

            //continuo passando a operaçao adiante
            sessao.setAttribute("operacao", operacao);

            request.getRequestDispatcher("WEB-INF/deposito/view.jsp").forward(request, response);
        }

    }

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Operacao operacao = new Operacao();
        operacao.setTipo(new TipoDeOperacaoDAO().get(3));//3 é o id de depósito no banco
        sessao.setAttribute("operacao", operacao);
        request.getRequestDispatcher("WEB-INF/deposito/index.jsp").forward(request, response);

    }
    private void create(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Operacao operacao = (Operacao) sessao.getAttribute("operacao");
        Conexao conn = new Conexao();
        
        //pede ao DepositoDAO pra depositar nessa conta e   
        try {
            //INICIO DA TRANSAÇÃO
            conn.getConexao().setAutoCommit(false);   
            
            //realizando o deposito
            double novoSaldo = new DepositoDAO(conn).depositarNaConta(operacao.getConta(), operacao.getValor());
            
            //seta timestamp da operação           
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());
            operacao.setData(timestamp);        
            
            //insere a operação na base
            new OperacaoDAO(conn).insert(operacao);
            
            conn.getConexao().commit();    
            //FIM DA TRANSAÇÃO
            
            //operação comitada, posso atualizar o saldo do objeto
            operacao.getConta().setSaldo(novoSaldo);
            request.setAttribute("msg", "deposito realizado com sucesso");
            request.getRequestDispatcher("WEB-INF/deposito/success.jsp").forward(request, response);

        } catch (SQLException ex) {
            try {
                conn.getConexao().rollback();
                
            } catch (SQLException ex1) {
                Logger.getLogger(DepositoServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                conn.getConexao().setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DepositoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn.fechar();

        }
        
    }
    protected void controle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        
        String action = (String) sessao.getAttribute("action");
       
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
        controle(request, response);
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
        controle(request, response);
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
