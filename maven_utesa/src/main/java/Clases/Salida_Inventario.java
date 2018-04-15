/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import db.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.Respuesta;
import utilidades.Util;

/**
 *
 * @author JuanLH
 */
public class Salida_Inventario {
    int id_salida,id_concepto;
    Timestamp fecha;
    String id_usuario;

    public int getId_salida() {
        return id_salida;
    }

    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
    }

    public int getId_concepto() {
        return id_concepto;
    }

    public void setId_concepto(int id_concepto) {
        this.id_concepto = id_concepto;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public void salida_inventario(String js_entrada_inv,String js_detalle_ent){
        Gson json = new Gson();
        Salida_Inventario e = json.fromJson(js_entrada_inv, Salida_Inventario.class);
        e.setId_salida(insert_salida_inventario(e));
        
        
        
        ArrayList<Detalle_Salida> lista = new ArrayList();
        JsonElement jsonE = new JsonParser().parse(js_detalle_ent);
        JsonArray array = jsonE.getAsJsonArray();
        for (JsonElement j : array) {
            Detalle_Salida d_sal;
            d_sal = json.fromJson(j, Detalle_Salida.class);
            /*
            Seteando el id_salida porque viene vacio ya que no se a 
            insertado el registro en Entrada Inventario
            */
            d_sal.setId_salida(e.getId_salida());
            lista.add(d_sal);
        }
        
        /*Insert the data en Detalle_entrada*/
        for(Detalle_Salida de:lista){
            try {
                de.insert(de);
            } catch (SQLException ex) {
                Logger.getLogger(Salida_Inventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public int insert_salida_inventario(Salida_Inventario info){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Salida_Inventario\"(\n" +
        "            id_concepto, fecha, id_usuario)\n" +
        "    VALUES (?, ?, ?);";
        try{
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setInt(1,info.getId_concepto());
            p.setTimestamp(2, info.getFecha());
            p.setString(3, info.getId_usuario());
            p.execute();
            dbase.CerrarConexion();
            
            return getLastId();
        }
        catch(SQLException e){
            
            System.err.println(e.getMessage());
            return -1;
        }
    }
    
    public int getLastId() {
        int id = -1;
        Db dbase = Util.getConection();
        String sql = "SELECT  id_salida, id_concepto, fecha, id_usuario\n" +
        "  FROM \"Salida_Inventario\" order by id_entrada desc limit 1;";
        
        try {
            ResultSet rs = dbase.execSelect(sql);
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Entrada_Inventario.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
