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
import models.Usuario;

/**
 *
 * @author Daian
 */
public class UsuarioDAO {

    Conexao conn;

    public UsuarioDAO() {
        conn = new Conexao();
    }

    public String getSenha(Usuario usuario) {
        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select senha from usuarios where id=?")) {
            sql.setInt(1, usuario.getId());
            String senha = null;
            ResultSet r = sql.executeQuery();

            while (r.next()) {
                senha = r.getString("senha");
            }

            return senha;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            conn.fechar();
        }
    }

    public Usuario get(String agencia, String conta) {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select id,nome,cpf,telefone,endereco from usuarios where id= "
                + "(select id_usuario from contas where num_agencia=? and num_conta=? "
                + "group by id_usuario)")) {
            sql.setString(1, agencia);
            sql.setString(2, conta);
            ResultSet r = sql.executeQuery();

            //criando o objeto usuario 
            Usuario usuario = new Usuario();

            while (r.next()) {
                popular(usuario, r);
                return usuario;
            }
            return null;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            conn.fechar();
        }

    }

    private void popular(Usuario usuario, ResultSet r) throws SQLException {

        usuario.setId(r.getInt("id"));
        usuario.setNome(r.getString("nome"));
        usuario.setCPF(r.getString("cpf"));
        usuario.setTelefone(r.getString("telefone"));
        usuario.setEndereço(r.getString("endereco"));
        //usuario.setSenha(r.getString("senha"));
        //as contas só serão setadas se a senha tiver correta
        //usuario.setContas(new ContaDAO().getContasDoUsuario(usuario));

    }
}
