/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.ItemVenda;
import br.edu.ifnmg.minimundo.DomainModel.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Joao Paulo
 */
public class ItemVendaRepositorio extends BancoDados {
    
    public ItemVendaRepositorio() {
        super();
    }
    
    public boolean Salvar(ItemVenda itemvenda){
        try {
            
            if(itemvenda.getId() == 0){
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into ItemVendas(cliente_id, usuario_id) values(?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setInt(1,  itemvenda.getCliente_id().getId());
                sql.setInt(2, itemvenda.getUsuario_id().getId());

                if(sql.executeUpdate() > 0){ 
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    itemvenda.setId(chave.getInt(1));
                    
                    insere_produtos(itemvenda);
                    
                    return true;
                }
                else
                    return false;
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update ItemVendas set cliente_id = ?, usuario_id = ? where id = ?");

                sql.setInt(1,  itemvenda.getCliente_id().getId());
                sql.setInt(2, itemvenda.getUsuario_id().getId());
                sql.setInt(3, itemvenda.getId());

                if(sql.executeUpdate() > 0) {
                    
                    PreparedStatement sql2 = this.getConexao()
                        .prepareStatement("delete from ListaVendas where ItemVendas_id = ?");
                    
                    sql2.setInt(1, itemvenda.getId());
                    
                    sql2.executeUpdate();
                    
                    insere_produtos(itemvenda);
                    
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

    private void insere_produtos(ItemVenda itemvenda) throws SQLException {
        for(Produto produto : itemvenda.getProdutos()){
            PreparedStatement sql2 = this.getConexao()
                    .prepareStatement("insert into ListaVendas(itemvenda_id, produto_id) values(?,?)");
            sql2.setInt(1, itemvenda.getId());
            sql2.setInt(2, produto.getId());
            sql2.executeUpdate();
        }
        
    }
    
    
}
