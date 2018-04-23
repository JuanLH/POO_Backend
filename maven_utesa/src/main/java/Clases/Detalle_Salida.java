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
import utilidades.Respuesta;
import utilidades.Util;

/**
 *
 * @author JuanLH
 */
public class Detalle_Salida {
    int id_salida,referencia;
    float cantidad,costo;

    public int getId_salida() {
        return id_salida;
    }

    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
    
    public void insert(Detalle_Salida info) throws  Exception {
        Db dbase = Util.getConection();
        
        String sql = "INSERT INTO \"Detalle_Salida\"(\n" +
        "            id_salida, referencia, cantidad, costo)\n" +
        "    VALUES (?, ?, ?, ?);";
        
        PreparedStatement p;

        p = dbase.conexion.prepareStatement(sql);

        p.setInt(1,info.getId_salida());
        p.setInt(2, info.getReferencia());
        p.setFloat(3, info.getCantidad());
        p.setFloat(4, info.getCosto());

        p.execute();

        Producto pro = new Producto();
        pro.actualizar_existencia(info.getReferencia(),info.getCantidad(), 's');

        //dbase.CerrarConexion();
        
    }
}
