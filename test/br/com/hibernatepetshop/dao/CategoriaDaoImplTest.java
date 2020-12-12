/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class CategoriaDaoImplTest {

    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Session sessao;

    public CategoriaDaoImplTest() {
        categoriaDao = new CategoriaDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        categoria = new Categoria(null, "Categoria " + UtilTeste.gerarCaracter(10), "bla, bla...");
        sessao = HibernateUtil.abrirSessao();
        categoriaDao.salvarOuAlterar(categoria, sessao);
        sessao.close();
        assertNotNull(categoria.getId());
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarCategoriaBD();
        categoria.setNome("Categoria alterado " + UtilTeste.gerarCaracter(5));
        sessao = HibernateUtil.abrirSessao();
        categoriaDao.salvarOuAlterar(categoria, sessao);
        Categoria categoriaAlt = categoriaDao.pesquisarPorId(categoria.getId(), sessao);
        sessao.close();
        assertEquals(categoria.getNome(), categoriaAlt.getNome());
    }

//    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarCategoriaBD();
        sessao = HibernateUtil.abrirSessao();
        categoriaDao.remover(categoria, sessao);
        Categoria categoriaExc = categoriaDao.pesquisarPorId(categoria.getId(), sessao);
        sessao.close();
        assertNull(categoriaExc);
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarCategoriaBD();
        sessao = HibernateUtil.abrirSessao();
        List<Categoria> categorias = categoriaDao.pesquisarPorNome(categoria.getNome(), sessao);
        sessao.close();
        assertTrue(categorias.size() > 0);
    }

    @Test
    public void testPesquisarTodo() {
        System.out.println("pesquisarTodo");
        buscarCategoriaBD();
        sessao = HibernateUtil.abrirSessao();
        List<Categoria> categorias = categoriaDao.pesquisarTodo(sessao);
        sessao.close();
        assertTrue(!categorias.isEmpty());
    }

    @Test
    public void testPesquisarPorNomeProduto() {
        System.out.println("pesquisarPorNomeProduto");
        sessao = HibernateUtil.abrirSessao();
        categoria = categoriaDao.pesquisarPorNomeProduto("nome", sessao);
        sessao.close();
        System.out.println("Categoria: " + categoria.getNome());
        for (Produto produto : categoria.getProdutos()) {
            System.out.println("Produto " + produto.getNome());
            System.out.println("");
        }
    }

    public Categoria buscarCategoriaBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Categoria");
        List<Categoria> categorias = consulta.list();
        if (categorias.isEmpty()) {
            sessao.close();
            testSalvar();
        } else {
            categoria = categorias.get(0);
            sessao.close();
        }
        return categoria;
    }
    

}
