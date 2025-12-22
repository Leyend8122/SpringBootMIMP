package com.example.demo.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CabeceraFicha;
import com.example.demo.model.DetalleFicha;
import com.example.demo.model.GArchivos;
import com.example.demo.repository.FichaRepositorio;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/fichas")
public class FichasControler {

     @Autowired 
    private JdbcTemplate jdbcTemplate; 
    
    private final FichaRepositorio fichasRepositorio;

    public FichasControler(FichaRepositorio fichasRepositorio){
        this.fichasRepositorio = fichasRepositorio;
    }

     @PostMapping( value = "/Aperturas",consumes = {"multipart/form-data"})
    public int Apertura_Ficha (@RequestParam int codigoUsuario){
        return fichasRepositorio.Aperturar_Ficha(codigoUsuario);
    }

    @PostMapping( value = "/ConsultaCabecera",consumes = {"multipart/form-data"})
    public List<CabeceraFicha> Consulta_Cabecera (@RequestParam int codigoFicha){
        return fichasRepositorio.Consulta_Cabecera(codigoFicha);
    }

    @PostMapping( value = "/ConsultaDetalle",consumes = {"multipart/form-data"})
    public List<DetalleFicha> Consulta_Detalle (@RequestParam int codigoFicha){
        return fichasRepositorio.Consulta_Detalle(codigoFicha);
    }

     @PostMapping( value = "/GCabeceraDetalle",consumes = {"multipart/form-data"})
    public void recibirLista(@RequestParam("json") String json, @RequestParam("jsonDetalle") String jsonDetalle) throws Exception {
         ObjectMapper mapper = new ObjectMapper();
         List<CabeceraFicha> cabecera = mapper.readValue(json, new TypeReference<List<CabeceraFicha>>() {});

         List<DetalleFicha> detalle = mapper.readValue(jsonDetalle, new TypeReference<List<DetalleFicha>>() {});
        fichasRepositorio.Guardar_Cabecera(cabecera);
        fichasRepositorio.Guardar_Detalle(detalle);
    }


@PostMapping(value = "/cargaArchivo", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadMultiple(
            @RequestPart("metadata") List<GArchivos> metadataList,
            @RequestPart("files") List<MultipartFile> files) {

        String sql = "INSERT INTO pro_archivos(pdf_id, pa_nombre, pa_tipo, pa_contenido) VALUES (?,?,?,?)";

        try {
            // recorrer ambas listas en paralelo
            for (int i = 0; i < files.size(); i++) {
                GArchivos meta = metadataList.get(i);
                MultipartFile file = files.get(i);

                jdbcTemplate.update(sql,
                        meta.getPdf_id(),                // valor desde metadata
                        file.getOriginalFilename(),       // nombre del archivo
                        file.getContentType(),            // tipo MIME
                        file.getBytes());                 // contenido binario
            }

            return ResponseEntity.ok("Se guardaron " + files.size() + " documentos en la base de datos.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al procesar archivos: " + e.getMessage());
        }
    }




    



}
