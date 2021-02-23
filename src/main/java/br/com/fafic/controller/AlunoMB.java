/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.enums.TipoPessoa;
import br.com.fafic.model.Aluno;
import br.com.fafic.model.Contato;
import br.com.fafic.model.Endereco;
import br.com.fafic.service.AlunoDao;
import br.com.fafic.service.JavaMailService;
import br.com.fafic.util.FacesMessageUtil;
import br.com.fafic.util.SenhaGeneration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;




@ManagedBean(name = "alunoMB")
@ViewScoped
public class AlunoMB implements Serializable {

    @EJB
    private AlunoDao alunoService;
    @EJB
    private JavaMailService javaMail;
    private Aluno aluno;
    private Contato contato;
    private Endereco endereco;
    @ManagedProperty(value = "#{loginMB}")
    private LoginController loginMB;
    private StreamedContent fileDownload;

    public Aluno getAluno() {
        if (aluno == null) {
            aluno = new Aluno();
        }
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        this.contato = aluno.getContato();
        this.endereco = aluno.getEndereco();
    }

    public Contato getContato() {
        if (contato == null) {
            contato = new Contato();
        }
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Aluno> getAll() throws Exception {
        return alunoService.getAll();
    }

    public LoginController getLoginMB() {
        return loginMB;
    }

    public void setLoginMB(LoginController loginMB) {
        this.loginMB = loginMB;
    }

    public StreamedContent getFileDownload() throws FileNotFoundException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String newFileName = "C:\\pessoal\\projetos java\\ProjetoSistemaAcademico\\target\\"
                    + "ProjetoSistemaAcademico-1.0-SNAPSHOT\\resources\\arquivos\\";

        String contentType = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getMimeType(newFileName);

        return new DefaultStreamedContent(new FileInputStream(newFileName + aluno.getPathFile()), contentType, aluno.getPathFile());

    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public void salvarAluno() {
        try {
            aluno.setSenha(SenhaGeneration.encryptSenha("123"));
            aluno.setLogin(contato.getEmail());
            aluno.setTipoPessoa(TipoPessoa.ALUNO);
            aluno.setContato(contato);
            aluno.setEndereco(endereco);
            alunoService.salvar(aluno);
            sendCredenciais();
            FacesMessageUtil.success("Aluno Salvo com Sucesso! Foi enviado para"
                    + " voçê, com suas credenciais de Acesso!");
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Cadastrar Aluno!");
        }
        this.aluno = null;
        this.contato = null;
        this.endereco = null;
    }

    public void remover(Aluno a) {

        try {
            alunoService.remover(a);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(DiretorMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Excluir Aluno!");
        }
    }

    public void sendCredenciais() {
        try {
            javaMail.sendMessage(contato.getEmail(), "Credenciais", "Login: " + contato.getEmail() + "/n"
                    + "Senha: 123");
        } catch (MessagingException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<SelectItem> listItensAlunos() throws Exception {
        List<SelectItem> alunos = new ArrayList<>();
        for (Aluno a : getAll()) {
            alunos.add(new SelectItem(a, a.getId().toString()));
        }
        return alunos;
    }

    public void fileUpload(FileUploadEvent event) {
        try {
            String nomeArquivo = String.valueOf(System.currentTimeMillis());
            UploadedFile recebido = event.getFile();
            InputStream inputStream = recebido.getInputstream();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String newFileName = "C:\\pessoal\\projetos java\\ProjetoSistemaAcademico\\target\\"
                    + "ProjetoSistemaAcademico-1.0-SNAPSHOT\\resources\\arquivos\\" + nomeArquivo + ".pdf";
            OutputStream out = new FileOutputStream(newFileName);
            byte buff[] = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                out.write(buff, 0, length);
            }
            inputStream.close();
            out.close();

            Aluno alunoLogado = (Aluno) loginMB.gePessoaLogaca();
            alunoLogado.setPathFile(nomeArquivo + ".pdf");
            alunoService.salvar(alunoLogado);
            FacesMessageUtil.success();
        } catch (IOException ex) {
            Logger.getLogger(AlunoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
