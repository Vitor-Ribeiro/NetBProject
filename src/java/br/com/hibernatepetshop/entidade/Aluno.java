/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hibernatepetshop.entidade;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "aluno")
@PrimaryKeyJoinColumn(name = "idPessoaSenac")
public class Aluno extends PessoaSenac {

    private String matricula;

    public Aluno() {
    }

    public Aluno(Long id, String nome, String cpf, String rg, String email, String matricula) {
        super(id, nome, cpf, rg, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
}
