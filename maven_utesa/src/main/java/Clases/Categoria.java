/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import db.Db;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanLH
 */
public class Categoria {
    int id;
    String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String insertar_categoria(){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql="INSERT INTO \"Categoria\"(id, descripcion)\n" +
        "    VALUES (?, ?);";
        try {
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setInt(1, id);
            p.setString(2, descripcion);
            p.execute();
            dbase.CerrarConexion();
            r.setId(1);
            r.setMensaje("Se inserto correctamente");
            return Respuesta.ToJson(r);
        } catch (SQLException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            r.setId(-1);
            r.setMensaje("Error en la base de datos");
            return Respuesta.ToJson(r);
            
        }
    }
}
