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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joao Paulo
 */
public class ClienteRepositorio extends BancoDados {
    
    public boolean Salvar(Cliente obj){
        try {
            
            if(obj.getId() == 0){
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into Clientes(nome, cpf, bairro, estado, rua, cidade, numero, complemento, email, cep) values(?,?,?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);

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

                if(sql.executeUpdate() > 0){ 
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));
                    
                    atualizarTelefones(obj);
                    
                    return true;
                }
                else
                    return false;
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

                if(sql.executeUpdate() > 0) {
                    atualizarTelefones(obj);
                    return true;
                }
                else
                    return false;
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return false;
        
    }
    
    public void atualizarTelefones(Cliente cliente){
        try {
            PreparedStatement sql = this.getConexao()
                    .prepareStatement("delete from Telefones where cliente_id = ?");
            
            sql.setInt(1, cliente.getId());
            
            String values = "";
            for(String telefone : cliente.getTelefones()){
                if(values.length() > 0) 
                    values += ", ";
                
                values += "("+cliente.getId()+",'"+telefone+"')";
            }
            
            Statement sql2 = this.getConexao().createStatement();
            
            sql2.executeUpdate("insert into Telefones(cliente_id, telefone) VALUES " + values);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Cliente Abrir(int id){
        try {
            
             PreparedStatement sql = this.getConexao()
                     .prepareStatement("select * from Clientes where id = ?");
             
             sql.setInt(1, id);
             
             ResultSet resultado = sql.executeQuery();
             
             
             resultado.next();
             
             Cliente cliente = new Cliente();
             
             try {
                cliente.setId( resultado.getInt("id"));
                cliente.setNome( resultado.getString("nome"));
                cliente.setCpf( resultado.getString("cpf"));
                cliente.setBairro( resultado.getString("bairro"));
                cliente.setEstado( Estado.valueOf(resultado.getString("estado")));
                cliente.setRua( resultado.getString("rua"));
                cliente.setCidade( resultado.getString("cidade"));
                cliente.setNumero( resultado.getInt("numero"));
                cliente.setComplemento( resultado.getString("complemento"));
                cliente.setEmail( resultado.getString("email"));
                cliente.setCep( resultado.getString("cep"));
                abrirTelefones(cliente);
             } catch(Exception ex){
                 cliente = null;
             }
             
             return cliente;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public void abrirTelefones(Cliente cliente){
        try {
            PreparedStatement sql = this.getConexao()
                    .prepareStatement("select telefone from Telefones where cliente_id = ?");
            
            sql.setInt(1, cliente.getId());
            
            ResultSet resultado = sql.executeQuery();
            
            while(resultado.next()){
                cliente.addTelefone(resultado.getString("telefone"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Cliente> Buscar(Cliente filtro){
        try {
            
            String where = "";
            
            if(filtro != null){
                if(filtro.getNome() != null && !filtro.getNome().isEmpty())
                    where += "nome like '%"+filtro.getNome() + "%'";

                if(filtro.getCpf() != null && !filtro.getCpf().isEmpty() && 
                        !"000.000.000-00".equals(filtro.getCpf())){
                    if(where.length() > 0)
                        where += " and ";
                    where += "cpf = '"+filtro.getCpf().replace(".", "").replace("-", "") + "'";
                }
            
                if(filtro.getEstado() != null ){
                    if(where.length() > 0)
                        where += " and ";
                    where += "estado = '"+filtro.getEstado().name() +"'";
                }
            }
            
            String consulta = "select * from Clientes";
            
            if(where.length() >0 )
                consulta += " where " + where;
            
             PreparedStatement sql = this.getConexao()
                     .prepareStatement(consulta);
             
             ResultSet resultado = sql.executeQuery();
             
             List<Cliente> clientes = new ArrayList<>();
             
             while(resultado.next()) {
             
                Cliente cliente = new Cliente();
                
                try {
                cliente.setId( resultado.getInt("id"));
                cliente.setNome( resultado.getString("nome"));
                cliente.setCpf( resultado.getString("cpf"));
                cliente.setBairro( resultado.getString("bairro"));
                cliente.setEstado( Estado.valueOf(resultado.getString("estado")));
                cliente.setRua( resultado.getString("rua"));
                cliente.setCidade( resultado.getString("cidade"));
                cliente.setNumero( resultado.getInt("numero"));
                cliente.setComplemento( resultado.getString("complemento"));
                cliente.setEmail( resultado.getString("email"));
                cliente.setCep( resultado.getString("cep"));
                abrirTelefones(cliente);
                } catch(Exception ex){
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
