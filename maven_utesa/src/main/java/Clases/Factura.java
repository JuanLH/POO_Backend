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
public class Factura {
    int id_factura,id_cliente;
    String tipo_factura,id_usuario;
    Timestamp fecha;
    Float monto;

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(String tipo_factura) {
        this.tipo_factura = tipo_factura;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }
    
    public Respuesta factura (String js_factura,String js_detalle_factura){
        Gson json = new Gson();
        Respuesta r = new Respuesta();
        Factura fac = json.fromJson(js_factura, Factura.class);
        try {
            fac.setId_factura(fac.insert_factura());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            r.setId(-1);
            r.setMensaje("Factura - Error en la base de datos");
            return r;
        }
        
        ArrayList<Detalle_Factura> lista = new ArrayList();
        JsonElement jsonE = new JsonParser().parse(js_detalle_factura);
        JsonArray array = jsonE.getAsJsonArray();
        for (JsonElement j : array) {
            Detalle_Factura d_fa;
            d_fa = json.fromJson(j, Detalle_Factura.class);
            /*
            Seteando el id_entrada porque viene vacio ya que no se a 
            insertado el registro en Entrada Inventario
            */
            d_fa.setId_factura(fac.getId_factura());
            lista.add(d_fa);
        }
        
         /*Insert the data en Detalle_Factura*/
        for(Detalle_Factura df :lista){
            try {
                df.insert();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                r.setId(-1);
                r.setMensaje("Factura- Error en la base de datos");
                return r;
            }
        }
        r.setId(1);
        r.setMensaje("Se inserto correctmente");
        return r;
    }
    
    public int insert_factura() throws SQLException{
        Db dbase = Util.getConection();
        
        String sql = "INSERT INTO \"Factura\"(\n" +
        "            tipo_factura, fecha, id_cliente, monto, id_usuario)\n" +
        "    VALUES (?, ?, ?, ?, ?);";
        
        PreparedStatement p = Db.conexion.prepareStatement(sql);
        p.setString(1,this.getTipo_factura());
        p.setTimestamp(2, this.getFecha());
        p.setInt(3, this.getId_cliente());
        p.setFloat(4,this.getMonto());
        p.setString(5,this.getId_usuario());
        p.execute();
        dbase.CerrarConexion();

        return getLastId();
    }
    
    public int getLastId() {
        int id = -1;
        Db dbase = Util.getConection();
        String sql = "SELECT id_factura, tipo_factura, fecha, id_cliente, monto, id_usuario\n" +
            "  FROM \"Factura\" order by id_factura desc limit 1;";
        
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
