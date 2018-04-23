/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            //dbase.CerrarConexion();
    }
    
    public Respuesta get_detalle_factura(int id_factura){
        Respuesta resp = new Respuesta();
        Db dbase = Util.getConection();
        ArrayList<Detalle_Factura> lista = new ArrayList();
        String sql = "SELECT id_factura, referencia, cantidad, precio, costo, tax\n" +
        "  FROM \"Detalle_Factura\" where id_factura = "+id_factura+";";
        try{
            ResultSet rs = dbase.execSelect(sql);
            
            while(rs.next()){
                Detalle_Factura dt = new Detalle_Factura();
                dt.setId_factura(rs.getInt(1));
                dt.setReferencia(rs.getInt(2));
                dt.setCantidad(rs.getFloat(3));
                dt.setPrecio(rs.getFloat(4));
                dt.setCosto(rs.getFloat(5));
                dt.setTax(rs.getFloat(6));
                
                lista.add(dt);
            }
            
            if(lista.isEmpty())
            {
                resp.setId(0);
                resp.setMensaje("No hay registros actualmente en la base de datos");
                dbase.CerrarConexion();
                return resp;
            }
        }
        catch(SQLException e){
            resp.setId(-1);
            resp.setMensaje("Error de la base de datos ");
            System.out.println(e.getMessage());
            dbase.CerrarConexion();
            return resp;
        }
        resp.setId(1);
        resp.setMensaje(resp.ToJson(lista));//convierto la lista a Gson
        dbase.CerrarConexion();
        return resp; 
        
    }
}
