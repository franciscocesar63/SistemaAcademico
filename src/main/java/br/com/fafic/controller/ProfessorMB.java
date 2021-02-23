/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.enums.TipoPessoa;
import br.com.fafic.model.Contato;
import br.com.fafic.model.Endereco;
import br.com.fafic.model.Professor;
import br.com.fafic.model.Turma;
import br.com.fafic.service.JavaMailService;
import br.com.fafic.service.ProfessorDao;
import br.com.fafic.service.TurmaDao;
import br.com.fafic.util.FacesMessageUtil;
import br.com.fafic.util.SenhaGeneration;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;


@ManagedBean(name = "professorMB")
@ViewScoped
public class ProfessorMB implements Serializable {

    @EJB
    private ProfessorDao professorService;
    @EJB
    private TurmaDao turmaService;
    @EJB
    private JavaMailService javaMail;
    private Professor professor;
    private Contato contato;
    private Endereco endereco;

    public Professor getProfessor() {
        if (professor == null) {
            professor = new Professor();
        }
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        this.contato = professor.getContato();
        this.endereco = professor.getEndereco();
    }

    public Contato getContato() {
        if (contato == null) {
            contato = new Contato();
        }
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Professor> getAll() throws Exception {
        return professorService.getAll();
    }

    public List<Turma> getTurmas() throws Exception {
        Professor prof = getProfessorSessao();
        return prof.getTurmas();
    }

    public void salvarProfessor() {
        try {
            professor.setTipoPessoa(TipoPessoa.PROFESSOR);
            professor.setSenha(SenhaGeneration.encryptSenha("123"));
            professor.setLogin(contato.getEmail());
            professor.setContato(contato);
            professor.setEndereco(endereco);
            professorService.salvar(professor);
            sendCredenciais();
            FacesMessageUtil.success("Professor Salvo com Sucesso! Foi enviado para"
                    + " voçê, um email com suas credenciais de Acesso!");
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Professor!");
        }
        this.professor = null;
        this.contato = null;
        this.endereco = null;
    }

    public void remover(Professor p) {

        try {
            professorService.remover(p);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Excluir Diretor!");
        }
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Professor getProfessorSessao() {
        return (Professor) getExternalContext().getSessionMap().get("vinculaProfessor");
    }

    public void redirecionar() {
        getExternalContext().getSessionMap().remove("vinculaProfessor");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/index.xhtml?faces-redirect=true");
    }

    public void salvarProfessorParaVinculo(Professor p) {
        getExternalContext().getSessionMap().put("vinculaProfessor", p);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/coordenador/vincularTurmaProfessor.xhtml?faces-redirect=true");
    }

    public void VincularTurmaProfessor(Turma t) {
        Professor prof = getProfessorSessao();
        if (t.getProfessores().contains(prof)) {
            FacesMessageUtil.fail("Essa vinculação já foi realizada Anteriormente");
        } else {
            try {
                List<Professor> professores = t.getProfessores();
                professores.add(prof);
                //System.out.println(professores.size());
                turmaService.salvar(t);
                List<Turma> turmas = prof.getTurmas();
                turmas.add(t);
                professorService.salvar(prof);
                FacesMessageUtil.success();
            } catch (Exception ex) {
                Logger.getLogger(ProfessorMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessageUtil.fail("Erro na Vinculação");
            }
        }
    }
    
    public void sendCredenciais() {
        try {
            javaMail.sendMessage(contato.getEmail(), "Credenciais", "Login: " + contato.getEmail() + "/n"
                    + "Senha: 123");
        } catch (MessagingException ex) {
            Logger.getLogger(ProfessorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
