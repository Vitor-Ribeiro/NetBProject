/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Aluno;
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
public class AlunoDaoImplTest {

    private Aluno aluno;
    private AlunoDao alunoDao;
    private Session sessao;

    public AlunoDaoImplTest() {
        alunoDao = new AlunoDaoImpl();
    }
    
//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        aluno = new Aluno(
                null,
                "Nome " + UtilTeste.gerarCaracter(10),
                UtilTeste.gerarCpf(),
                UtilTeste.gerarNumero(5),
                UtilTeste.gerarEmail(),
                UtilTeste.gerarNumero(6)
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
        aluno.setEndereco(endereco);
        endereco.setPessoaSenac(aluno);
        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            telefones.add(criarTelefone());
        }
        
        aluno.setTelefones(telefones);
        for (Telefone telefone : telefones) {
            telefone.setPessoaSenac(aluno);
        }
        sessao = HibernateUtil.abrirSessao();
        alunoDao.salvarOuAlterar(aluno, sessao);
        sessao.close();
        assertNotNull(endereco.getId());
        assertNotNull(aluno.getId());
        assertNotNull(aluno.getTelefones().get(0).getId());
    }
    
//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarAlunoBD();
        aluno.setNome(UtilTeste.gerarCaracter(15));
        aluno.getEndereco().setLogradouro("Av. " + UtilTeste.gerarCaracter(15));
        aluno.getTelefones().get(0).setNumero(UtilTeste.gerarTelefone());
        sessao = HibernateUtil.abrirSessao();
        alunoDao.salvarOuAlterar(aluno, sessao);
        
        Aluno alunoAlterado = alunoDao.pesquisarPorId(aluno.getId(), sessao);
        Telefone telefone = alunoAlterado.getTelefones().get(0);
        sessao.close();
        
        assertEquals(aluno.getNome(), alunoAlterado.getNome());
        assertEquals(aluno.getEndereco().getLogradouro(), alunoAlterado.getEndereco().getLogradouro());
        assertEquals(aluno.getTelefones().get(0).getNumero(), telefone.getNumero());
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
     
//     @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarAlunoBD();
        sessao = HibernateUtil.abrirSessao();
        alunoDao.remover(aluno, sessao);
        Aluno professorAluno = alunoDao.pesquisarPorId(aluno.getId(), sessao);
        sessao.close();
        assertNull(professorAluno);
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarAlunoBD();
        sessao = HibernateUtil.abrirSessao();
        List<Aluno> alunos = alunoDao.pesquisarPorNome(aluno.getNome(), sessao);
        assertTrue(alunos.size() > 0);
    }
    
    public Aluno buscarAlunoBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("Select distinct(p) from Aluno p"
                + " join fetch p.telefones");
        List<Aluno> alunos = consulta.list();
        if (alunos.isEmpty()) {
            sessao.close();
            testSalvar();
        } else {
            aluno = alunos.get(0);
            sessao.close();
        }
        return aluno;
    }

}
