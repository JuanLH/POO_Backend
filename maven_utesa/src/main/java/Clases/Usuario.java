/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import utilidades.Util;
import db.Db;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author JuanLH
 */
public class Usuario {
    String id_usuario,nombre,password;
    boolean activo;

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public String insertar_usuario(){
        Db dbase = Util.getConection();
        Respuesta r= new Respuesta();
        String sql = "INSERT INTO \"Usuario\"(\n" +
        "            id_usuario, nombre, password, activo)\n" +
        "    VALUES (?, ?, ?, ?);";
        try{
            PreparedStatement p = Db.conexion.prepareStatement(sql);
           
            p.setString(1,this.getId_usuario());
            p.setString(2, this.getNombre());
            p.setString(3, this.getPassword());
            p.setBoolean(4, this.isActivo());
            p.execute();
            dbase.CerrarConexion();
            
            r.setId(1);
            r.setMensaje("Insertado Correctamente");
            return Respuesta.ToJson(r);
        }
        catch(SQLException e){
            r.setId(-1);
            r.setMensaje(e.getMessage());
            System.out.println(Respuesta.ToJson(r));
            return Respuesta.ToJson(r);
        }
    }
    
    
    public String check_usuario(String id_user,String pass) throws SQLException{
        Db dbase = Util.getConection();
        Respuesta resp = new Respuesta();
        String sql = "SELECT id_usuario, nombre, password, activo\n" +
        "  FROM \"Usuario\" where id_usuario like '"+id_user+"'"
                + " and password like '"+pass+"'";
        
        ResultSet rs = dbase.execSelect(sql);
        if(rs.next()){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            sql = "select md5('"+id_user.concat(dateFormat.format(date))+"')";
            ResultSet rs2 = dbase.execSelect(sql);
            
            rs2.next();
            resp.setId(1);
            resp.setMensaje(rs2.getString(1));
            
            Historial h = new Historial();
            h.insert_historial(id_user, rs2.getString(1));
            
            Token token = new Token();
            token.insert_token(id_user, rs2.getString(1));
            
            return Respuesta.ToJson(resp);
        }
        else{
            resp.setId(0);
            resp.setMensaje("No Existe");
            return  Respuesta.ToJson(resp);
        }
            
    }
}
