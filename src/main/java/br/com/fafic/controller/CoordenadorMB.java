/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.enums.TipoPessoa;
import br.com.fafic.model.Contato;
import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Curso;
import br.com.fafic.model.Endereco;
import br.com.fafic.service.CoordenadorDao;
import br.com.fafic.service.JavaMailService;
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



@ManagedBean(name = "coordenadorMB")
@ViewScoped
public class CoordenadorMB implements Serializable {

    @EJB
    private CoordenadorDao coordenadorService;
    @EJB
    private JavaMailService javaMail;
    private Coordenador coordenador;
    private Contato contato;
    private Endereco endereco;

    public Coordenador getCoordenador() {
        if (coordenador == null) {
            coordenador = new Coordenador();
        }
        return coordenador;
    }

    public void setCoordenador(Coordenador coordenador) {
        this.coordenador = coordenador;
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

    public List<Coordenador> getAll() throws Exception {
        return coordenadorService.getAll();
    }

    public void salvarCoordenador() {
        try {
            coordenador.setTipoPessoa(TipoPessoa.COODERNADOR);
            coordenador.setSenha(SenhaGeneration.encryptSenha("123"));
            coordenador.setLogin(contato.getEmail());
            coordenador.setContato(contato);
            coordenador.setEndereco(endereco);
            coordenadorService.salvar(coordenador);
            sendCredenciais();
            FacesMessageUtil.success("Coordenador Salvo com Sucesso! Foi enviado para"
                    + " voçê, com suas credenciais de Acesso!");
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Diretor!");
        }
        this.coordenador = null;
        this.contato = null;
        this.endereco = null;
    }

    public void remover(Coordenador c) {

        try {
            coordenadorService.remover(c);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Excluir Diretor!");
        }
    }

    public void salvarCoordenadorParaVinculo(Coordenador c) {
        getExternalContext().getSessionMap().put("vinculaCoordenador", c);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/diretor/vincular.xhtml?faces-redirect=true");
    }

    public void redirecionar() {
        getExternalContext().getSessionMap().remove("vinculaCoordenador");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/diretor/index.xhtml?faces-redirect=true");
    }

    public Coordenador getCoordenadorSalvo() {
        return (Coordenador) getExternalContext().getSessionMap().get("vinculaCoordenador");
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public void vincularCurso(Curso c) {
        Coordenador coordenadorSalvo = getCoordenadorSalvo();
        if (coordenadorSalvo.getCurso() == null) {
            coordenadorSalvo.setCurso(c);
            coordenadorService.salvar(coordenadorSalvo);
            FacesMessageUtil.success();
        } else {
            FacesMessageUtil.fail("Esse Coordenador Já está Vinculado a um Curso!");
        }
    }

    public void sendCredenciais() {
        try {
            javaMail.sendMessage(contato.getEmail(), "Credenciais", "Login: " + contato.getEmail() + "/n"
                    + "Senha: 123");
        } catch (MessagingException ex) {
            Logger.getLogger(CoordenadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
