/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.TipoDeOperacaoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Operacao;

/**
 *
 * @author Daian
 */
@WebServlet(name = "ControladorOperacoesServlet", urlPatterns = {"/ControladorOperacoesServlet"})
public class ControladorOperacoesServlet extends HttpServlet {
@Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
              
        String servlet = request.getParameter("servlet");
        HttpSession sessao = request.getSession();
        sessao.setAttribute("action", request.getParameter("action"));
        
        try {            
            request.getRequestDispatcher(servlet).forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "A operação causou uma exceção", e);
        }
    }
}
