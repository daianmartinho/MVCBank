/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.Conexao;
import models.Agencia;

/**
 *
 * @author Daian
 */
public class AgenciaDAO {

    Conexao conn;

    public AgenciaDAO() {
        conn = new Conexao();
    }

    public Agencia get(String num_agencia) {
        try (PreparedStatement sql = conn.getConexao().prepareStatement("select * from agencias where num_agencia = ?")) {
            sql.setString(1, num_agencia);
            ResultSet r = sql.executeQuery();
            Agencia agencia = new Agencia();
            while (r.next()) {
                popular(agencia, r);
            }
            return agencia;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            conn.fechar();
        }
    }

    private void popular(Agencia agencia, ResultSet r) throws SQLException {

        agencia.setNum_agencia(r.getString("num_agencia"));
        agencia.setEndereço(r.getString("endereco"));

    }
}
