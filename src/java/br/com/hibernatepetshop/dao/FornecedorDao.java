/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Fornecedor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public interface FornecedorDao extends BaseDao<Fornecedor, Long>{
    
    List<Fornecedor> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
    
    List<Fornecedor> pesquisarTodo(Session sessao)throws HibernateException;
    
    Fornecedor pesquisarPorNomeProduto(String nomeProduto, Session sessao)
                                                                    throws HibernateException;
}
