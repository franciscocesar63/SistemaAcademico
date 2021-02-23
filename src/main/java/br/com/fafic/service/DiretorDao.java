/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Diretor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.primefaces.model.SortOrder;


@Stateless
public class DiretorDao extends GenericService<Diretor> {

    protected DiretorDao() {
        super(Diretor.class);
    }

    @Override
    public void salvar(Diretor diretor) {
        super.salvar(diretor);
    }

    @Override
    public Diretor pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }

    @Override
    public void remover(Diretor diretor) {
        super.remover(diretor);
    }

    public List<Diretor> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("diretor.getAll", parametros);
    }
    
    public List<Diretor> listLazy(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
        Query query = em.createNamedQuery(Diretor.DIRETOR_GET_ALL);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    
    public Long totalRegistros(){
         Query query = em.createNamedQuery(Diretor.DIRETOR_COUNT);
         return (Long) query.getSingleResult();
    }
}
