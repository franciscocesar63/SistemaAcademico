/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Curso;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class CursoDao extends GenericService<Curso> {

    protected CursoDao() {
        super(Curso.class);
    }

    @Override
    public void salvar(Curso curso) {
        super.salvar(curso);
    }
    
    @Override
    public Curso pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Curso curso) {
        super.remover(curso);
    }
    
    public List<Curso> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("curso.getAll", parametros);
    }
}
