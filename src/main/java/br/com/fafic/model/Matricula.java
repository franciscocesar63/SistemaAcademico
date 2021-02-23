/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@NamedQueries({
    @NamedQuery(name = "matricula.getAll", query = "select m from Matricula m"),})
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    @Column(unique = true)
    private String matricula;
    @OneToOne
    private Turma turma;
    @OneToOne
    private Aluno aluno;
    @OneToOne
    private Curso curso;
    private boolean ativada;
    @Temporal(TemporalType.DATE)
    private Date dataMatricula;

    public Matricula(String matricula, Turma turma, Aluno aluno, boolean ativada, Date data) {
        this.matricula = matricula;
        this.turma = turma;
        this.aluno = aluno;
        this.ativada = ativada;
        this.dataMatricula = data;
    }

    public Matricula() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public boolean isAtivada() {
        return ativada;
    }

    public void setAtivada(boolean ativada) {
        this.ativada = ativada;
    }

    public Date getData() {
        return dataMatricula;
    }

    public void setData(Date data) {
        this.dataMatricula = data;
    }
    
    public String getAtiva() {
        if(isAtivada()) {
            return "Ativa";
        } else {
            return "Inativa";
        }
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.turma);
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
        final Matricula other = (Matricula) obj;
        if (!Objects.equals(this.turma, other.turma)) {
            return false;
        }
        return true;
    }
}
