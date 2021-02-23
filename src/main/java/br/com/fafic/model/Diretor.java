/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.model;

import br.com.fafic.enums.TipoPessoa;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@NamedQueries({
    @NamedQuery(name = "diretor.getAll", query = "select d from Diretor d"),
    @NamedQuery(name = Diretor.DIRETOR_COUNT, query = "select count(d) from Diretor d")
})
public class Diretor extends Pessoa {
    
    public static final String DIRETOR_GET_ALL = "diretor.getAll";
    public static final String DIRETOR_COUNT = "diretor.getCount";

    private String titulacao;

    public Diretor() {
    }

    public Diretor(String titulacao, String nome, String cpf, String login, String senha, Endereco endereco, Contato contato, TipoPessoa tipoPessoa) {
        super(nome, cpf, login, senha, endereco, contato, tipoPessoa);
        this.titulacao = titulacao;
    }

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.titulacao);
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
        final Diretor other = (Diretor) obj;
        if (!Objects.equals(this.titulacao, other.titulacao)) {
            return false;
        }
        return true;
    }
}
