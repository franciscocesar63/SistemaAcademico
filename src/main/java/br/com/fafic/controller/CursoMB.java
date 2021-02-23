/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Curso;
import br.com.fafic.model.Disciplina;
import br.com.fafic.service.CursoDao;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



@ManagedBean(name = "cursoMB")
@ViewScoped
public class CursoMB implements Serializable {

    @EJB
    private CursoDao cursoService;
    private Curso curso;

    public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

    public CursoDao getCursoService() {
        return cursoService;
    }

    public void setCursoService(CursoDao cursoService) {
        this.cursoService = cursoService;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getAll() throws Exception {
        return cursoService.getAll();
    }

    public String retornaPagina() {
        return "vincular";
    }

    public void salvarCurso() {
        try {
            cursoService.salvar(curso);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(CursoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Curso!");
        }
        this.curso = null;
    }

    public void removerCurso(Curso c) {
        try {
            cursoService.remover(c);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(CursoMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Remover Curso!");
        }
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Curso getCursoCoordenador() {
        Coordenador coor = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        return coor.getCurso();
    }

    public List<Disciplina> getDisciplinasCurso() {
        return getCursoCoordenador().getDisciplinas();
    }
}
