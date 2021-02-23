/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Aluno;
import br.com.fafic.model.Disciplina;
import br.com.fafic.model.DisciplinaNotas;
import br.com.fafic.service.DisciplinaNotasService;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;


@ManagedBean(name = "alunoDisciplinaMB")
@SessionScoped
public class AlunoDisciplinaMB implements Serializable {

    @EJB
    private DisciplinaNotasService disciplinaNotasService;
    private DisciplinaNotas disciplinaNotas = new DisciplinaNotas();
    private Aluno alunoSelecionado;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private DisciplinaNotas alunoNota;
    private String disciplinaSelect = "";

    public DisciplinaNotasService getDisciplinaNotasService() {
        return disciplinaNotasService;
    }

    public void setDisciplinaNotasService(DisciplinaNotasService disciplinaNotasService) {
        this.disciplinaNotasService = disciplinaNotasService;
    }

    public DisciplinaNotas getDisciplinaNotas() {
        return disciplinaNotas;
    }

    public void setDisciplinaNotas(DisciplinaNotas disciplinaNotas) {
        this.disciplinaNotas = disciplinaNotas;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public DisciplinaNotas getAlunoNota() {
        if (alunoNota == null) {
            alunoNota = new DisciplinaNotas();
        }
        return alunoNota;
    }

    public void setAlunoNota(DisciplinaNotas alunoNota) {
        this.alunoNota = alunoNota;
        mostrarDialogNotas();
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
        FacesMessageUtil.success("Aluno Selecionado com Sucesso!");
    }

    public String getDisciplinaSelect() {
        return disciplinaSelect;
    }

    public void setDisciplinaSelect(String disciplinaSelect) {
        this.disciplinaSelect = disciplinaSelect;
        System.out.println(disciplinaSelect);
    }

    public List<DisciplinaNotas> getAll() throws Exception {
        return disciplinaNotasService.getAll();
    }

    public List<DisciplinaNotas> getDisciplinasVinculadas() throws Exception {
        List<DisciplinaNotas> disciplinasVinculadas = disciplinaNotasService.getAll();
        List<DisciplinaNotas> disciplinasSemRepeticao = new ArrayList<>();
        for (DisciplinaNotas d : disciplinasVinculadas) {
            if (!disciplinasSemRepeticao.contains(d)) {
                disciplinasSemRepeticao.add(d);
            }
        }
        return disciplinasSemRepeticao;
    }
    
    public List<DisciplinaNotas> getDisciplinasVinculadasRepeticao() throws Exception {
        List<DisciplinaNotas> disciplinasVinculadas = disciplinaNotasService.getAll();
        List<DisciplinaNotas> disciplinasSemRepeticao = new ArrayList<>();
        for (DisciplinaNotas d : disciplinasVinculadas) {
            //if (!disciplinasSemRepeticao.contains(d)) {
                disciplinasSemRepeticao.add(d);
            //}
        }
        return disciplinasSemRepeticao;
    }

    public List<DisciplinaNotas> getAlunoByDisciplina() throws Exception {
        List<DisciplinaNotas> alunos = new ArrayList<>();
        for (DisciplinaNotas d : getAll()) {
            if (d.getDisciplina().getNome().equals(disciplinaSelect)) {
                alunos.add(d);
            }
        }
        return alunos;
    }

    public void addListDisciplina(Disciplina d) {
        if (disciplinas.contains(d)) {
            FacesMessageUtil.fail("Essa disciplina já foi Selecionada!");
        } else {
            disciplinas.add(d);
            FacesMessageUtil.success("Selecionado com Sucesso!");
        }
    }

    public void mostrarDialog() {
        PrimeFaces.current().executeScript("PF('disciplinasSelect').show();");
    }

    public void mostrarDialogNotas() {
        PrimeFaces.current().executeScript("PF('formNotas').show();");
    }

    public void vincularAlunoDisciplina() {
        if (alunoSelecionado != null && disciplinas.size() > 0) {
            for (Disciplina d : disciplinas) {
                disciplinaNotas.setDisciplina(d);
                disciplinaNotas.setAlunoMatriculado(alunoSelecionado);
                disciplinaNotas.setSituacao("Reprovado");
                disciplinaNotasService.salvar(disciplinaNotas);
            }
            FacesMessageUtil.success();
        } else {
            FacesMessageUtil.fail("Necessária a seleção das disciplinas e do aluno");
        }
        this.disciplinas.clear();
        this.disciplinaNotas = new DisciplinaNotas();
    }

    public void salvarAlteracao() {
        atualizarMedia();
        disciplinaNotasService.salvar(alunoNota);
        FacesMessageUtil.success();
    }

    public void atualizarMedia() {
        double somaNotas = alunoNota.getNota1() + alunoNota.getNota2() + alunoNota.getNota3();
        double media = somaNotas / 3;
        atualizaSituacao(media);
        alunoNota.setMedia(media);
    }

    public void atualizaSituacao(double media) {
        if (media >= 7.0) {
            alunoNota.setSituacao("Aprovado");
        } else if (media < 7 && media > 4) {
            alunoNota.setSituacao("Recuperação");
        } else {
            alunoNota.setSituacao("Reprovado");
        }
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Aluno getAlunoLogado() {
        return (Aluno) getExternalContext().getSessionMap().get("pessoa");
    }

    public List<DisciplinaNotas> getNotasByAluno() throws Exception {
        System.out.println(getAlunoLogado().getNome());
        List<DisciplinaNotas> listNotas = new ArrayList<>();
        for (DisciplinaNotas d : getAll()) {
            if (d.getAlunoMatriculado().getNome().equals(getAlunoLogado().getNome())) {
                listNotas.add(d);
            }
        }
        return listNotas;
    }
    
    public int qtdAlunosReprovados() throws Exception {
        int contador = 0;
        for (DisciplinaNotas d : getAll()) {
            if (d.getSituacao().equalsIgnoreCase("reprovado")) {
                contador++;
            }
        }
        return contador;
    }
    
    public int qtdAlunosAprovados() throws Exception {
        int contador = 0;
        for (DisciplinaNotas d : getAll()) {
            if (d.getSituacao().equalsIgnoreCase("aprovado")) {
                contador++;
            }
        }
        return contador;
    }
}
