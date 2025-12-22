package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Instituciones;
import com.example.demo.model.PersonaIIEE;
import com.example.demo.model.ResponsableIIEE;
import com.example.demo.repository.reponsableiiRepositorio;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Instituciones")
public class IntitucionesControler {

      private final reponsableiiRepositorio reponsableiiRepositorio;

    public IntitucionesControler(reponsableiiRepositorio reponsableiiRepositorio){
        this.reponsableiiRepositorio = reponsableiiRepositorio;
    }

    @PostMapping( value = "/ConsultaInstituciones",consumes = {"multipart/form-data"})
    public List<Instituciones> obtenerInstituciones (){
        return reponsableiiRepositorio.obtenerInstituciones();
    }


    @PostMapping( value = "/registro_personalIIEE",consumes = {"multipart/form-data"})
    public int registro_personalIIEE (@RequestParam String Cuerpo){
         ObjectMapper mapper = new ObjectMapper();
            ResponsableIIEE usuario = mapper.readValue(Cuerpo, ResponsableIIEE.class);
            return reponsableiiRepositorio.registro_personalIIEE(usuario);
    }


    @PostMapping( value = "/consultar_personalIIEE",consumes = {"multipart/form-data"})
    public List<PersonaIIEE> Registro  (@RequestParam int codigoInstitucion){
        return reponsableiiRepositorio.consultar_personalIIEE(codigoInstitucion);
    }


}
