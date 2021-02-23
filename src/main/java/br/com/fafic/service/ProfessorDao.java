/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Professor;
import br.com.fafic.model.Turma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class ProfessorDao extends GenericService<Professor> {
    
    protected ProfessorDao() {
        super(Professor.class);
    }
    
    @Override
    public void salvar(Professor professor) {
        super.salvar(professor);
    }
    
    @Override
    public Professor pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Professor professor) {
        super.remover(professor);
    }
    
    public List<Professor> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("professor.getAll", parametros);
    }
}
