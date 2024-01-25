package com.kardec.clinica.controller;

import com.kardec.clinica.medico.DadosCadastroMedicos;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicosController {

    @PostMapping
    public void cadastrarMedicos(@RequestBody DadosCadastroMedicos dados){
        System.out.println(dados);

    }

}
