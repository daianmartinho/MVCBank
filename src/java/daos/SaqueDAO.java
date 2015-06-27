/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import jdbc.Conexao;
import models.Conta;
import models.Operacao;
import models.Usuario;

/**
 *
 * @author Daian
 */
public class SaqueDAO {

    Conexao conn;

    public SaqueDAO(Conexao c) {
        conn = c;

    }

    public double sacar(Conta conta, double valor) throws SQLException {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "update contas set saldo=? where num_agencia=? and num_conta=? and id_tipo_conta=?")) {
            //cria variavel pra guardar o valor do saldo temporário, 
            //é temporario pq se der algum ko na transação este valor não 
            //será atribuido ao objeto conta;            
            double tSaldo = conta.getSaldo() - valor;
            if(tSaldo<0){
                throw new SQLException();
            }
            //setando as variaveis do stmt
            sql.setDouble(1, tSaldo);
            sql.setString(2, conta.getAgencia().getNum_agencia());
            sql.setString(3, conta.getNum_conta());
            sql.setInt(4, conta.getTipo().getId());

            sql.executeUpdate();

            return tSaldo;

        }
    }
    
}
