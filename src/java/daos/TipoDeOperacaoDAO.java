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
import models.TipoDeOperacao;

/**
 *
 * @author Daian
 */
public class TipoDeOperacaoDAO {
    Conexao conn;
    
    public TipoDeOperacaoDAO(){
        conn = new Conexao();
    }
    
    public TipoDeOperacao get(int id){
        try (PreparedStatement sql = conn.getConexao().prepareStatement("select * from tipos_operacao where id=?")) {
            sql.setInt(1, id);
            ResultSet r = sql.executeQuery();
            TipoDeOperacao tipo = new TipoDeOperacao();
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
    private void popular(TipoDeOperacao tipo, ResultSet r) throws SQLException {

        tipo.setId(r.getInt("id"));
        tipo.setDescricao(r.getString("descricao"));

    }
}
