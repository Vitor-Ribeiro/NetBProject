/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Categoria;
import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Professor;
import br.com.hibernatepetshop.entidade.Telefone;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class ProfessorDaoImplTest {

    private Professor professor;
    private ProfessorDao professorDao;
    private Session sessao;

    public ProfessorDaoImplTest() {
        professorDao = new ProfessorDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        professor = new Professor(
                null,
                "Nome " + UtilTeste.gerarCaracter(10),
                UtilTeste.gerarCpf(),
                UtilTeste.gerarNumero(5),
                UtilTeste.gerarEmail(),
                UtilTeste.gerarNumero(4)
        );
        Endereco endereco = new Endereco(null,
                "Rua " + UtilTeste.gerarCaracter(7),
                UtilTeste.gerarNumero(3),
                "Bairro " + UtilTeste.gerarCaracter(8),
                "Cidade " + UtilTeste.gerarCaracter(9),
                "SC",
                "88888-888",
                "casa"
        );
        professor.setEndereco(endereco);
        endereco.setPessoaSenac(professor);
        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            telefones.add(criarTelefone());
        }
        
        professor.setTelefones(telefones);
        for (Telefone telefone : telefones) {
            telefone.setPessoaSenac(professor);
        }
        sessao = HibernateUtil.abrirSessao();
        professorDao.salvarOuAlterar(professor, sessao);
        sessao.close();
        assertNotNull(endereco.getId());
        assertNotNull(professor.getId());
        assertNotNull(professor.getTelefones().get(0).getId());
    }

    private Telefone criarTelefone() {
        Telefone telefone = new Telefone(
                null,
                UtilTeste.gerarTelefone(),
                "Fixo",
                "Vivo"
        );
        return telefone;
    }

    //@Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProfessorBD();
        professor.setNome(UtilTeste.gerarCaracter(15));
        professor.getEndereco().setLogradouro("Av. " + UtilTeste.gerarCaracter(15));
        professor.getTelefones().get(0).setNumero(UtilTeste.gerarTelefone());
        sessao = HibernateUtil.abrirSessao();
        professorDao.salvarOuAlterar(professor, sessao);
        
        Professor professorAlterado = professorDao.pesquisarPorId(professor.getId(), sessao);
        Telefone telefone = professorAlterado.getTelefones().get(0);
        sessao.close();
        
        assertEquals(professor.getNome(), professorAlterado.getNome());
        assertEquals(professor.getEndereco().getLogradouro(),
                                                     professorAlterado.getEndereco().getLogradouro());
        assertEquals(professor.getTelefones().get(0).getNumero(), telefone.getNumero());
    }

    //@Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarProfessorBD();
        sessao = HibernateUtil.abrirSessao();
        professorDao.remover(professor, sessao);
        Professor professorExcluidoo = professorDao.pesquisarPorId(professor.getId(), sessao);
        sessao.close();
        assertNull(professorExcluidoo);
    }

    //@Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

    //@Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfessorBD();
        sessao = HibernateUtil.abrirSessao();
        List<Professor> professores = professorDao.pesquisarPorNome(professor.getNome(), sessao);
        assertTrue(professores.size() > 0);
    }

    
    public Professor buscarProfessorBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("Select distinct(p) from Professor p"
                + " join fetch p.telefones");
        List<Professor> professores = consulta.list();
        if (professores.isEmpty()) {
            sessao.close();
            testSalvar();
        } else {
            professor = professores.get(0);
            sessao.close();
        }
        return professor;
    }
}
