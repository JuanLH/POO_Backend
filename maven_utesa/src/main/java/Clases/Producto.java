    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import utilidades.Respuesta;
import utilidades.Util;
import com.google.gson.Gson;
import db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JuanLH
 */
public class Producto {
    String descripcion;
    int id_categoria,referencia;
    float precio,costo,existencia,tax;

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
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

    public float getExistencia() {
        return existencia;
    }

    public void setExistencia(float existencia) {
        this.existencia = existencia;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }
    
    public String insertar_producto(Producto info){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Producto\"( descripcion,"
                + " id_categoria, precio, costo, existencia, tax)"
                + "VALUES ( ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            
            
            p.setString(1,info.getDescripcion());
            p.setInt(2, info.getId_categoria());
            p.setFloat(3, info.getPrecio());
            p.setFloat(4, info.getCosto());
            p.setFloat(5, info.getExistencia());
            p.setFloat(6, info.getTax());
            p.execute();
            dbase.CerrarConexion();
            r.setId(1);
            r.setMensaje("Se inserto correctamiente");
            return Respuesta.ToJson(r);
        }
        catch(SQLException e){
            r.setId(-1);
            r.setMensaje("Error en la base de datos");
            System.err.println(e.getMessage());
            return Respuesta.ToJson(r);
        }
                
    }
    
    public void actualizar_existencia(int referencia,float cant,char tm) throws SQLException {
        Db dbase = Util.getConection();
        float ex;
        String sql = "select existencia from \"Producto\" where referencia = "+referencia+"";
        String sql2 = "UPDATE \"Producto\"\n" +
        "   SET existencia=?\n" +
        " WHERE referencia =?;";
        
        ResultSet rs = dbase.execSelect(sql);
        if(rs.next()){
            ex = rs.getFloat(1);
            if(tm == 'e')
                ex = ex + cant;
            else
                ex = ex - cant;
            
            PreparedStatement p = Db.conexion.prepareStatement(sql2);
            p.setFloat(1, ex);
            p.setInt(2, referencia);
            p.execute();
            
        }
        dbase.CerrarConexion();
        
    }
    
    public String get_producto(String nombre){
        Db dbase = Util.getConection();
        Respuesta resp = new Respuesta();
        ArrayList<Producto> lista = new ArrayList<Producto>();
        String sql = "SELECT referencia, descripcion, id_categoria, precio, costo, existencia, tax FROM \"Producto\""
                + " where descripcion ilike '"+nombre+"%' ;";
        
        System.out.println("Sql ->"+sql);
        
        try{
            ResultSet rs = dbase.execSelect(sql);
            
            while(rs.next()){
                Producto p = new Producto();
                p.setReferencia(rs.getInt(1));
                p.setDescripcion(rs.getString(2));
                p.setId_categoria(rs.getInt(3));
                p.setPrecio(rs.getFloat(4));
                p.setCosto(rs.getFloat(5));
                p.setExistencia(rs.getFloat(6));
                p.setTax(rs.getFloat(7));
                
                lista.add(p);
            }
            
            if(lista.isEmpty())
            {
                resp.setId(0);
                resp.setMensaje("No hay registros actualmente en la base de datos");
                dbase.CerrarConexion();
                return resp.ToJson(resp);
            }
        }
        catch(SQLException e){
            resp.setId(-1);
            resp.setMensaje("Error de la base de datos ");
            System.out.println(e.getMessage());
            dbase.CerrarConexion();
            return resp.ToJson(resp);
        }
        resp.setId(1);
        resp.setMensaje(resp.ToJson(lista));//convierto la lista a Gson
        dbase.CerrarConexion();
        return resp.ToJson(resp); 
        
    }
    
    public String get_producto(int id){
        Db dbase = Util.getConection();
        Respuesta resp = new Respuesta();
        
        String sql = "SELECT referencia, descripcion, id_categoria, precio, costo, existencia, tax FROM \"Producto\""
                + "where referencia = "+id+";";
        
        try{
            ResultSet rs = dbase.execSelect(sql);
            
            if(rs.next()){
                Producto p = new Producto();
                p.setReferencia(rs.getInt(1));
                p.setDescripcion(rs.getString(2));
                p.setId_categoria(rs.getInt(3));
                p.setPrecio(rs.getFloat(4));
                p.setCosto(rs.getFloat(5));
                p.setExistencia(rs.getFloat(6));
                p.setTax(rs.getFloat(7));
                resp.setId(1);
                resp.setMensaje(Respuesta.ToJson(p));
                
            }
            
            
        }
        catch(SQLException e){
            resp.setId(-1);
            resp.setMensaje("Error de la base de datos ");
            System.out.println(e.getMessage());
            dbase.CerrarConexion();
            return resp.ToJson(resp);
        }
        
        dbase.CerrarConexion();
        return resp.ToJson(resp); 
        
    }
    
    
    
}
