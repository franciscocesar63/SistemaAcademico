/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.convert;

import br.com.fafic.model.Aluno;
import br.com.fafic.service.AlunoDao;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "alunoConvert")
public class AlunoConvert implements Converter {
    
    @EJB
    AlunoDao alunoService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Aluno aluno = new Aluno();
        if (value != null && value.trim().length() > 0) {
            aluno = alunoService.pesquisarPorId(Long.valueOf(value));
            return aluno;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null && !value.equals("")) {
            Aluno a = (Aluno) value;
            return a.getId().toString();
        }
        return null;
    }
}
