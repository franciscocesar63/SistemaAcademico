/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Disciplina;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class DisciplinaDao extends GenericService<Disciplina> {
    
    protected DisciplinaDao() {
        super(Disciplina.class);
    }
    
    @Override
    public void salvar(Disciplina disciplina) {
        super.salvar(disciplina);
    }
    
    @Override
    public Disciplina pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Disciplina disciplina) {
        super.remover(disciplina);
    }
    
    public List<Disciplina> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("disciplina.getAll", parametros);
    }
}
