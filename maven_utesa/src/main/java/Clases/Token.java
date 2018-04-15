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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanLH
 */
public class Token {
    String id_usuario,token;
    Timestamp date;

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public String insert_token(String id_usuario,String token){
        Db dbase = Util.getConection();
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
        String sql = "INSERT INTO \"Token\"(id_usuario, fecha, token)\n" +
        "    VALUES (?, ?, ?);";
        try {
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setString(1, id_usuario);
            p.setTimestamp(2, t);
            p.setString(3,token);
            p.execute();
            return "1";
        } catch (SQLException ex) {
            Logger.getLogger(Token.class.getName()).log(Level.SEVERE, null, ex);
            return "-1";
        }
        
    }
    
    public String delete_token(String id_usuario) throws SQLException{
        Db dbase = Util.getConection();
        String sql = "DELETE FROM \"Token\"\n" +
        " WHERE id_usuario = '"+id_usuario+"';";
        
        dbase.execSelect(sql);
        return "1";
    }
    
    public static boolean check_token(String token) throws SQLException{
        Db dbase = Util.getConection();
        String sql = "SELECT id_usuario\n" +
        "  FROM \"Token\" where token='"+token+"';";
        ResultSet rs = dbase.execSelect(sql);
        
        if(rs.next())
            return true;
        else
            return false;
                
    }
}
