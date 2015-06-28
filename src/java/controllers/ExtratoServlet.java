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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
 * @author igorsfa
 */
@WebServlet(name = "ExtratoServlet", urlPatterns = {"/ExtratoServlet"})
public class ExtratoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        controle(request, response);
    }
        protected void controle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession sessao = request.getSession();
        String action = !request.getParameter("action").equals(null) ?
                request.getParameter("action"):
                (String) sessao.getAttribute("action");
        switch (action) {
            case "view":
                view(request, response);
                break;
            case "index":
                index(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ExtratoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ExtratoServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        Operacao operacao = new Operacao();
        operacao.setTipo(new TipoDeOperacaoDAO(new Conexao()).get(2));//2 é o id de extrato no banco
        sessao.setAttribute("operacao", operacao);
        request.getRequestDispatcher("/WEB-INF/extrato/selecionar-conta.jsp").forward(request, response);
    }

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        ContaDAO contadao = new ContaDAO(new Conexao());        
        Usuario usuario = (Usuario)sessao.getAttribute("usuario");
        Conta conta = null;
        for(Conta c : usuario.getContas()){
            if(c.getTipo().getId() == Integer.parseInt(request.getParameter("tipo"))){
                conta = contadao.getObject(c.getAgencia().getNum_agencia(),
                        c.getNum_conta(), ""+c.getTipo().getId());
            }
        }
        if(conta!=null){
            OperacaoDAO operacaodao = new OperacaoDAO(new Conexao());
            conta.setOperacoes(operacaodao.getLista(conta));
            List<Operacao> operacoes = operacaodao.getLista(conta);
            request.setAttribute("operacoes", operacoes);
            request.getRequestDispatcher("/WEB-INF/extrato/view.jsp").forward(request, response);
        }
    }

}
