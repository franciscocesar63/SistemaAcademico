/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Turma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class TurmaDao extends GenericService<Turma> {
    
    protected TurmaDao() {
        super(Turma.class);
    }
    
    @Override
    public void salvar(Turma turma) {
        super.salvar(turma);
    }
    
    @Override
    public Turma pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Turma turma) {
        super.remover(turma);
    }
    
    public List<Turma> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("turma.getAll", parametros);
    }
    
}
