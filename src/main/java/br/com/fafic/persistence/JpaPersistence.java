/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;



public class JpaPersistence {

    private final EntityManager em = Persistence.createEntityManagerFactory("AgendaWeb_PU").createEntityManager();

    public EntityManager getEm() {
        return em;
    }
}
