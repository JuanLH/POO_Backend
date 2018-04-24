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
public class Cliente {
    int id;
    String nombre,apellido,direccion,telefono,email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String insertar_cliente(Cliente info){
    Db dbase = Util.getConection();
        Respuesta r = new Respuesta();
        String sql = "INSERT INTO \"Cliente\"(\n" +
            "             nombre, apellido, direccion, telefono, email)\n" +
            "       VALUES ( ?, ?, ?, ?, ?);";
        try{
            PreparedStatement p = Db.conexion.prepareStatement(sql);
            
            
            p.setString(1,info.getNombre());
            p.setString(2, info.getApellido());
            p.setString(3, info.getDireccion());
            p.setString(4, info.getTelefono());
            p.setString(5, info.getEmail());
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

    
    public String get_cliente(String nombre){
        Db dbase = Util.getConection();
        Respuesta resp = new Respuesta();
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        String sql = "SELECT id, nombre, apellido, direccion, telefono, email\n" +
                    "  FROM \"Cliente\" where nombre ilike '"+nombre+"%';";
        
        try{
            ResultSet rs = dbase.execSelect(sql);
            
            while(rs.next()){
                Cliente p = new Cliente();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setApellido(rs.getString(3));
                p.setDireccion(rs.getString(4));
                p.setTelefono(rs.getString(5));
                p.setEmail(rs.getString(6));
                
                
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
    
    public String get_cliente(int id){
        Db dbase = Util.getConection();
        Respuesta resp = new Respuesta();
        String sql = "SELECT id, nombre, apellido, direccion, telefono, email\n" +
                    "  FROM \"Cliente\" where id = "+id+";";
        
        try{
            ResultSet rs = dbase.execSelect(sql);
            
            if(rs.next()){
                Cliente p = new Cliente();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setApellido(rs.getString(3));
                p.setDireccion(rs.getString(4));
                p.setTelefono(rs.getString(5));
                p.setEmail(rs.getString(6));
                resp.setId(1);
                resp.setMensaje(Respuesta.ToJson(p));
                dbase.CerrarConexion();
                return Respuesta.ToJson(resp);
            }
            else
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
        
        
    }
    
}
