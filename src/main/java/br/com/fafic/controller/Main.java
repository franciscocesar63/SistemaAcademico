/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fafic.controller;

import br.com.fafic.util.SenhaGeneration;


public class Main {
    public static void main(String[] args) {
        System.out.println(SenhaGeneration.encryptSenha("admin"));
    }
}
