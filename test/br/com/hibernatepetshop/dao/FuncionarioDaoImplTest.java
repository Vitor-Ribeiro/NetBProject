/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.dao;

import br.com.hibernatepetshop.entidade.Endereco;
import br.com.hibernatepetshop.entidade.Funcionario;
import br.com.hibernatepetshop.util.UtilTeste;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class FuncionarioDaoImplTest {
    
    private Funcionario funcionario;
    private FuncionarioDao funcionarioDao;
    private Session sessao;
    
    public FuncionarioDaoImplTest() {
        funcionarioDao = new FuncionarioDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        funcionario = new Funcionario(null,
                "Func " + UtilTeste.gerarCaracter(7) ,
                UtilTeste.gerarEmail(),
                UtilTeste.gerarTelefone(),
                UtilTeste.gerarNumero(5)
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
        funcionario.setEndereco(endereco);
        endereco.setFuncionario(funcionario);
        sessao = HibernateUtil.abrirSessao();
        funcionarioDao.salvarOuAlterar(funcionario, sessao);
        sessao.close();
        assertNotNull(funcionario.getId());
        assertNotNull(endereco.getId());
    }
    
//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
    }
    
}
