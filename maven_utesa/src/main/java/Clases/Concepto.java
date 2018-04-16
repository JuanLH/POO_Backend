/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import utilidades.Respuesta;
import utilidades.Util;
import db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanLH
 */
public class Concepto {
    int id_concepto;
    String descripcion;

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String insertar_concepto(Concepto info){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Concepto\"(\n" +
            "           descripcion)\n" +
            "    VALUES (?, ?);";
        try{
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            
            
            p.setString(1,info.getDescripcion());
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
    
    public String get_concepto(){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql="SELECT id_concepto, descripcion\n" +
                    "  FROM \"Concepto\";";
        ArrayList<Concepto> lista = new ArrayList();
        
        try {
            ResultSet rs = dbase.execSelect(sql);
            while(rs.next()){
                Concepto c = new Concepto();
                c.setId_concepto(rs.getInt(1));
                c.setDescripcion(rs.getString(2));
                lista.add(c);
            }
            
            if(lista.isEmpty())
            {
                r.setId(0);
                r.setMensaje("No hay registros actualmente en la base de datos");
                dbase.CerrarConexion();
                return Respuesta.ToJson(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            r.setId(-1);
            r.setMensaje("Error de la base de datos ");
            System.out.println(ex.getMessage());
            dbase.CerrarConexion();
            return Respuesta.ToJson(r);
        }
        r.setId(1);
        r.setMensaje(Respuesta.ToJson(lista));//convierto la lista a Gson
        dbase.CerrarConexion();
        return Respuesta.ToJson(r); 
        
        
    }
}
