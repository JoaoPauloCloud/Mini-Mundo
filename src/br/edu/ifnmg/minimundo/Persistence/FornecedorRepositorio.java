/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Fornecedor;
import br.edu.ifnmg.minimundo.DomainModel.Estado;
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
public class FornecedorRepositorio extends BancoDados {

    public boolean Salvar(Fornecedor obj) {
        try {

            if (obj.getId() == 0) {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("insert into fornecedores(cnpj, nome, rs, email, bairro, estado, rua, cidade, numero, complemento,cep) values(?,?,?,?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getcnpj().replace(".", "").replace("-", ""));
                sql.setString(2, obj.getNome());
                sql.setString(3, obj.getRs());
                sql.setString(4, obj.getEmail());
                //Endereço
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
                    return true;
                }

            } else {
                PreparedStatement sql = this.getConexao()
                        .prepareStatement("update fornecedores set cnpj = ?, nome = ? ,rs = ?, email = ?, bairro = ?, estado = ?, rua = ?, cidade = ?, numero = ?, complemento = ?, cep = ? where id = ?");

                sql.setString(1, obj.getcnpj());
                sql.setString(2, obj.getNome());
                sql.setString(3, obj.getRs());
                sql.setString(4, obj.getEmail());
                //Endereço
                sql.setString(5, obj.getBairro());
                sql.setString(6, obj.getEstado().name());
                sql.setString(7, obj.getRua());
                sql.setString(8, obj.getCidade());
                sql.setInt(9, obj.getNumero());
                sql.setString(10, obj.getComplemento());
                sql.setString(11, obj.getCep());
                //ID do fornecedor
                sql.setInt(12, obj.getId());

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;

    }

    public Fornecedor Abrir(int id) {
        try {

            PreparedStatement sql = this.getConexao()
                    .prepareStatement("select * from Fornecedores where id = ?");

            sql.setInt(1, id);

            ResultSet resultado = sql.executeQuery();

            resultado.next();

            Fornecedor fornecedor = new Fornecedor();

            try {
                fornecedor.setId(resultado.getInt("id"));
                fornecedor.setNome(resultado.getString("nome"));
                fornecedor.setcnpj(resultado.getString("cnpj"));
                fornecedor.setRs(resultado.getString("rs"));
                fornecedor.setEmail(resultado.getString("email"));
                //Endereço
                fornecedor.setBairro(resultado.getString("bairro"));
                fornecedor.setEstado(Estado.valueOf(resultado.getString("estado")));
                fornecedor.setRua(resultado.getString("rua"));
                fornecedor.setCidade(resultado.getString("cidade"));
                fornecedor.setNumero(resultado.getInt("numero"));
                fornecedor.setComplemento(resultado.getString("complemento"));
                fornecedor.setCep(resultado.getString("cep"));

            } catch (Exception ex) {
                fornecedor = null;
            }

            return fornecedor;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public List<Fornecedor> Buscar(Fornecedor filtro) {
        try {

            String where = "";

               if(filtro != null){
                if (filtro.getNome() != null && !filtro.getRs().isEmpty()) {
                    where += "nome like '%" + filtro.getNome() + "%'";
                }

                if (filtro.getcnpj() != null && !filtro.getcnpj().isEmpty()
                        && !"00.000.000.0000-00".equals(filtro.getcnpj())) {
                    if (where.length() > 0) {
                        where += " and ";
                    }
                    where += "cnpj = '" + filtro.getcnpj().replace(".", "").replace("-", "") + "'";
                }

                if ((filtro.getEstado().name() != "Todos") && (filtro.getEstado() != null)) {
                    if (where.length() > 0) {
                        where += " and ";
                    }
                    where += "estado = '" + filtro.getEstado().name() + "'";
                }
            
               }
            String consulta = "select * from fornecedores";

            if (where.length() > 0) consulta += " where " + where;
                consulta += " order by nome";
                
            

            PreparedStatement sql = this.getConexao()
                    .prepareStatement(consulta);

            ResultSet resultado = sql.executeQuery();

            List<Fornecedor> fornecedores = new ArrayList<>();

            while (resultado.next()) {

                Fornecedor fornecedor = new Fornecedor();

                try {
                    fornecedor.setId(resultado.getInt("id"));
                    fornecedor.setNome(resultado.getString("nome"));
                    fornecedor.setRs(resultado.getString("rs"));
                    fornecedor.setcnpj(resultado.getString("cnpj"));
                    fornecedor.setEmail(resultado.getString("email"));
                                      
                    //Endereço
                    fornecedor.setBairro(resultado.getString("bairro"));
                    fornecedor.setEstado(Estado.valueOf(resultado.getString("estado")));
                    fornecedor.setRua(resultado.getString("rua"));
                    fornecedor.setCidade(resultado.getString("cidade"));                     
                    fornecedor.setNumero(resultado.getInt("numero"));                    
                    fornecedor.setComplemento(resultado.getString("complemento"));                    
                    fornecedor.setCep(resultado.getString("cep").replace("-", ""));
                    //abrirTelefones(Fornecedor);
                } catch (Exception ex) {
                    fornecedor = null;
                    
                }

                fornecedores.add(fornecedor);
            }
            
            return fornecedores;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
