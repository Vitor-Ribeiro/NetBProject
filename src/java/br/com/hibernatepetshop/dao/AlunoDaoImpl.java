/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Aluno;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class AlunoDaoImpl extends BaseDaoImpl<Aluno, Long> implements AlunoDao, Serializable{

    @Override
    public Aluno pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Aluno) sessao.get(Aluno.class, id);
    }

    @Override
    public List<Aluno> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("Select distinct(p) from Aluno p"
                + " join fetch p.telefones where p.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
