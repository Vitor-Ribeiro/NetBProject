/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Produto;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class ProdutoDaoImpl extends BaseDaoImpl<Produto, Long> implements ProdutoDao{

    @Override
    public Produto pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Produto) sessao.get(Produto.class, id);
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM Produto WHERE nome LIKE :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Produto> pesquisarPorFornecedor(String fornecedor, Session sessao) 
                                                                       throws HibernateException {
        Query consulta = sessao.createQuery("FROM Produto WHERE fornecedor.nome LIKE :nomeFornecedor");
        consulta.setParameter("nomeFornecedor", "%" + fornecedor + "%");
        return consulta.list();
    }

    @Override
    public List<Produto> pesquisarPorProdutoEstoque(int qtd, String nome,
                                                          Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM Produto WHERE estoque <= :qtdEstoque "
                + "AND nome LIKE :produto");
        consulta.setParameter("qtdEstoque", qtd);
        consulta.setParameter("produto", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Produto> pesquisarPorPrecoMinimoMaximo(double minimo, double maximo, Session sessao)
                                                                           throws HibernateException {
         Query consulta = sessao.createQuery("FROM Produto WHERE preco BETWEEN :minimo AND :maximo");
        consulta.setParameter("minimo", minimo);
        consulta.setParameter("maximo", maximo);
        return consulta.list();
    }

   

    
}
