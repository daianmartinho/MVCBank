/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



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
        request.setAttribute("action", request.getParameter("action"));
        
        try {            
            request.getRequestDispatcher(servlet).forward(request, response);

        } catch (ServletException | IOException e) {
            throw new ServletException(
                    "A operação causou uma exceção", e);
        }
    }
}
