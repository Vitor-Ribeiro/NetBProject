/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Aluno;
import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Funcionario;
import br.com.hibernatepetshop.entidade.Pessoa;
import br.com.hibernatepetshop.entidade.PessoaSenac;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.entidade.Professor;
import br.com.hibernatepetshop.entidade.Telefone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Admin
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    //Singleton
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Produto.class);
            cfg.addAnnotatedClass(Categoria.class);
            cfg.addAnnotatedClass(Pessoa.class);            
            cfg.addAnnotatedClass(PessoaSenac.class);            
            cfg.addAnnotatedClass(Fornecedor.class);
            cfg.addAnnotatedClass(Funcionario.class);
            cfg.addAnnotatedClass(Endereco.class);
            cfg.addAnnotatedClass(Professor.class);
            cfg.addAnnotatedClass(Aluno.class);
            cfg.addAnnotatedClass(Telefone.class);
            
            cfg.configure("/br/com/hibernatepetshop/dao/hibernate.cfg.xml");
            ServiceRegistry servico = new StandardServiceRegistryBuilder().
                    applySettings(cfg.getProperties()).build();
            
            sessionFactory = cfg.buildSessionFactory(servico);
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
