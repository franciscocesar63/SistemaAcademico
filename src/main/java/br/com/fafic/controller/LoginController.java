/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Aluno;
import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Pessoa;
import br.com.fafic.model.Professor;
import br.com.fafic.model.Turma;
import br.com.fafic.service.LoginService;
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
import org.primefaces.PrimeFaces;



@ManagedBean(name = "loginMB")
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private LoginService loginService;
    private String login;
    private String senha;
    private Pessoa pessoa;
    private String confirmaSenha;
    private Pessoa pessoaSelecionada;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public List<Pessoa> getAll() throws Exception {
        return loginService.getAll();
    }

    public void logar() {
        try {
            String paginaDestino = "index.xhtml";
            StringBuilder builder = new StringBuilder();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            pessoa = loginService.getPessoaByLogin(login, SenhaGeneration.encryptSenha(senha));

            if (pessoa != null) {

                getExternalContext().getSessionMap().put("pessoa", pessoa);
                if (new String(SenhaGeneration.decryptSenha(pessoa.getSenha())).equals("123")) {
                    //mostrar dialog ao usuário
                    PrimeFaces.current().executeScript("PF('dialogRedSenha').show();");

                } else {

                    paginaDestino = "";
                    paginaDestino = builder.append("/")
                            .append(pessoa.getTipoPessoa().getTipo()
                                    .toLowerCase()).append("/index.xhtml?faces-redirect=true").toString();
                }
            } else {
                FacesMessageUtil.fail("Login ou Senha Incorretos!");
            }

            NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
            navigation.handleNavigation(facesContext, null, paginaDestino);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Pessoa gePessoaLogaca() {
        return (Pessoa) getExternalContext().getSessionMap().get("pessoa");
    }
    
    public Coordenador gePessoaLogada() {
        return (Coordenador) getExternalContext().getSessionMap().get("pessoa");
    }

    public List<Turma> getProfessorLogado() {
        Professor prof = (Professor) getExternalContext().getSessionMap().get("pessoa");
        return prof.getTurmas();
    }

    public void logout() {
        getExternalContext().getSessionMap().remove("pessoa");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigation = facesContext.getApplication().getNavigationHandler();
        navigation.handleNavigation(facesContext, null, "/index.xhtml?faces-redirect=true");
    }

    public boolean senhaEquals() {
        return this.senha.equals(this.confirmaSenha);
    }

    public boolean senhaPadrao() {
        return this.senha.equals("123");
    }

    public void updateSenha() {
        if (!senhaEquals()) {
            this.senha = "";
            this.confirmaSenha = "";
            FacesMessageUtil.SenhasDiferentes();
        } else if (senhaPadrao()) {
            this.senha = "";
            this.confirmaSenha = "";
            FacesMessageUtil.SenhaPadrao();
        } else {
            pessoa = this.gePessoaLogaca();
            pessoa.setSenha(SenhaGeneration.encryptSenha(senha));
            loginService.salvar(pessoa);
            // fechando o dialog
            PrimeFaces.current().executeScript("PF('dialogRedSenha').hide();");
            FacesMessageUtil.success();
        }
    }

    public Aluno getAlunoLogado() {
        return (Aluno) getExternalContext().getSessionMap().get("pessoa");
    }
}
