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
public class Recibo {
    int id_recibo,id_cliente;
    Timestamp fecha;
    Float monto;
    String concepto_recibo,id_usuario;

    public int getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(int id_recibo) {
        this.id_recibo = id_recibo;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getConcepto_recibo() {
        return concepto_recibo;
    }

    public void setConcepto_recibo(String concepto_recibo) {
        this.concepto_recibo = concepto_recibo;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public Respuesta insert_recibo() {
        Respuesta r = new Respuesta();
        try {
            Db dbase = Util.getConection();
            String sql ="INSERT INTO \"Recibo\"(\n" +
                    "             fecha, id_cliente, monto, concepto_recibo, id_usuario)\n" +
                    "    VALUES ( ?, ?, ?, ?, ?);";
            
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setTimestamp(1, this.fecha);
            p.setInt(2, this.getId_cliente());
            p.setFloat(3, this.getMonto());
            p.setString(4,this.getConcepto_recibo());
            p.setString(5, this.getId_usuario());
            p.execute();
            dbase.CerrarConexion();
            r.setId(1);
            r.setMensaje("Se inserto correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(Recibo.class.getName())
                    .log(Level.SEVERE, null, ex);
            r.setId(1);
            r.setMensaje("Error en la base de datos");
        }
        return r;

    }
    
        public Respuesta getRecibos(){
            Db dbase = Util.getConection();
            Respuesta r = new Respuesta();
            String sql = "SELECT id_recibo, fecha, id_cliente, monto, concepto_recibo, id_usuario\n" +
                "  FROM \"Recibo\";";
            ArrayList<Recibo> lista = new ArrayList();
            try {
                ResultSet rs = dbase.execSelect(sql);
                while(rs.next()){
                    Recibo rec = new Recibo();
                    rec.setId_recibo(rs.getInt(1));
                    rec.setFecha(rs.getTimestamp(2));
                    rec.setId_cliente(rs.getInt(3));
                    rec.setMonto(rs.getFloat(4));
                    rec.setConcepto_recibo(rs.getString(5));
                    rec.setId_usuario(rs.getString(6));
                    lista.add(rec);
                }
                r.setId(1);
                r.setMensaje(Respuesta.ToJson(lista));
                return r;
            } catch (SQLException ex) {
                Logger.getLogger(Entrada_Inventario.class.getName())
                        .log(Level.SEVERE, null, ex);
                r.setId(-1);
                r.setMensaje("Error en la base de datos");
                return r;
            }
    
        }
        
        public Respuesta getRecibos(int id_cliente){
            Db dbase = Util.getConection();
            Respuesta r = new Respuesta();
            String sql = "SELECT id_recibo, fecha, id_cliente, monto, concepto_recibo, id_usuario\n" +
                "  FROM \"Recibo\" where id_cliente = "+id_cliente+";";
            ArrayList<Recibo> lista = new ArrayList();
            try {
                ResultSet rs = dbase.execSelect(sql);
                while(rs.next()){
                    Recibo rec = new Recibo();
                    rec.setId_recibo(rs.getInt(1));
                    rec.setFecha(rs.getTimestamp(2));
                    rec.setId_cliente(rs.getInt(3));
                    rec.setMonto(rs.getFloat(4));
                    rec.setConcepto_recibo(rs.getString(5));
                    rec.setId_usuario(rs.getString(6));
                    lista.add(rec);
                }
                r.setId(1);
                r.setMensaje(Respuesta.ToJson(lista));
                return r;
            } catch (SQLException ex) {
                Logger.getLogger(Entrada_Inventario.class.getName())
                        .log(Level.SEVERE, null, ex);
                r.setId(-1);
                r.setMensaje("Error en la base de datos");
                return r;
            }
        }
}
