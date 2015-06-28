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
import models.Operacao;

/**
 *
 * @author Daian
 */
public class OperacaoDAO {

    Conexao conn;

    public OperacaoDAO(Conexao c) {
        conn = c;
    }

    public List<Operacao> getLista(Conta conta) throws SQLException {
        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select * from log_operacoes where num_agencia=? and num_conta=? and id_tipo_conta=?")) {

            //setando as variaveis do stmt
            sql.setString(1, conta.getAgencia().getNum_agencia());
            sql.setString(2, conta.getNum_conta());
            sql.setInt(3, conta.getTipo().getId());

            ResultSet r = sql.executeQuery();
            List<Operacao> lista = new ArrayList();
            Operacao operacao;
            while (r.next()) {
                operacao = new Operacao();
                popular(operacao, r);
                lista.add(operacao);
            }
            return lista;

        }
    }

    public void doInsert(Operacao operacao) throws SQLException {
        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "insert into log_operacoes (id_tipo_operacao,num_agencia,num_conta,id_tipo_conta,id_usuario,valor,data) values (?,?,?,?,?,?,?)")) {

            //setando as variaveis do stmt
            sql.setInt(1, operacao.getTipo().getId());
            sql.setString(2, operacao.getConta().getAgencia().getNum_agencia());
            sql.setString(3, operacao.getConta().getNum_conta());
            sql.setInt(4, operacao.getConta().getTipo().getId());
            sql.setInt(5, operacao.getConta().getUsuario().getId());
            sql.setDouble(6, operacao.getValor());
            sql.setTimestamp(7, operacao.getData());

            sql.executeUpdate();

        }
    }

    public double doSaque(Conta conta, double valor) throws SQLException {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "update contas set saldo=? where num_agencia=? and num_conta=? and id_tipo_conta=?")) {
            //cria variavel pra guardar o valor do saldo temporário, 
            //é temporario pq se der algum ko na transação este valor não 
            //será atribuido ao objeto conta;            
            double tSaldo = conta.getSaldo() - valor;
            if (tSaldo < 0) {
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

    public double getSaldo(Conta conta) throws SQLException {

        try (PreparedStatement sql = conn.getConexao().prepareStatement(
                "select saldo from contas where num_agencia=? and num_conta=? and id_tipo_conta=?")) {

            //setando as variaveis do stmt
            sql.setString(1, conta.getAgencia().getNum_agencia());
            sql.setString(2, conta.getNum_conta());
            sql.setInt(3, conta.getTipo().getId());

            ResultSet r = sql.executeQuery();
            while (r.next()) {
                return r.getDouble("saldo");
            }
        }
        throw new SQLException();
    }

    private void popular(Operacao operacao, ResultSet r) throws SQLException {
        operacao.setId(r.getInt("id"));
        operacao.setTipo(new TipoDeOperacaoDAO().get(r.getInt("id_tipo_operacao")));
        operacao.setValor(r.getDouble("valor"));
        operacao.setData(r.getTimestamp("data"));
    }
}
