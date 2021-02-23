/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.model;

import br.com.fafic.enums.TipoPessoa;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@NamedQueries({
    @NamedQuery(name = "professor.getAll", query = "select p from Professor p")
})
public class Professor extends Pessoa {

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Turma> turmas;

    public Professor() {
    }

    public Professor(List<Turma> turmas, String nome, String cpf, String login, String senha, Endereco endereco, Contato contato, TipoPessoa tipoPessoa) {
        super(nome, cpf, login, senha, endereco, contato, tipoPessoa);
        this.turmas = turmas;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.turmas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Professor other = (Professor) obj;
        if (!Objects.equals(this.turmas, other.turmas)) {
            return false;
        }
        return true;
    }
}
