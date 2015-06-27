/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ContaDAO;
import daos.TipoDeOperacaoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Conta;
import models.Operacao;
import models.Usuario;

/**
 *
 * @author igorsfa
 */
@WebServlet(name = "SaldoServlet", urlPatterns = {"/SaldoServlet"})
public class SaldoServlet extends HttpServlet {

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //listar-contas é comum a algumas servlets, por isso preciso mandar alguns parametros pros botões
        //neste caso, quero que o botão tenha um link pra SaldoServlet com action=view
        request.setAttribute("nomeServlet", "SaldoServlet");
        request.setAttribute("action", "view");
        
        request.getRequestDispatcher("WEB-INF/common/listar-contas.jsp").forward(request, response);
    }

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();        
        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        new Saldo
        
        if (conta != null) {
            request.setAttribute("saldo", conta.getSaldo());
            request.getRequestDispatcher("/WEB-INF/saldo/view.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/saldo/view.jsp").forward(request, response);
        }
    }

    protected void controle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
