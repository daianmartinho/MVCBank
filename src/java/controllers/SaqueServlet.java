/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.OperacaoDAO;
import daos.SaqueDAO;
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
import models.Operacao;
import models.Usuario;

/**
 *
 * @author Daian
 */
@WebServlet(name = "SaqueServlet", urlPatterns = {"/SaqueServlet"})
public class SaqueServlet extends HttpServlet {

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Operacao operacao = new Operacao();
        operacao.setTipo(new TipoDeOperacaoDAO().get(4));//4 é o id de saque no banco
        sessao.setAttribute("operacao", operacao);
        request.getRequestDispatcher("WEB-INF/saque/index.jsp").forward(request, response);
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Operacao operacao = new Operacao();
        operacao.setTipo(new TipoDeOperacaoDAO().get(4));//4 é o id de saque no banco
        String tipo = request.getParameter("tipo");
        request.setAttribute("tipo", tipo);
        sessao.setAttribute("operacao", operacao);
        request.getRequestDispatcher("WEB-INF/saque/create.jsp").forward(request, response);
    }

    private void confirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Operacao operacao = (Operacao) sessao.getAttribute("operacao");
        Usuario usuario = (Usuario)sessao.getAttribute("usuario");
        
        operacao.setConta(usuario.getConta((int)request.getAttribute("tipo")));
        System.out.println("saldo da conta= "+operacao.getConta().getSaldo());
        Conexao conn = new Conexao();

        //pede ao SaqueDAO() pra sacar dessa conta  
        try {
            //INICIO DA TRANSAÇÃO
            conn.getConexao().setAutoCommit(false);

            //realizando o deposito
            double novoSaldo = new SaqueDAO(conn).sacar(operacao.getConta(), operacao.getValor());

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

            request.setAttribute("msg", "O saque foi realizado com sucesso! Seu novo Saldo é" + novoSaldo);
            request.getRequestDispatcher("WEB-INF/saque/success.jsp").forward(request, response);
        } catch (SQLException ex) {
            try {
                conn.getConexao().rollback();
                request.setAttribute("msg", "saldo insuficiente");
                request.getRequestDispatcher("WEB-INF/saque/erro.jsp").include(request, response);
            } catch (SQLException ex1) {
                Logger.getLogger(SaqueServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                conn.getConexao().setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SaqueServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn.fechar();

        }

    }

    protected void controle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        String action = !request.getParameter("action").equals(null) ?
                request.getParameter("action"):
                (String) sessao.getAttribute("action");
      

        switch (action) {
            case "index":
                index(request, response);
                break;
            case "create":
                create(request, response);
                break;
            case "confirm":
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
        //request.setAttribute("tipo", request.getParameter("tipo"));
        //request.setAttribute("action", request.getParameter("action"));

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
