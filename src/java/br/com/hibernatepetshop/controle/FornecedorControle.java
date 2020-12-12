/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.EnderecoDao;
import br.com.hibernatepetshop.dao.EnderecoDaoImpl;
import br.com.hibernatepetshop.dao.FornecedorDao;
import br.com.hibernatepetshop.dao.FornecedorDaoImpl;
import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Fornecedor;
import static com.sun.faces.facelets.util.Path.context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Silvio
 */
@ManagedBean(name = "fornecedorC")
@ViewScoped
public class FornecedorControle {

    private Fornecedor fornecedor;
    private Endereco endereco;
    private FornecedorDao fornecedorDao;
    private Session sessao;
    private DataModel<Fornecedor> modelFornecedores;
    private List<Endereco> enderecos;
    private int numeroAba = 0;

    public void pesquisarPornome() {
        try {
            fornecedorDao = new FornecedorDaoImpl();
            sessao = HibernateUtil.abrirSessao();
            List<Fornecedor> fornecedores = fornecedorDao.pesquisarPorNome(fornecedor.getNome(), sessao);
            modelFornecedores = new ListDataModel(fornecedores);
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void excluir() {
        fornecedor = modelFornecedores.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            sessao = HibernateUtil.abrirSessao();
            fornecedorDao = new FornecedorDaoImpl();
            fornecedorDao.remover(fornecedor, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));
            fornecedor.setNome(null);
            modelFornecedores = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao excluir " + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar!", ""));
        } finally {
            sessao.close();
        }
    }
    
    public void novoEndereco(){
        endereco = new Endereco();
    }

    public void excluirEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        if (endereco.getId() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            EnderecoDao enderecoDao = new EnderecoDaoImpl();
            sessao = HibernateUtil.abrirSessao();
            try {
                enderecoDao.remover(endereco, sessao);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!!", ""));
                numeroAba = 0;
            } catch (HibernateException e) {
                System.out.println("Erro ao excluir endereco " + e.getMessage());
            } finally {
                sessao.close();
            }
        }

    }

    public void alterar() {
        numeroAba = 1;
        fornecedor = modelFornecedores.getRowData();
        enderecos = fornecedor.getEnderecos();
    }

    public void carregarEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public void onRowCancel(RowEditEvent<Endereco> event) {
        System.out.println("Cancelando alterar endereço");
    }
     
    public void onCellEdit(CellEditEvent event) {
        // Object oldValue = event.getOldValue();
        // Object newValue = event.getNewValue();       
        
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        fornecedorDao = new FornecedorDaoImpl();
        sessao = HibernateUtil.abrirSessao();
        try {
            fornecedor.setDataCadastro(new Date());
            fornecedorDao.salvarOuAlterar(fornecedor, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", ""));
            fornecedor = new Fornecedor();
            numeroAba = 0;
            modelFornecedores = null;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar!", ""));
        } finally {
            sessao.close();
        }
    }

    public void salvarEndereco() {
        if (enderecos == null) {
            enderecos = new ArrayList<>();
            fornecedor.setEnderecos(enderecos);
        }
        if(endereco.getId() == null ){
            enderecos.add(endereco);
        }        
        endereco.setFornecedor(fornecedor);
    }

//    getters e setters
    public Fornecedor getFornecedor() {
        if (fornecedor == null) {
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public DataModel getModelFornecedores() {
        return modelFornecedores;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

}
