/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import db.Db;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utilidades.Respuesta;
import utilidades.Util;

/**
 *
 * @author JuanLH
 */
public class Detalle_Entrada {
    int id_entrada,referencia;
    float cantidad,costo;

    public int getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(int id_entrada) {
        this.id_entrada = id_entrada;
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
    
    public void insert(Detalle_Entrada info) throws SQLException{
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Detalle_Entrada\"(\n" +
        "            id_entrada, referencia, cantidad, costo)\n" +
        "    VALUES (?, ?, ?, ?);";
        
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setInt(1,info.getId_entrada());
            p.setInt(2, info.getReferencia());
            p.setFloat(3, info.getCantidad());
            p.setFloat(4, info.getCosto());
            p.execute();
            
            new Producto().actualizar_existencia(info.getReferencia(),info.getCantidad(), 'e');
            dbase.CerrarConexion();
            
        
        
    }
    
    
    
}
