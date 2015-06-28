/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.OperacaoDAO;
import java.io.IOException;
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
import models.Operacao;
import models.Usuario;

/**
 *
 * @author igorsfa
 */
@WebServlet(name = "ExtratoServlet", urlPatterns = {"/ExtratoServlet"})
public class ExtratoServlet extends HttpServlet {

    Conexao conn;

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("action", "view");
        request.setAttribute("nomeServlet", "ExtratoServlet");
        request.getRequestDispatcher("/WEB-INF/common/listar-contas.jsp").forward(request, response);
    }

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sessao = request.getSession();
        String sId_tipo_conta = request.getParameter("tipo");
        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        List<Operacao> extrato = new OperacaoDAO(conn).getExtrato(usuario.getConta(Integer.parseInt(sId_tipo_conta)));
        request.setAttribute("extrato", extrato);

        request.getRequestDispatcher("/WEB-INF/extrato/view.jsp").forward(request, response);

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
            Logger.getLogger(ExtratoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ExtratoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
