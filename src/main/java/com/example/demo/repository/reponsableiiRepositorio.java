package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Instituciones;
import com.example.demo.model.PersonaIIEE;
import com.example.demo.model.ResponsableIIEE;

@Repository
public class reponsableiiRepositorio {
    private final JdbcTemplate jdbcTemplate;

    public reponsableiiRepositorio(JdbcTemplate jdbcTemplate){
            this.jdbcTemplate = jdbcTemplate;
    }

    public List<Instituciones> obtenerInstituciones() {
    String sql = "select aie.pie_id,aie.pie_definicion,ub.ub_nombdep,ub.ub_nombprov,ub.ub_nombdist from pro_institucionEducativa  aie\n" + //
                "left join UBIGEO ub on aie.pie_ubigeo = ub.ub_id;";
    return jdbcTemplate.query(sql, (rs, rowNum) -> {
        Instituciones u = new Instituciones();
        u.setPie_id(rs.getInt("pie_id"));
        u.setPie_definicion(rs.getString("pie_definicion"));
        u.setUb_nombdep(rs.getString("ub_nombdep"));
        u.setUb_nombprov(rs.getString("ub_nombprov"));
        u.setUb_nombdist(rs.getString("ub_nombdist"));
        return u;
    });
}


public int registro_personalIIEE(ResponsableIIEE responsableIIEE){
            return jdbcTemplate.queryForObject(
                    "CALL registro_personalIIEE(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{responsableIIEE.getP_nombres_(), responsableIIEE.getP_primer_apellido_(),
                        responsableIIEE.getP_segundo_apellido_(),responsableIIEE.getP_sexo_(),
                        responsableIIEE.getP_fecha_nacimiento_(),
                        responsableIIEE.getP_telefono_1_(),responsableIIEE.getP_telefono_2_(),
                        responsableIIEE.getP_correo_(),responsableIIEE.getP_direccion_(),
                        responsableIIEE.getP_tipo_doc_(),responsableIIEE.getP_nrdocumento_(),
                        responsableIIEE.getPie_id_(),
                        responsableIIEE.getCodigoRegistra_()
                    },Integer.class
                );
    }


    public List<PersonaIIEE> consultar_personalIIEE(Integer pie_id){
            return jdbcTemplate.query(
                    "CALL consultar_personalIIEE(?)",
                     new Object[]{pie_id},
                    (rs, rowNum) -> {
                        PersonaIIEE cabecera = new PersonaIIEE();
                        cabecera.setCodigo(rs.getObject("pr_id",Integer.class));
                        cabecera.setDescripcion(rs.getString("nombres_completos"));
                        return cabecera;
                    }
                );
    }

}
