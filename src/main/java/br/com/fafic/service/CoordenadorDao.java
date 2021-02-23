/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Coordenador;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;


@Stateless
public class CoordenadorDao extends GenericService<Coordenador> {
    
    protected CoordenadorDao() {
        super(Coordenador.class);
    }
    
    @Override
    public void salvar(Coordenador coordenador) {
        super.salvar(coordenador);
    }
    
    @Override
    public Coordenador pesquisarPorId(Long id) {
        return super.pesquisarPorId(id);
    }
    
    @Override
    public void remover(Coordenador coordenador) {
        super.remover(coordenador);
    }
    
    public List<Coordenador> getAll() throws Exception {
        Map<String, Object> parametros = new HashMap();
        return super.getResulListByNamedQuery("coordenador.getAll", parametros);
    }
}

