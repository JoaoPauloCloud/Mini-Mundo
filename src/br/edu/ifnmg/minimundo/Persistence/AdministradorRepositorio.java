/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Administrador;

/**
 *
 * @author Joao Paulo
 */
public class AdministradorRepositorio extends BancoDados{
    
    public boolean checkAdmin(Administrador filtro){        
        
        boolean check = false;        
        String login = "admin";
        String senha = "1234";
        String where = "";
        //verificando se os campos não estão vazius
        if((filtro.getLogin() == null && filtro.getLogin().isEmpty())||(filtro.getSenha() == null && filtro.getSenha().isEmpty())){
            
            return check;
            
        }
        if(filtro.getLogin() ==  login && filtro.getSenha() == senha ){
            System.out.println("cheguei");
            check = true;
        }
  
    return check; 


}
    
    
    
    
    
    
}
