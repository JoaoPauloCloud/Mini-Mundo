/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package br.edu.ifnmg.minimundo.Persistence;


import br.edu.ifnmg.minimundo.DomainModel.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Joao Paulo
 */


public class ProdutoRepositorio extends BancoDados {
    
    public boolean Salvar(Produto obj) {
        try {

            if (obj.getId() == 0) {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into produtos(descricao,unidadecompra,unidadevenda,precodcompra,precodvenda,status,idfornecedor,nome) values(?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getDescricao());
                sql.setString(2, obj.getUc());
                sql.setString(3, obj.getUv());
                sql.setFloat(4, obj.getPc());
                sql.setFloat(5, obj.getPv());
                sql.setInt(6, 1);
                sql.setInt(7, obj.getFornecedor().getId());
                sql.setString(8, obj.getFornecedor().getNome());

                if (sql.executeUpdate() > 0) {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));                   

                    return true;
                } else {
                    return false;
                }
            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Produtos set descricao = ?, unidadecompra = ?, unidadevenda = ?, precodcompra = ?, precodvenda = ?, status = ?,idfornecedor = ?,nome = ? where id = ?");

                sql.setString(1, obj.getDescricao());
                sql.setString(2, obj.getUc());
                sql.setString(3, obj.getUv());
                sql.setFloat(4, obj.getPc());
                sql.setFloat(5, obj.getPv());
                sql.setInt(6, 1);
                sql.setInt(6, obj.getFornecedor().getId());
                sql.setString(7, obj.getFornecedor().getNome());
                sql.setInt(8, obj.getId());
                

                if (sql.executeUpdate() > 0) {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1));
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

    public List<Produto> Buscar(Produto filtro) {
         
        try {

            String where = "";

            if (filtro != null) {
                if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                    where += "nome like '%" + filtro.getNome() + "%'";
                }

               

                if ((filtro.getDescricao() != null && !filtro.getDescricao().isEmpty())) {
                    if (where.length() > 0) {
                        where += " and ";
                    }
                    where += "descricao = '" + filtro.getDescricao() + "'";
                }
            }

            String consulta = "select * from produtos";

            if (where.length() > 0) {
                consulta += " where " + where;
            }

            PreparedStatement sql = this.getConexao()
                    .prepareStatement(consulta);

            ResultSet resultado = sql.executeQuery();

            List<Produto> produtos = new ArrayList<>();
            
            //FornecedorRepositorio repo_forne = new FornecedorRepositorio();

            while (resultado.next()) {

                Produto produto = new Produto();

                try {
                produto.setId(resultado.getInt("idproduto"));               
                produto.setDescricao(resultado.getString("descricao"));
                produto.setUc(resultado.getString("unidadecompra"));
                produto.setUv(resultado.getString("unidadevenda"));
                produto.setPc(resultado.getFloat("precodcompra"));
                produto.setPv(resultado.getFloat("precodvenda"));
                produto.setNome(resultado.getString("nome"));
                produto.setIdfornecedor(resultado.getInt("idfornecedor"));
                //produto.setFornecedor(repo_forne.Abrir( resultado.getInt("nome") ));
                //produto.setFornecedor(repo_forne.Abrir( resultado.getInt("idfornecedor") ));
              
                } catch (Exception ex) {
                    produto = null;
                }

                produtos.add(produto);
            }
            return produtos;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
        
    }
    
    
    
    

