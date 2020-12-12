/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.controle;

import br.com.hibernatepetshop.dao.HibernateUtil;
import br.com.hibernatepetshop.dao.ProfessorDao;
import br.com.hibernatepetshop.dao.ProfessorDaoImpl;
import br.com.hibernatepetshop.dao.TelefoneDao;
import br.com.hibernatepetshop.dao.TelefoneDaoImpl;
import br.com.hibernatepetshop.entidade.PessoaSenac;
import br.com.hibernatepetshop.entidade.Professor;
import br.com.hibernatepetshop.entidade.Telefone;
import java.util.ArrayList;
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
@ManagedBean(name = "professorC")
@ViewScoped
public class ProfessorControle {

    private Professor professor;
    private Telefone telefone;
    private ProfessorDao professorDao;
    private Session sessao;
    private DataModel<Professor> modelProfessores;
    private List<Telefone> telefones;
    private int numeroAba = 0;

    public void pesquisarPornome() {
        try {
            professorDao = new ProfessorDaoImpl();
            sessao = HibernateUtil.abrirSessao();
            List<Professor> professores = professorDao.pesquisarPorNome(professor.getNome(), sessao);
            modelProfessores = new ListDataModel(professores);
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar por nome - controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void excluir() {
        professor = modelProfessores.getRowData();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            sessao = HibernateUtil.abrirSessao();
            professorDao = new ProfessorDaoImpl();
            professorDao.remover(professor, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!", ""));            
            professor = null;
            telefones = null;
            modelProfessores = null;           
        } catch (HibernateException e) {
            System.out.println("Erro ao excluir " + e.getMessage());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar!", ""));
        } finally {
            sessao.close();
        }
    }
    
    public void novoTelefone(){
        telefone = new Telefone();
    }

    public void excluirTelefone(Telefone telefone) {
        telefones.remove(telefone);
        if (telefone.getId() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            TelefoneDao telefoneDao = new TelefoneDaoImpl();
            sessao = HibernateUtil.abrirSessao();
            try {
                telefoneDao.remover(telefone, sessao);
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
        professor = modelProfessores.getRowData();
        telefones = professor.getTelefones();
    }

    public void carregarTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    
    

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();
        professorDao = new ProfessorDaoImpl();
        sessao = HibernateUtil.abrirSessao();
        try {            
            professorDao.salvarOuAlterar(professor, sessao);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso!", ""));
            professor = new Professor();
            numeroAba = 0;
            modelProfessores = null;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar!", ""));
        } finally {
            sessao.close();
        }
    }

    public void salvarTelefone() {
        if (telefones == null) {
            telefones = new ArrayList<>();
            professor.setTelefones(telefones);
        }
        if(telefone.getId() == null ){
            telefones.add(telefone);
        }        
        telefone.setPessoaSenac(professor);
    }

//    getters e setters
    public Professor getProfessor() {
        if (professor == null) {
            professor = new Professor();
        }
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public DataModel getModelProfessores() {
        return modelProfessores;
    }

    public int getNumeroAba() {
        return numeroAba;
    }

    public void setNumeroAba(int numeroAba) {
        this.numeroAba = numeroAba;
    }

    public Telefone getTelefone() {
        if (telefone == null) {
            telefone = new Telefone();
        }
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

}
