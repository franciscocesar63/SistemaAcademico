/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Aluno;
import br.com.fafic.model.Disciplina;
import br.com.fafic.model.DisciplinaNotas;
import br.com.fafic.service.DisciplinaNotasService;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;


@ManagedBean(name = "notasMB")
@ViewScoped
public class NotasMB implements Serializable {

    @EJB
    private DisciplinaNotasService disciplinaNotasService;
    private DisciplinaNotas disciplinaNotas = new DisciplinaNotas();
    private Aluno alunoSelecionado = new Aluno();
    private Disciplina nomeDisciplina = new Disciplina();
    private DisciplinaNotas disAlunoSelecionado = new DisciplinaNotas();

    public DisciplinaNotasService getDisciplinaNotasService() {
        return disciplinaNotasService;
    }

    public void setDisciplinaNotasService(DisciplinaNotasService disciplinaNotasService) {
        this.disciplinaNotasService = disciplinaNotasService;
    }

    public DisciplinaNotas getDisciplinaNotas() {
        return disciplinaNotas;
    }

    public void setDisciplinaNotas(DisciplinaNotas disciplinaNotas) {
        this.disciplinaNotas = disciplinaNotas;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public DisciplinaNotas getDisAlunoSelecionado() {
        return disAlunoSelecionado;
    }

    public void setDisAlunoSelecionado(DisciplinaNotas disAlunoSelecionado) {
        this.disAlunoSelecionado = disAlunoSelecionado;
//        System.out.println(this.disAlunoSelecionado.getID());
//        System.out.println(this.disAlunoSelecionado.getAlunoMatriculado().getNome());
//        System.out.println(this.disAlunoSelecionado.getDisciplina().getNome());
//        System.out.println(this.disAlunoSelecionado.getMedia());
        System.out.println(this.disAlunoSelecionado.getSituacao());
        System.out.println(this.disAlunoSelecionado.getNota2());
        System.out.println(this.disAlunoSelecionado.getNota3());
        System.out.println(this.disAlunoSelecionado.getNota1());
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
        FacesMessageUtil.success("Aluno Selecionado com Sucesso!");
    }

    public Disciplina getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(Disciplina nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
        System.out.println(this.nomeDisciplina.getNome());
        redirecionar();
    }

    public List<DisciplinaNotas> getAll() throws Exception {
        return disciplinaNotasService.getAll();
    }

    public List<DisciplinaNotas> getDisciplinasVinculadas() throws Exception {
        List<DisciplinaNotas> disciplinasVinculadas = disciplinaNotasService.getAll();
        List<DisciplinaNotas> disciplinasSemRepeticao = new ArrayList<>();
        for (DisciplinaNotas d : disciplinasVinculadas) {
            if (!disciplinasSemRepeticao.contains(d)) {
                disciplinasSemRepeticao.add(d);
            }
        }
        return disciplinasSemRepeticao;
    }

    public void mostrarDialog() {
        PrimeFaces.current().executeScript("PF('disciplinasSelect').show();");
    }

    public void finalizar() {
        redirecionarHome();
    }

    public void redirecionarHome() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/professor/index.xhtml?faces-redirect=true");
    }

    public void redirecionar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/professor/notasAlunosByDisciplina.xhtml?faces-redirect=true");
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public void atualizarMedia() {
        double media = disAlunoSelecionado.getNota1() + disAlunoSelecionado.getNota2() + disAlunoSelecionado.getNota3();
        double mediaFinal = media/3;
        System.out.println("Média: "+mediaFinal);
        disAlunoSelecionado.setMedia(mediaFinal);
    }
    
    public void atualizarNotas() {
        atualizarMedia();
        disciplinaNotasService.salvar(disAlunoSelecionado);
    }
}
