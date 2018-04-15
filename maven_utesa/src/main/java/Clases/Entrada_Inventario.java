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
public class Entrada_Inventario {
    int id_entrada,id_concepto;
    Timestamp fecha;
    String id_usuario;

    public int getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(int id_entrada) {
        this.id_entrada = id_entrada;
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
    
    public void entrada_inventario(String js_entrada_inv,String js_detalle_ent){
        Gson json = new Gson();
        Entrada_Inventario e = json.fromJson(js_entrada_inv, Entrada_Inventario.class);
        e.setId_entrada(insert_entrada_inventario(e));
        
        
        
        ArrayList<Detalle_Entrada> lista = new ArrayList();
        JsonElement jsonE = new JsonParser().parse(js_detalle_ent);
        JsonArray array = jsonE.getAsJsonArray();
        for (JsonElement j : array) {
            Detalle_Entrada d_en;
            d_en = json.fromJson(j, Detalle_Entrada.class);
            /*
            Seteando el id_entrada porque viene vacio ya que no se a 
            insertado el registro en Entrada Inventario
            */
            d_en.setId_entrada(e.getId_entrada());
            lista.add(d_en);
        }
        
        /*Insert the data en Detalle_entrada*/
        for(Detalle_Entrada de:lista){
            de.insert(de);
        }
        
    }
    
    public int insert_entrada_inventario(Entrada_Inventario info){
        Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Entrada_Inventario\"(\n" +
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
        String sql = "SELECT  id_entrada, id_concepto, fecha, id_usuario\n" +
        "  FROM \"Entrada_Inventario\" order by id_entrada desc limit 1;";
        
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
