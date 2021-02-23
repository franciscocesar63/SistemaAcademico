/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Curso;
import br.com.fafic.model.Disciplina;
import br.com.fafic.model.Professor;
import br.com.fafic.service.CursoDao;
import br.com.fafic.service.DisciplinaDao;
import br.com.fafic.service.ProfessorDao;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;



@ManagedBean(name = "displinaMB")
@ViewScoped
public class DisciplinaMB implements Serializable {

    @EJB
    private DisciplinaDao disciplinaService;
    private Disciplina disciplina;
    @EJB
    private CursoDao cursoService;

    public Disciplina getDisciplina() {
        if (disciplina == null) {
            disciplina = new Disciplina();
        }
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        
    }

    public CursoDao getCursoService() {
        return cursoService;
    }

    public void setCursoService(CursoDao cursoService) {
        this.cursoService = cursoService;
    }

    public List<Disciplina> getAll() throws Exception {
        return disciplinaService.getAll();
    }

    public List<Disciplina> getDisciplinaCurso() {
        Coordenador coordenadorSessao = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        Curso curso = coordenadorSessao.getCurso();
        return curso.getDisciplinas();
    }
    
    public Set<Professor> getProfessorCurso() {
        List<Disciplina> disciplinasCurso = getDisciplinaCurso();
        Set<Professor> professoresCurso = new HashSet<>();
        for(Disciplina d : disciplinasCurso) {
            professoresCurso.add(d.getProfessor());
        }
        return professoresCurso;
    }

    public void salvar() {
        try {
            disciplinaService.salvar(disciplina);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DisciplinaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Disciplina!");
        }
        this.disciplina = null;
    }

    public void remover(Disciplina d) {
        try {
            disciplinaService.remover(d);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DisciplinaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Remover Disciplina!");
        }
    }

    public void salvarDisciplinaParaVinculo(Disciplina d) {
        getExternalContext().getSessionMap().put("vinculaDisciplina", d);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/vincularDisciplina.xhtml?faces-redirect=true");
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Disciplina getDisciplinaSalva() {
        return (Disciplina) getExternalContext().getSessionMap().get("vinculaDisciplina");
    }

    public void redirecionar() {
        getExternalContext().getSessionMap().remove("vinculaDisciplina");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/index.xhtml?faces-redirect=true");
    }

    public void vincularDisciplina(Professor p) {
        Disciplina disciplinaSalva = getDisciplinaSalva();
        //System.out.println(disciplinaSalva.getId());
        Coordenador coordenadorSessao = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        if (disciplinaSalva.getProfessor() == null) {
            disciplinaSalva.setProfessor(p);
            Curso curso = coordenadorSessao.getCurso();
            disciplinaService.salvar(disciplinaSalva);
            //curso.addDisciplina(disciplina);
//            List<Disciplina> listDisciplinas = curso.getDisciplinas();
//            listDisciplinas.add(disciplinaSalva);
            curso.getDisciplinas().add(disciplinaSalva);
            cursoService.salvar(curso);
            FacesMessageUtil.success();
        } else {
            FacesMessageUtil.fail("Essa Disciplina já possui um Professor");
        }
    }
}
