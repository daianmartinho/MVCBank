/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ContaDAO;
import daos.UsuarioDAO;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;

/**
 *
 * @author Daian
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/login/index.jsp").forward(request, response);
    }

    private void userCheck(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sAgencia = request.getParameter("agencia");
        String sConta = request.getParameter("conta");

        //pede o objeto do usuario que possui agencia e conta informada 
        Usuario usuario = new UsuarioDAO().get(sAgencia, sConta);
        if (usuario != null) {

            //poe o objeto usuario na sessao
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuario", usuario);

            //redireciona para pagina de autenticação
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/autenticacao.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("erro", "Usuário não encontrado");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/erro.jsp");
            rd.forward(request, response);
        }

    }

    private void passCheck(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String senha = request.getParameter("senha");
        HttpSession sessao = request.getSession();
        Object oUsuario = sessao.getAttribute("usuario");
        if (oUsuario != null && senha != null) {
            Usuario usuario = (Usuario) oUsuario;

            if (usuario.getSenha().equals(senha)) {
                //seta a lista de contas desse usuario com a lista obtida no bd pelo ContaDAO
                usuario.setContas(new ContaDAO().getContasDoUsuario(usuario));
                //redireciona pro menu
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/menu.jsp");
                rd.forward(request, response);

            } else {
                request.setAttribute("erro", "Senha inválida");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/erro.jsp");
                rd.forward(request, response);

            }

        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/novoLogin.jsp");
            rd.forward(request, response);

        }

    }

    protected void controle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();

        String action = (String) sessao.getAttribute("action");

        switch (action) {
            case "index":
                index(request, response);
                break;
            case "userCheck":
                userCheck(request, response);
                break;
            case "passCheck":
                passCheck(request, response);
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
