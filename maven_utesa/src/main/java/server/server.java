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

import Clases.Categoria;
import Clases.Concepto;
import Clases.Entrada_Inventario;
import Clases.Producto;
import utilidades.Respuesta;
import Clases.Token;
import Clases.Usuario;
import static spark.Spark.*;


public class server {
    
    public static void main(String a []){
        
        port(5555);
        
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
        /*----FIN---Servicios de Producto*/
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
        /*---FIN---Servicios de usuario*/
        /*Servicios Categoria*/
        post("/insertar_categoria",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Categoria c = new Categoria();
                c.setDescripcion(req.queryParams("p1"));
                c.insertar_categoria();
                r.setId(1);
                r.setMensaje("Se inserto correctamente");
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        
        get("/buscar_categoria/:token", (req, res) -> {
            Respuesta r = new Respuesta();
            String token = req.params("token"); 
            if(Token.check_token(token)){
               
                Categoria c = new Categoria();
                String resp = c.get_categoria();
                return resp;
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        /*---FIN---Servicios Categoria*/
        /*Servicios Concepto*/
        post("/insertar_concepto",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Concepto c = new Concepto();
                c.setId_concepto(Integer.parseInt(req.queryParams("p1")));
                c.setDescripcion(req.queryParams("p2"));
                c.insertar_concepto(c);
                r.setId(1);
                r.setMensaje("Se inserto correctamente");
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        
        get("/buscar_concepto/:token", (req, res) -> {
            Respuesta r = new Respuesta();
            String token = req.params("token"); 
            if(Token.check_token(token)){
               
                Concepto c = new Concepto();
                String resp = c.get_concepto();
                return resp;
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        /*--FIN--- Servicios Concepto*/
        /*Servicios de Inventario*/
        post("/insertar_entrada",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                String js_entrada_inv = req.queryParams("js_entrada_inv");
                String js_detalle_ent = req.queryParams("js_detelle_ent");
                Entrada_Inventario ei = new Entrada_Inventario();
                r = ei.entrada_inventario(js_entrada_inv, js_detalle_ent);
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        /*--FIN--- Servicios de Inventario*/
    }
    
    
}
