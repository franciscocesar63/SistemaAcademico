/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Vestibular;
import br.com.fafic.service.VestibularDao;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "vestibularMB")
@ViewScoped
public class VestibularMB implements Serializable{
    
    @EJB
    private VestibularDao vestibularService;
    private Vestibular vestibular;

    public Vestibular getVestibular() {
        if (vestibular == null) {
            vestibular = new Vestibular();
        }
        return vestibular;
    }

    public void setVestibular(Vestibular vestibular) {
        this.vestibular = vestibular;
    }

    public List<Vestibular> getAll() throws Exception {
        return vestibularService.getAll();
    }

    public void salvarVestibular() {
        try {
            vestibularService.salvar(vestibular);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Diretor!");
        }
        this.vestibular = null;
    }

    public void remover(Vestibular v) {

        try {
            vestibularService.remover(v);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Excluir Diretor!");
        }
    }
}
