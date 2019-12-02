/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Usuario;
import java.sql.Connection;
//import br.edu.ifnmg.minimundo.Persistence.BancoDados;
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
public class UsuarioRepositorio extends BancoDados {
    
    public boolean Salvar(Usuario obj){
        try {
            
            if(obj.getId() == 0){
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into Usuarios(nome, cpf, usuario, senha, telefone) values(?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getNome());
                sql.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
                sql.setString(3, obj.getUsuario());                
                sql.setString(4, obj.getSenha());
                sql.setString(5, obj.getTelefone());

               
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Clientes set nome = ?, cpf = ?, usuario = ?, senha = ?, tlefone = ? where id = ?");

                sql.setString(1, obj.getNome());
                sql.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
                sql.setString(3, obj.getUsuario());                
                sql.setString(4, obj.getSenha());
                sql.setString(5, obj.getTelefone());
                sql.setInt(6, obj.getId());

                
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return false;
        
    }
    
    public Usuario Abrir(int id){
        try {
            
             PreparedStatement sql = this.getConexao()
                     .prepareStatement("select * from Usuarios where id = ?");
             
             sql.setInt(1, id);
             
             ResultSet resultado = sql.executeQuery();
             
             
             resultado.next();
             
             Usuario usuario = new Usuario();
             
             try {
                usuario.setId( resultado.getInt("id"));
                usuario.setNome( resultado.getString("nome"));
                usuario.setCpf( resultado.getString("cpf"));
                usuario.setUsuario( resultado.getString("usuario"));
                usuario.setSenha( resultado.getString("senha"));
                usuario.setTelefone( resultado.getString("telefone"));
                
             } catch(Exception ex){
                 usuario = null;
             }
             
             return usuario;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public List<Usuario> Buscar(Usuario filtro){
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
            
               
            }
            
            String consulta = "select * from Usuarios";
            
            if(where.length() >0 )
                consulta += " where " + where;
            
             PreparedStatement sql = this.getConexao()
                     .prepareStatement(consulta);
             
             ResultSet resultado = sql.executeQuery();
             
             List<Usuario> usuarios = new ArrayList<>();
             
             while(resultado.next()) {
             
                Usuario usuario = new Usuario();
                
                try {
                usuario.setId( resultado.getInt("id"));
                usuario.setNome( resultado.getString("nome"));
                usuario.setCpf( resultado.getString("cpf"));
                usuario.setUsuario( resultado.getString("usuario"));
                usuario.setSenha( resultado.getString("senha"));
                usuario.setTelefone( resultado.getString("telefone"));
                
                } catch(Exception ex){
                    usuario = null;
                }
                
                usuarios.add(usuario);
             }
             return usuarios;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
        public boolean checkLogin(Usuario filtro){
        
        
        boolean check = false;
        
        
        try{
            
            String where = "";
            //verificando se os campos não estão vazius 
            if((filtro.getUsuario() == null && filtro.getUsuario().isEmpty())||(filtro.getSenha() == null && filtro.getSenha().isEmpty())){         
                
                return check;  
             
            }
            
            PreparedStatement sql = this.getConexao()
                        .prepareStatement("SELECT * FROM usuarios where usuario = ? and senha = ?",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, filtro.getUsuario());
                sql.setString(2, filtro.getSenha());     
                     
                     
        
        ResultSet resultado = sql.executeQuery();
        if(resultado.next()){        
         
            check = true;        
        }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    return check; 


}
        
}