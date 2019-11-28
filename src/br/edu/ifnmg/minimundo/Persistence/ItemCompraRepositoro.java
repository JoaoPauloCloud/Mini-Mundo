/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.ItemCompra;
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
public class ItemCompraRepositoro extends BancoDados{
    
    public boolean Salvar(ItemCompra itemcompra){
        try {
            
            if(itemcompra.getId() == 0){
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into ItemCompras(fornecedor_id, usuario_id) values(?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setInt(1,  itemcompra.getFornecedor_id().getId());
                sql.setInt(2, itemcompra.getUsuario_id().getId());

                if(sql.executeUpdate() > 0){ 
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    itemcompra.setId(chave.getInt(1));
                    
                    insere_produtos(itemcompra);
                    
                    return true;
                }
                else
                    return false;
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update ItemCompras set fornecedor_id = ?, usuario_id = ? where id = ?");

                sql.setInt(1,  itemcompra.getFornecedor_id().getId());
                sql.setInt(2, itemcompra.getUsuario_id().getId());
                sql.setInt(3, itemcompra.getId());

                if(sql.executeUpdate() > 0) {
                    
                    PreparedStatement sql2 = this.getConexao()
                        .prepareStatement("delete from ListaVendas where ItemVendas_id = ?");
                    
                    sql2.setInt(1, itemcompra.getId());
                    
                    sql2.executeUpdate();
                    
                    insere_produtos(itemcompra);
                    
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

    private void insere_produtos(ItemCompra itemcompra) throws SQLException {
        for(Produto produto : itemcompra.getProdutos()){
            PreparedStatement sql2 = this.getConexao()
                    .prepareStatement("insert into ListaVendas(itemcompra_id, produto_id) values(?,?)");
            sql2.setInt(1, itemcompra.getId());
            sql2.setInt(2, produto.getId());
            sql2.executeUpdate();
        }
        
    }
}
