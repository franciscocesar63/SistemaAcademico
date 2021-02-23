/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.util;

import javax.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;



public class FacesMessageUtil {

    public static void success() {
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", "Operação realizada com sucesso!"), true);
    }

    public static void fail(String dynamicMessage) {
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensagem", dynamicMessage), true);
    }

    public static void success(String msg) {
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem", msg), true);
    }
    
    public static void SenhasDiferentes() {
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensagem", "As senhas informadas são Diferentes!"), true);
    }
    
    public static void SenhaPadrao() {
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensagem", "A senha não pode ser igual a Anterior"), true);
    }
}
