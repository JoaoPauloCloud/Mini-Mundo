/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                
                if(sql.executeUpdate() > 0){ 
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                                        
                    return true;
                }
                else
                    return false;

               
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Usuarios set nome = ?, cpf = ?, usuario = ?, senha = ?, tlefone = ? where id = ?");

                sql.setString(1, obj.getNome());
                sql.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
                sql.setString(3, obj.getUsuario());                
                sql.setString(4, obj.getSenha());
                sql.setString(5, obj.getTelefone());
                sql.setInt(6, obj.getId());

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
    
    public boolean checkAdmin(Usuario filtro){        
        
        boolean check = false;        
        String login = "admin";
        String senha = "1234";
        
        String where = "";
        //verificando se os campos não estão vazius
        if((filtro.getUsuario() == null && filtro.getUsuario().isEmpty())||(filtro.getSenha() == null && filtro.getSenha().isEmpty())){
            
            return check;
            
        }
        
        
        if(filtro.getUsuario().equals(login) && filtro.getSenha().equals(senha) ){
            
            check = true;
        }
  
    return check; 


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