/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Daian
 */
public class Conexao {

    private Connection con = null;
    private final String url = "jdbc:derby://localhost:1527/bancoMVC";
    private final String usr = "app";
    private final String pwd = "app";

    public Conexao() {
        this.abrir();
    }

    private void abrir() {        
        try {
            con = DriverManager.getConnection(url, usr, pwd);
            System.out.println("[MVC] Conexão com BD estabelecida.");
        } catch (SQLException sqle) {
            System.out.println("[MVC] Erro: " + sqle.getMessage());
        }
    }

    public void fechar() {
        try {
            if (con != null) {
                con.close();
                System.out.println("[MVC] Conexão com BD encerrada.");
            }
        } catch (SQLException sqle) {
            System.out.println("[MVC] Erro: " + sqle.getMessage());
        }
    }

    public Connection getConexao() {
        return con;
    }

}
