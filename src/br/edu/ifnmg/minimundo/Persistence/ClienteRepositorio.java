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

                if(sql.executeUpdate() > 0){ 
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));
                    SalvarTelefones(obj);
                    
                    
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
    
    public void SalvarTelefones(Cliente tef){
        try {
                       
            String values = "";
            for(String telefone : tef.getTelefones()){ 
                if(values.length() > 0) 
                    values += ", ";
                
                values += "("+tef.getId()+",'"+telefone+"')"; }
            
            for(String telefone : tef.getTelefones()){
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into telefones(clientes_id,telefone) values(?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                             sql.setInt(1, tef.getId());
                             sql.setInt(2, Integer.parseInt(telefone.trim()));
                             int i = Integer.parseInt(telefone.trim());
                             System.out.printf("telefone :",i);
                
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
            
                if((filtro.getEstado().name() != "Todos") && (filtro.getEstado() != null) ){
                    if(where.length() > 0)
                        where += " and ";
                    where += "estado = '"+filtro.getEstado().name() +"'";
                }
            }
            
            String consulta = "select * from clientes";
            
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
                    cliente.setEstado( Estado.valueOf(resultado.getString("Estado")));
                    //abrirTelefones(cliente);
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
