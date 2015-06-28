
package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.Conexao;
import models.Usuario;

/**
 *
 * @author Daian
 */
public class UsuarioDAO {

    Conexao conn;

    public UsuarioDAO(Conexao c) {
        conn = c;
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
        } 
    }

    public Usuario getObject(String agencia, String conta) {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select id,nome,cpf,telefone,endereco from usuarios where id= "
                + "(select id_usuario from usuario_conta where num_agencia=? and num_conta=? "
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
        }
    }
    
    public List<String> getIdentificacao(String agencia, String conta) {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select nome,cpf from usuarios where id= "
                + "(select id_usuario from usuario_conta where num_agencia=? and num_conta=? "
                + "group by id_usuario)")) {
            sql.setString(1, agencia);
            sql.setString(2, conta);
            ResultSet r = sql.executeQuery();
            List<String> identificacao = new ArrayList();
            while (r.next()) {
                r.getString("nome");
                r.getString("cpf");
                return identificacao;
            }
            return null;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
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
