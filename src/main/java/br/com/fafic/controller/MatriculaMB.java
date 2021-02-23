/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.model.Aluno;
import br.com.fafic.model.Coordenador;
import br.com.fafic.model.Curso;
import br.com.fafic.model.Matricula;
import br.com.fafic.model.Turma;
import br.com.fafic.service.MatriculaDao;
import br.com.fafic.util.FacesMessageUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "matriculaMB")
@ViewScoped
public class MatriculaMB implements Serializable {

    @EJB
    private MatriculaDao matriculaService;
    private Matricula matricula = new Matricula();
    private List<Matricula> matriculas;
    private String filtro;
    private Turma turmaSelecionada = new Turma();
    private Turma turmaSelecionadaQtdAlunos = new Turma();

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Matricula getMatricula() {
        if (matricula == null) {
            matricula = new Matricula();
        }
        return matricula;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Turma getTurmaSelecionada() {
        return turmaSelecionada;
    }

    public void setTurmaSelecionada(Turma turmaSelecionada) {
        this.turmaSelecionada = turmaSelecionada;
        FacesMessageUtil.success("Selecionado com Sucesso!");
    }

    public Turma getTurmaSelecionadaQtdAlunos() {
        return turmaSelecionadaQtdAlunos;
    }

    public void setTurmaSelecionadaQtdAlunos(Turma turmaSelecionadaQtdAlunos) throws Exception {
        this.turmaSelecionadaQtdAlunos = turmaSelecionadaQtdAlunos;
        FacesMessageUtil.success("Quantidade de Alunos: " + getQtdAlunoTurma());
    }

    public int getQtdAlunoTurma() throws Exception {
        int contador = 0;
        for (Matricula m : getAll()) {
            if (m.getTurma().getId().equals(this.turmaSelecionadaQtdAlunos.getId())) {
                contador++;
            }
        }
        return contador;
    }

    @PostConstruct
    public void init() {
        try {
            matriculas = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(MatriculaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<Matricula> getAll() throws Exception {
        return matriculaService.getAll();
    }

    public void salvar() {
        try {
            StringBuilder codigoMatricula = new StringBuilder();
            Random gerador = new Random();
            matricula.setAtivada(true);
            Calendar dataAtual = Calendar.getInstance();
            int ano = dataAtual.get(Calendar.YEAR);
            int mes = dataAtual.get(Calendar.MONTH) + 1;
            codigoMatricula.append(String.valueOf(ano));
            if (mes <= 6) {
                codigoMatricula.append("1");
            } else {
                codigoMatricula.append("2");
            }
            codigoMatricula.append(String.format("%05d", matricula.getAluno().getId()));
            matricula.setData(getDataAtual());
            matricula.setMatricula(codigoMatricula.toString());
            matricula.setCurso(getCurso());
            matriculaService.salvar(matricula);
            FacesMessageUtil.success();
        } catch (ParseException ex) {
            Logger.getLogger(MatriculaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Matricular Aluno!");
        }
        this.matricula = null;
        this.matricula = new Matricula();
    }

    public void desativarMatricula(Matricula matriculaAluno) {
        try {
            matriculaAluno.setAtivada(!matriculaAluno.isAtivada());
            matriculaService.salvar(matriculaAluno);
            FacesMessageUtil.success();
        } catch (Exception ex) {
            Logger.getLogger(MatriculaMB.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessageUtil.fail("Erro ao Desativar Matrícula!");
        }
    }

    public void filtrarLista() throws Exception {
        if (filtro.equals("true")) {
            matriculas = getMatriculasAtivas();
        } else if (filtro.equals("false")) {
            matriculas = getMatriculasInativas();
        }
    }

    public void setAluno(Aluno aluno) {
        matricula.setAluno(aluno);
        FacesMessageUtil.success("Aluno Selecionado com Sucesso!");
    }

    public void setTurma(Turma turma) {
        matricula.setTurma(turma);
        FacesMessageUtil.success("Turma Selecionada com Sucesso!");
    }

    public Date getDataAtual() throws ParseException {
        Date dataHoje = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat = format.format(dataHoje);
        Date dataFormatada = format.parse(dataFormat);
        return dataFormatada;
    }

    public List<Matricula> getMatriculasAtivas() throws Exception {
        List<Matricula> ativas = new ArrayList<>();
        for (Matricula m : getAll()) {
            if (m.isAtivada()) {
                ativas.add(m);
            }
        }
        return ativas;
    }

    public List<Matricula> getMatriculasInativas() throws Exception {
        List<Matricula> inativas = new ArrayList<>();
        for (Matricula m : getAll()) {
            if (!m.isAtivada()) {
                inativas.add(m);
            }
        }
        return inativas;
    }

    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Curso getCurso() {
        Coordenador coor = (Coordenador) getExternalContext().getSessionMap().get("pessoa");
        return coor.getCurso();
    }

    public List<Aluno> getAlunosCurso() throws Exception {
        List<Aluno> alunos = new ArrayList<>();
        for (Matricula m : getAll()) {
            if (m.getCurso().getId().equals(getCurso().getId())) {
                alunos.add(m.getAluno());
            }
        }
        return alunos;
    }

    public List<Turma> getTurmas() throws Exception {
        List<Matricula> listMatriculas = getAll();
        List<Turma> turmas = new ArrayList<>();
        for (Matricula m : listMatriculas) {
            if (!turmas.contains(m.getTurma())) {
                turmas.add(m.getTurma());
            }
        }
        return turmas;
    }

    public List<Matricula> getMaticulaByTurma() throws Exception {
        List<Matricula> listMatriculas = new ArrayList<>();
        for (Matricula m : getAll()) {
            if (m.getTurma().getPeriodo() == null ? this.turmaSelecionada.getPeriodo() == null : m.getTurma().getPeriodo().equals(this.turmaSelecionada.getPeriodo())) {
                listMatriculas.add(m);
            }
        }
        return listMatriculas;
    }

    public int qtdAlunosCurso() throws Exception {
        int contator = 0;
        for (Matricula m : getAll()) {
            if (m.getCurso().getId().equals(getCurso().getId())) {
                contator++;
            }
        }
        return contator;
    }

    public void mostrarDialog() {
        PrimeFaces.current().executeScript("PF('listAlunosTurma').show();");
    }
}
