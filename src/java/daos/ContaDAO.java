/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.Conexao;
import models.Conta;
import models.Usuario;

/**
 *
 * @author Daian
 */
public class ContaDAO {

    Conexao conn;

    public ContaDAO(Conexao c) {
        conn = c;
    }

    public Conta getObject(String sNumAgencia, String sNumConta, String sIdTipoDeConta) {
        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select * from contas where num_agencia=? and num_conta=? and id_tipo_conta=?")) {
            sql.setString(1, sNumAgencia);
            sql.setString(2, sNumConta);
            sql.setString(3, sIdTipoDeConta);
            ResultSet r = sql.executeQuery();
            Conta conta = new Conta();
            while (r.next()) {
                popular(conta, r);                
            }
            return conta;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } 
    }

    //metodo retorna a lista de contas que um usuario possui
    public List<Conta> getContasDoUsuario(Usuario usuario) {
        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select num_agencia, num_conta, id_tipo_conta from usuario_conta where id_usuario=?")) {
           
            sql.setInt(1, usuario.getId());

            ResultSet r = sql.executeQuery();

            //criando a lista de contas que será retornada
            List<Conta> contas = new ArrayList();
            while (r.next()) {
                Conta conta = new Conta();
                //metodo pra popular a conta
                popular(conta, r);
                contas.add(conta);
            }

            return contas;

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } 

    }

    private void popular(Conta conta, ResultSet r) throws SQLException {
        //populando a conta com os dados obtidos no resultset
        //note que para setar agencia e tipo, tivemos q chamar outros DAO,
        //para criar o objeto agencia e tipo respectivamente
        String numAgencia = r.getString("num_agencia");
        String numConta = r.getString("num_conta");

        conta.setAgencia(new AgenciaDAO().get(numAgencia));
        conta.setNum_conta(numConta);
        conta.setTipo(new TipoDeContaDAO().get(r.getInt("id_tipo_conta")));
        conta.setSaldo(new OperacaoDAO(conn).getSaldo(conta));
        
    }
}
