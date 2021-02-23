/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Matricula;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class MatriculaDao extends GenericService<Matricula>{
    
    protected MatriculaDao(){
        super(Matricula.class);
    }
    
    @Override
    public void salvar(Matricula matricula) {
        super.salvar(matricula);
    }
    
    @Override
    public Matricula pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Matricula matricula) {
        super.remover(matricula);
    }
    
    public List<Matricula> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("matricula.getAll", parametros);
    }
}
