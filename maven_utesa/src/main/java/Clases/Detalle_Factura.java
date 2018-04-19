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
public class Detalle_Factura {
    int id_factura,referencia;
    float cantidad,precio,costo,tax;

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }
    
    public void insert() throws SQLException{
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Detalle_Factura\"(\n" +
        "            id_factura, referencia, cantidad, precio, costo, tax)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?);";
        
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setInt(1,new Factura().getLastId());
            p.setInt(2, this.getReferencia());
            p.setFloat(3, this.getCantidad());
            p.setFloat(4, this.getPrecio());
            p.setFloat(5, this.getCosto());
            p.setFloat(6, this.getTax());
            p.execute();
            
            /*Actualiza Inventario*/
            new Producto().actualizar_existencia(this.getReferencia(),this.getCantidad(), 's');
            dbase.CerrarConexion();
    }
}
