/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.convert;

import br.com.fafic.model.Turma;
import br.com.fafic.service.TurmaDao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "turmaConvert")
public class turmaConvert implements Converter,Serializable {

    @EJB
    TurmaDao turmaService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        Turma t = new Turma();
        if (value != null && value.trim().length() > 0) {
            t = turmaService.pesquisarPorId(Long.valueOf(value));
            return t;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null && !value.equals("")) {
            Turma t = (Turma) value;
            return t.getId().toString();
        }
        return null;
    }

}
