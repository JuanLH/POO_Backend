/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Michael Jared Diaz
 */

import Clases.Producto;
import Clases.Respuesta;
import Clases.Token;
import Clases.Usuario;
import static spark.Spark.*;


public class server {
    
    public static void main(String a []){
        
        port(5555);
        /*
        get("/hello", (req, res) -> "<h3> GET </h3> Hello World <br> Hello <b>UTESA</b>");
        
        post("/hello", (req, res) -> "<h3> POST </h3> Hello World <br> Hello <b>UTESA</b>");
        
        //-------------------------------
        
        // (GET) RECIBIENDO LOS PARAMETRO DIRECTAMENTE POR URL
        get("/sumar_cantidades/:n1/:n2", (req, res) -> {
            int num1 = Integer.parseInt(req.params("n1"));
            int num2 = Integer.parseInt(req.params("n2"));
            return num1 + num2;
        });
        
        
        // (POST) RECIBIENDO LOS PARAMETRO POR FORM
        post("/sumar_cantidades", (req, res) -> {
            double num1 = Integer.parseInt(req.queryParams("n1"));
            double num2 = Integer.parseInt(req.queryParams("n2"));
            return num1 + num2;
        });
        
        // (POST) RECIBIENDO LOS PARAMETRO DIRECTAMENTE POR URL
        post("/sumar_cantidades/:n1/:n2", (req, res) -> {
            double num1 = Integer.parseInt(req.params("n1"));
            double num2 = Integer.parseInt(req.params("n2"));
            return num1 + num2;
        });
        
      // (POST) RECIBIENDO LOS PARAMETRO DIRECTAMENTE POR URL
        post("/insertar_cliente", (req, res) -> {
           
            req.queryParams("d1");
            req.queryParams("d2");
            DaoClientes dd = new DaoClientes();
            
            return "OK";
        });  
        */
        // (GET) RECIBIENDO LOS PARAMETRO DIRECTAMENTE POR URL
        
        
        /*Servicios de Producto*/
        get("/buscar_producto/:token/:n1", (req, res) -> {
            String token = req.params("token"); 
            if(Token.check_token(token)){
                String num1 = req.params("n1");
                Producto p = new Producto();
                String resp = p.get_producto(num1);
                return resp;
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        // (POST) RECIBIENDO LOS PARAMETRO POR FORM
        post("/insertar_producto", (req, res) -> {
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Producto p = new Producto();
                p.setDescripcion(req.queryParams("p2"));
                p.setId_categoria(Integer.parseInt(req.queryParams("p3")));
                p.setPrecio(Float.parseFloat(req.queryParams("p4")));
                p.setCosto(Float.parseFloat(req.queryParams("p5")));
                p.setExistencia(Float.parseFloat(req.queryParams("p6")));
                p.setTax(Float.parseFloat(req.queryParams("p7")));
                return  p.insertar_producto(p);      
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        
        /*Servicios de usuario*/
        post("/insertar_usuario",(req,res) ->{
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Usuario u = new Usuario();
                u.setId_usuario(req.queryParams("p1"));
                u.setNombre(req.queryParams("p2"));
                u.setPassword(req.queryParams("p3"));
                return u.insertar_usuario();
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        
        get("/login/:n1/:n2", (req, res) -> {
            String id_usuario = req.params("n1");
            String password = req.params("n2");
            Usuario user = new Usuario();
            String resp = user.check_usuario(id_usuario, password);
            return resp;
        });
        
        get("/logout/:token/:n1", (req, res) -> {
            Respuesta r = new Respuesta();
            String token = req.params("token");
            if(Token.check_token(token)){
                String id_usuario = req.params("n1");
                Token t = new Token();
                t.delete_token(id_usuario);
                r.setId(1);
                r.setMensaje("logout succesfull");
                return Respuesta.ToJson(r);
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        
    }
    
    
}
