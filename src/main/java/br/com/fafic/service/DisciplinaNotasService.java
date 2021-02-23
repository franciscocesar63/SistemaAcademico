/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.DisciplinaNotas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class DisciplinaNotasService extends GenericService<DisciplinaNotas> {
    protected DisciplinaNotasService(){
        super(DisciplinaNotas.class);
    }
    
    @Override
    public void salvar(DisciplinaNotas disciplinaNotas) {
        super.salvar(disciplinaNotas);
    }
    
    @Override
    public DisciplinaNotas pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(DisciplinaNotas disciplinaNotas) {
        super.remover(disciplinaNotas);
    }
    
    public List<DisciplinaNotas> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("disciplinaNotas.getAll", parametros);
    }
}
