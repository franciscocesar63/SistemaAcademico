/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Aluno;
import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Curso;
import br.com.fafic.model.Professor;
import br.com.fafic.model.Turma;
import br.com.fafic.service.CursoDao;
import br.com.fafic.service.TurmaDao;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "turmaMB")
@ViewScoped
public class TurmaMB implements Serializable {

    @EJB
    private TurmaDao turmaService;
    private Turma turma;
    @EJB
    private CursoDao cursoService;

    public Turma getTurma() {
        if (turma == null) {
            turma = new Turma();
        }
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    
    public List<Turma> getAll() throws Exception {
        return turmaService.getAll();
    }
    
    public List<Turma> getTurmasCurso() {
        Coordenador coordenadorSessao = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        Curso c = coordenadorSessao.getCurso();
        return c.getTurmas();
    }

    public void salvar() {
        try {
            turmaService.salvar(turma);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(TurmaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Turma!");
        }
        this.turma = null;
    }

    public void remover(Turma t) {
        try {
            turmaService.remover(t);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(TurmaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Remover Turma!");
        }
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public void vincularTurmaAoCurso(Turma t) {
        Coordenador coordenadorSessao = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        try {
            Curso curso = coordenadorSessao.getCurso();
            List<Turma> turmas = curso.getTurmas();
            turmas.add(t);
            //curso.setTurmas(turmas);
            cursoService.salvar(curso);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(TurmaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Essa Turma já está vinculado ao Curso!");
        }
    }
   
    public void salvarTurma(Turma t) {
        getExternalContext().getSessionMap().put("turma", t);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/listaProfessoresTurma.xhtml?faces-redirect=true");
    }
    
    public void redirecionar() {
        getExternalContext().getSessionMap().remove("turma");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/index.xhtml?faces-redirect=true");
    }
    
    public List<Professor> getProfessoresTurma() {
        Turma t = (Turma) getExternalContext().getSessionMap().get("turma");
        return t.getProfessores();
    }
    
    public List<SelectItem> listItensTurmas() throws Exception {
        List<SelectItem> turmas = new ArrayList<>();
        for(Turma t : getAll()) {
            turmas.add(new SelectItem(t,t.getSigla()));
        }
        return turmas;
    }
}
