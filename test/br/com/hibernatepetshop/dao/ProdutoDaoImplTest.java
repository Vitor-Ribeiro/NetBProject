/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class ProdutoDaoImplTest {

    private Produto produto;
    private Fornecedor fornecedor;
    private ProdutoDao produtoDao;
    private Session sessao;
    private FornecedorDaoImplTest fornecedorDaoImplTest;

    public ProdutoDaoImplTest() {
        produtoDao = new ProdutoDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        produto = new Produto(
                null,
                "Nome " + UtilTeste.gerarCaracter(7),
                UtilTeste.gerarNumero(5),
                "bla, bla. bla",
                Double.parseDouble(UtilTeste.gerarNumero(3)),
                Integer.parseInt(UtilTeste.gerarNumero(3))
        );

        fornecedorDaoImplTest = new FornecedorDaoImplTest();
        fornecedor = fornecedorDaoImplTest.buscarFornecedorBd();
        produto.setFornecedor(fornecedor);
        
        CategoriaDaoImplTest categoriaTeste = new CategoriaDaoImplTest();
        Categoria categoria = categoriaTeste.buscarCategoriaBD();
        produto.setCategoria(categoria);
        sessao = HibernateUtil.abrirSessao();
        produtoDao.salvarOuAlterar(produto, sessao);
        sessao.close();
        assertNotNull(produto.getId());
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProdutoBd();
        produto.setNome("produto alterado");
        sessao = HibernateUtil.abrirSessao();
        produtoDao.salvarOuAlterar(produto, sessao);
        Produto produtoAlt = produtoDao.pesquisarPorId(produto.getId(), sessao);
        sessao.close();
        assertEquals(produto.getNome(), produtoAlt.getNome());
    }

//    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarProdutoBd();
        sessao = HibernateUtil.abrirSessao();
        produtoDao.remover(produto, sessao);
        Produto produtoExcluido = produtoDao.pesquisarPorId(produto.getId(), sessao);
        sessao.close();
        assertNull(produtoExcluido);
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProdutoBd();
        sessao = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorNome(produto.getNome(), sessao);
        sessao.close();
        assertTrue(produtos.size() > 0);
    }

//    @Test
    public void testPesquisarPorFornecedor() {
        System.out.println("pesqusiarPorFornecedor");
        buscarProdutoBd();
        sessao = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorFornecedor(fornecedor.getNome(), sessao);
        sessao.close();
        assertTrue(produtos.size() > 0);
    }

//    @Test
    public void testPesquisarPorProdutoEstoque() {
        System.out.println("pesquisarPorProdutoEstoque");
        buscarProdutoBd();
        int qtd = produto.getEstoque();
        sessao = HibernateUtil.abrirSessao();
        List<Produto> produtos = produtoDao.pesquisarPorProdutoEstoque(qtd, produto.getNome(), sessao);
       assertTrue(produtos.size() > 0);
    }

//    @Test
    public void testPesquisarPorPrecoMinimoMaximo() {
        System.out.println("pesquisarPorPrecoMinimoMaximo");
        buscarProdutoBd();
        sessao = HibernateUtil.abrirSessao();
        List<Produto> produtos = 
                produtoDao.pesquisarPorPrecoMinimoMaximo((produto.getPreco() - 20),
                                                                   (produto.getPreco()), sessao);
        assertTrue(!produtos.isEmpty());
    }

    public Produto buscarProdutoBd() {
        Long id;
        sessao = HibernateUtil.abrirSessao();
        try {
            Query consulta = sessao.createQuery("SELECT max(id) FROM Produto");
            id = (Long) consulta.uniqueResult();
            if (id == null) {
                sessao.close();
                testSalvar();
            } else {
                produto = produtoDao.pesquisarPorId(id, sessao);
                sessao.close();
            }
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar buscarProdutoBd " + e.getMessage());
        }
        return produto;
    }

}
