/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;



@Entity
@NamedQueries({
    @NamedQuery(name = "disciplinaNotas.getAll", query = "select d from DisciplinaNotas d")
})
public class DisciplinaNotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    private double nota1;
    private double nota2;
    private double nota3;
    private double media;
    private String situacao = "";
    @OneToOne
    private Disciplina disciplina;
    @OneToOne
    private Aluno alunoMatriculado;

    public DisciplinaNotas() {
    }

    public DisciplinaNotas(Long ID, double nota1, double nota2, double nota3, double media, String situacao, Disciplina disciplina, Aluno alunoMatriculado) {
        this.ID = ID;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.media = media;
        this.situacao = situacao;
        this.disciplina = disciplina;
        this.alunoMatriculado = alunoMatriculado;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAlunoMatriculado() {
        return alunoMatriculado;
    }

    public void setAlunoMatriculado(Aluno alunoMatriculado) {
        this.alunoMatriculado = alunoMatriculado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.disciplina);
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
        final DisciplinaNotas other = (DisciplinaNotas) obj;
        if (!Objects.equals(this.disciplina, other.disciplina)) {
            return false;
        }
        return true;
    }
}
