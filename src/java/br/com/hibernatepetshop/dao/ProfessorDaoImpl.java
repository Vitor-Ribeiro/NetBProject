/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Professor;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class ProfessorDaoImpl extends BaseDaoImpl<Professor, Long> implements ProfessorDao, Serializable{

    @Override
    public Professor pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Professor) sessao.get(Professor.class, id);
    }

    @Override
    public List<Professor> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("Select distinct(p) from Professor p"
                + " join fetch p.telefones where p.nome like :nomeProfessor");
        consulta.setParameter("nomeProfessor", "%" + nome + "%");
        return consulta.list();
    }
    
}
