/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.enums.TipoPessoa;
import br.com.fafic.model.Contato;
import br.com.fafic.model.Diretor;
import br.com.fafic.model.Endereco;
import br.com.fafic.service.DiretorDao;
import br.com.fafic.service.JavaMailService;
import br.com.fafic.util.FacesMessageUtil;
import br.com.fafic.util.SenhaGeneration;
import br.com.fafic.ws.CepDiretor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean(name = "diretorMB")
@ViewScoped
public class DiretorMB implements Serializable {

    @EJB
    private DiretorDao diretorService;
    @EJB
    private JavaMailService javaMail;
    private Diretor diretor;
    private Contato contato;
    private Endereco endereco;
    private boolean render;
    @EJB
    private CepDiretor cepDiretor;

    private LazyDataModel<Diretor> diretorsLazy;

    @PostConstruct
    public void init() {
        diretorsLazy = new LazyDataModel<Diretor>() {
            @Override
            public List<Diretor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                setRowCount(diretorService.totalRegistros().intValue());
                return diretorService.listLazy(first, pageSize, null, null, null); //To change body of generated methods, choose Tools | Templates.
            }

        };
        this.render = false;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public LazyDataModel<Diretor> getDiretorsLazy() {
        return diretorsLazy;
    }

    public void setDiretorsLazy(LazyDataModel<Diretor> diretorsLazy) {
        this.diretorsLazy = diretorsLazy;
    }

    public Diretor getDiretor() {
        if (diretor == null) {
            diretor = new Diretor();
        }
        return diretor;
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

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
        this.contato = diretor.getContato();
        this.endereco = diretor.getEndereco();
        this.render = true;
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

    public List<Diretor> getAll() throws Exception {
        return diretorService.getAll();
    }

    public void salvarCliente() {
        try {
            diretor.setTipoPessoa(TipoPessoa.DIRETOR);
            diretor.setSenha(SenhaGeneration.encryptSenha("123"));
            diretor.setLogin(contato.getEmail());
            diretor.setContato(contato);
            diretor.setEndereco(endereco);
            diretorService.salvar(diretor);
            sendCredenciais();
            FacesMessageUtil.success("Diretor Salvo com Sucesso! Foi enviado para"
                    + " você, com suas credenciais de Acesso!");
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Diretor!");
        }
        this.diretor = null;
        this.contato = null;
        this.endereco = null;
        this.render = false;
    }

    public void remover(Diretor d) {

        try {
            diretorService.remover(d);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Excluir Diretor!");
        }
    }

    public void buscarEndereco() {
        diretor.setEndereco(cepDiretor.buscarEnderecoCep(diretor.getEndereco().getCep()));
    }

    public void sendCredenciais() {
        try {
            javaMail.sendMessage(contato.getEmail(), "Senha de acesso do " + diretor.getTipoPessoa(), "Login: " + contato.getEmail() + "<br/>"
                    + "Senha: 123");
        } catch (MessagingException ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
