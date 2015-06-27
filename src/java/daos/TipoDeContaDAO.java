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
import models.TipoDeConta;

/**
 *
 * @author Daian
 */
public class TipoDeContaDAO {

    Conexao conn;

    public TipoDeContaDAO() {
        conn = new Conexao();
    }

    public TipoDeConta get(int id) {
        try (PreparedStatement sql = conn.getConexao().prepareStatement("select * from tipos_conta where id=?")) {
            sql.setInt(1, id);
            ResultSet r = sql.executeQuery();
            TipoDeConta tipo = new TipoDeConta();
            while (r.next()) {
                popular(tipo, r);
            }
            return tipo;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            conn.fechar();
        }
    }

    private void popular(TipoDeConta tipo, ResultSet r) throws SQLException {

        tipo.setId(r.getInt("id"));
        tipo.setDescricao(r.getString("descricao"));

    }
}
