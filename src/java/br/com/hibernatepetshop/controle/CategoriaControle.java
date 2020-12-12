/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.CategoriaDao;
import br.com.hibernatepetshop.dao.CategoriaDaoImpl;
import br.com.hibernatepetshop.dao.FornecedorDao;
import br.com.hibernatepetshop.dao.FornecedorDaoImpl;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.dao.ProdutoDao;
import br.com.hibernatepetshop.dao.ProdutoDaoImpl;
import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Fornecedor;
import br.com.hibernatepetshop.entidade.Produto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Silvio
 */
@ManagedBean(name = "categoriaC")
@ViewScoped
public class CategoriaControle {

    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Session sessao;
    private DataModel<Categoria> modelCategorias;
    private int numeroAba = 0;
    private List<Produto> produtos;
    

    public CategoriaControle() {
        
    }

    public void pesquisarPornome() {
        try {
            categoriaDao = new CategoriaDaoImpl();
            sessao = HibernateUtil.abrirSessao();
            List<Categoria> categorias = categoriaDao.pesquisarPorNome(categoria.getNome(), sessao);
            modelCategorias = new ListDataModel(categorias);
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void excluir() {
        categoria = modelCategorias.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            sessao = HibernateUtil.abrirSessao();
            categoriaDao = new CategoriaDaoImpl();
            categoriaDao.remover(categoria, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            categoria.setNome(null);
            modelCategorias = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao excluir " + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir!", ""));
        } finally {
            sessao.close();
        }
    }

    public void alterar() {
        numeroAba = 1;
        categoria = modelCategorias.getRowData();
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        categoriaDao = new CategoriaDaoImpl();
        sessao = HibernateUtil.abrirSessao();
        try {
            categoria.setProdutos(produtos);
            categoriaDao.salvarOuAlterar(categoria, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", ""));
            categoria = new Categoria();
            numeroAba = 0;
            modelCategorias = null;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar!", ""));
        } finally {
            sessao.close();
        }
    }

//    getters e setters
    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public DataModel getModelCategorias() {
        return modelCategorias;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

    public List<Produto> getProdutos() {
        return produtos;
    } 

}
