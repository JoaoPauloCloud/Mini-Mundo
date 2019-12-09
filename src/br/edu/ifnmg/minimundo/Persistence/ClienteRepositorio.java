/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Cliente;
import br.edu.ifnmg.minimundo.DomainModel.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Joao Paulo
 */
public class ClienteRepositorio extends BancoDados {

    public boolean Salvar(Cliente obj) {
        try {

            if (obj.getId() == 0) {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into clientes(nome, cpf, status, email, bairro, estado, rua, cidade, numero, complemento,  cep) values(?,?,?,?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getNome());
                sql.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
                sql.setInt(3, 1);
                sql.setString(4, obj.getEmail());
                sql.setString(5, obj.getBairro());
                sql.setString(6, obj.getEstado().name());
                sql.setString(7, obj.getRua());
                sql.setString(8, obj.getCidade());
                sql.setInt(9, obj.getNumero());
                sql.setString(10, obj.getComplemento());
                sql.setString(11, obj.getCep());

                if (sql.executeUpdate() > 0) {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));
                    SalvarTelefones(obj);
                    //System.out.print(obj.getTelefones());

                    return true;
                } else {
                    return false;
                }
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Clientes set nome = ?, cpf = ?, bairro = ?, estado = ?, rua = ?, cidade = ?, numero = ?, complemento = ?, email = ?, cep = ? where id = ?");

                sql.setString(1, obj.getNome());
                sql.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
                sql.setString(3, obj.getBairro());
                sql.setString(4, obj.getEstado().name());
                sql.setString(5, obj.getRua());
                sql.setString(6, obj.getCidade());
                sql.setInt(7, obj.getNumero());
                sql.setString(8, obj.getComplemento());
                sql.setString(9, obj.getEmail());
                sql.setString(10, obj.getCep());
                sql.setInt(11, obj.getId());

                if (sql.executeUpdate() > 0) {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));

                    //SalvarTelefones(obj);
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;

    }
    
    public void SalvarTelefones(Cliente cliente) throws SQLException{
        try {    
            //cliente.addTelefone("998352820");
            //cliente.addTelefone("998352829");
             
            
            String values = "";
            for(String telefone : cliente.getTelefones()){
                    
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into telefones(telefone,clientes_id) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
                           
                            
                           sql.setString(1, telefone);
                           sql.setInt(2, cliente.getId());
                           sql.executeUpdate();
                           
            }
            
            
            
            
            
            
            
            /*
            for(String telefone : cliente.getTelefones()){
                
             PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into telefones(telefone,clientes_id)values(?,?)");   
                sql.setString(1, telefone);
                sql.setInt(2, cliente.getId());
                
                sql.executeUpdate();
                
            }*/
            
            
            
        } catch (SQLException ex) {
            PreparedStatement sql2 = this.getConexao()
                    .prepareStatement("delete from clientes where id = ?");
            
            sql2.setInt(1, cliente.getId());
            sql2.executeUpdate();
            
           JOptionPane.showMessageDialog(null,"não foi possivel cadastra telefone");
            return;
        }
    }

    public List<Cliente> Buscar(Cliente filtro) {
        try {

            String where = "";

            if (filtro != null) {
                if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                    where += "nome like '%" + filtro.getNome() + "%'";
                }

                if (filtro.getCpf() != null && !filtro.getCpf().isEmpty()
                        && !"000.000.000-00".equals(filtro.getCpf())) {
                    if (where.length() > 0) {
                        where += " and ";
                    }
                    where += "cpf = '" + filtro.getCpf().replace(".", "").replace("-", "") + "'";
                }

                if ((filtro.getEstado().name() != "Todos") && (filtro.getEstado() != null)) {
                    if (where.length() > 0) {
                        where += " and ";
                    }
                    where += "estado = '" + filtro.getEstado().name() + "'";
                }
            }

            String consulta = "select * from clientes";

            if (where.length() > 0) {
                consulta += " where " + where;
            }

            PreparedStatement sql = this.getConexao()
                    .prepareStatement(consulta);

            ResultSet resultado = sql.executeQuery();

            List<Cliente> clientes = new ArrayList<>();

            while (resultado.next()) {

                Cliente cliente = new Cliente();

                try {
                    cliente.setId(resultado.getInt("id"));
                    cliente.setNome(resultado.getString("nome"));
                    cliente.setCpf(resultado.getString("cpf"));
                    cliente.setEmail(resultado.getString("email"));
                    //Endereço
                    cliente.setBairro(resultado.getString("bairro"));
                    cliente.setEstado(Estado.valueOf(resultado.getString("estado")));
                    cliente.setRua(resultado.getString("rua"));
                    cliente.setCidade(resultado.getString("cidade"));
                    cliente.setNumero(resultado.getInt("numero"));
                    cliente.setComplemento(resultado.getString("complemento"));
                    cliente.setCep(resultado.getString("cep"));

                    //abrirTelefones(cliente);
                } catch (Exception ex) {
                    cliente = null;
                }

                clientes.add(cliente);
            }
            return clientes;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
