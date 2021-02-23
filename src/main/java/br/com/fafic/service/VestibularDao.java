/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Vestibular;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class VestibularDao extends GenericService<Vestibular>{
    
    protected VestibularDao() {
        super(Vestibular.class);
    }
    
    @Override
    public void salvar(Vestibular vestibular) {
        super.salvar(vestibular);
    }
    
    @Override
    public Vestibular pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Vestibular vestibular) {
        super.remover(vestibular);
    }
    
    public List<Vestibular> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("vestibular.getAll", parametros);
    }
    
}
