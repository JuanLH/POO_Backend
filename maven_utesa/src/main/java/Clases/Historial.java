
package Clases;

import db.Db;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanLH
 */
public class Historial {
    String id_usuario,token;
    Timestamp entrada,salida;

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

    public Timestamp getEntrada() {
        return entrada;
    }

    public void setEntrada(Timestamp entrada) {
        this.entrada = entrada;
    }

    public Timestamp getSalida() {
        return salida;
    }

    public void setSalida(Timestamp salida) {
        this.salida = salida;
    }
    
    public String insert_historial(String id_user,String token) {
        Db dbase = Util.getConection();
        Historial h = new Historial();
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
        h.setId_usuario(id_usuario);
        h.setEntrada(t);
        h.setToken(token);
        
        String  sql = "INSERT INTO historial(\n" +
        "            id_usuario, entrada, token)\n" +
        "    VALUES (?, ?, ?);";
        try {
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setString(1,id_user);
            p.setTimestamp(2, t);
            p.setString(3, token);
            p.execute();
            dbase.CerrarConexion();
            return "1";
        } catch (SQLException ex) {
            Logger.getLogger(Historial.class.getName()).log(Level.SEVERE, null, ex);
            return "-1";
        }
    }
    
    public String insert_salida_historial(String token) {
        Db dbase = Util.getConection();
        
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
       
        
        String  sql = "UPDATE historial\n" +
            "   SET salida=?\n" +
            " WHERE token='"+token+"';";
        try {
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            p.setTimestamp(1,t);
  
            p.execute();
            dbase.CerrarConexion();
            return "1";
        } catch (SQLException ex) {
            Logger.getLogger(Historial.class.getName()).log(Level.SEVERE, null, ex);
            return "-1";
        }
    }
    
    
}
