/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.service;

import br.com.fafic.model.Pessoa;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;



@Stateless
public class LoginService extends GenericService<Pessoa> {

    private Map<String, Object> parametros;

    public Pessoa getPessoaByLogin(String login, String senha) throws Exception {
        parametros = new HashMap<>();
        parametros.put("login", login);
        parametros.put("senha", senha);
        return getSingleResultNamedQuery(Pessoa.PESSOA_BY_LOGIN, parametros);
    }

    public List<Pessoa> getAll() throws Exception {
        Map<String, Object> parametrosConsulta = new HashMap();
        return super.getResulListByNamedQuery("pessoaGetAll", parametrosConsulta);
    }

    public List<Pessoa> getTipoPessoa(String tipoPessoa) throws Exception {
        Map<String, Object> parametrosTipo = new HashMap();
        parametrosTipo.put("tipoPessoa", tipoPessoa);
        return super.getResulListByNamedQuery("pessoaGetTipo", parametrosTipo);
    }
}
