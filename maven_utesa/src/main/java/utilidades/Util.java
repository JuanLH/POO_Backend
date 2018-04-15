/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import db.Db;
import java.sql.Connection;

/**
 *
 * @author JuanLH
 */
public class Util {
    private static Connection cnn = null;
    
    public static Db getConection(){
        Db dbase = new Db("POO","postgres","JuanLH@22");
        cnn = dbase.getConection();
        return dbase;
    }

    public static Connection getCnn() {
        return cnn;
    }
}
