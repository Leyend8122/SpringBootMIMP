package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CUsuarioModel;
import com.example.demo.model.UsuarioModel;

@Repository
public class UsuarioRepositorio {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositorio(JdbcTemplate jdbcTemplate){
            this.jdbcTemplate = jdbcTemplate;
    }

    public List<UsuarioModel> validacion_usuario(CUsuarioModel cusuariomodel){
            return jdbcTemplate.query(
                    "CALL validacion_usuario(?, ?)",
                    new Object[]{cusuariomodel.getUsuario(), cusuariomodel.getContrasena()},
                    (rs, rowNum) -> {
                        UsuarioModel usuario = new UsuarioModel();
                        usuario.setCodUsuario(rs.getInt("codigo_personal"));
                        usuario.setCodTipoProfesional(rs.getInt("codigo_tipo_profesional"));
                        usuario.setDefTipProfesional(rs.getString("tipo_definicion"));
                        usuario.setDefNombreProfesional(rs.getString("nombre_completo"));
                        usuario.setDefEstadoProfesional(rs.getInt("estado_profesional"));
                        // agrega más campos según tu procedimiento
                        return usuario;
                    }
                );

    }
    
}
