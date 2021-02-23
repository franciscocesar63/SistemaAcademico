/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author franc
 * @param <T>
 */
public class GenericService<T> {

    @PersistenceContext(unitName = "SistemaAcademico_PU")
    EntityManager em;
    private Class<T> classe;

    public GenericService(Class<T> classe) {
        this.classe = classe;
    }

    public GenericService() {
    }

    public EntityManager getEm() {
        return em;
    }

    public void salvar(T entity) {
        em.merge(entity);
    }

    public T pesquisarPorId(Long id) {
        return em.find(classe, id);
    }

    public void remover(T entity) {
        T en = em.merge(entity);
        em.remove(en);
    }

    public T getSingleResultNamedQuery(String query, Map<String, Object> parametros) {
        try {
            return (T) createNamedQuery(query, parametros).getSingleResult();
        } catch (NoResultException ne) {
            return null;
        }
    }

    public List<T> getResulListByNamedQuery(String query, Map<String, Object> parametros) {
        Query q = createNamedQuery(query, parametros);
        return q.getResultList();
    }

    private Query createNamedQuery(String query, Map<String, Object> parametros) {
        Query q = em.createNamedQuery(query);

        if (parametros != null) {
            for (Map.Entry<String, Object> param : parametros.entrySet()) {
                q.setParameter(param.getKey(), param.getValue());
            }

        }
        return q;
    }
}
