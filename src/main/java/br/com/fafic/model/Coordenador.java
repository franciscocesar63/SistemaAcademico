/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;



@Entity
@NamedQueries({
    @NamedQuery(name = "coordenador.getAll", query = "select c from Coordenador c")
})
public class Coordenador extends Pessoa {
    
    @OneToOne(fetch = FetchType.EAGER)
    private Curso curso;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
