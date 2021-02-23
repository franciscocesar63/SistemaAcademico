/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Aluno;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class AlunoDao extends GenericService<Aluno> {
    protected AlunoDao() {
        super(Aluno.class);
    }
    
    @Override
    public void salvar(Aluno aluno) {
        super.salvar(aluno);
    }
    
    @Override
    public Aluno pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Aluno aluno) {
        super.remover(aluno);
    }
    
    public List<Aluno> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("aluno.getAll", parametros);
    }
}
