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
import Clases.Cliente;
import Clases.Concepto;
import Clases.Detalle_Factura;
import Clases.Detalle_Pago;
import Clases.Entrada_Inventario;
import Clases.Factura;
import Clases.Historial;
import Clases.Producto;
import Clases.Recibo;
import java.sql.Timestamp;
import Clases.Salida_Inventario;
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
        
        get("/buscar_producto_ref/:token/:id", (req, res) -> {
            String token = req.params("token"); 
            int id = Integer.parseInt(req.params("id"));
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                Producto f = new Producto();
                return f.get_producto(id);
            }
            else{
                
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
        
        get("/logout/:token", (req, res) -> {
            Respuesta r = new Respuesta();
            String token = req.params("token");
            if(Token.check_token(token)){
                Token t = new Token();
                Historial h = new Historial();
                h.insert_salida_historial(token);
                t.delete_token(token);
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
                
                c.setDescripcion(req.queryParams("p1"));
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
        post("/insertar_entrada_inventario",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                String js_entrada_inv = req.queryParams("js_entrada_inv");
                String js_detalle_ent = req.queryParams("js_detalle_ent");
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
        
        post("/insertar_salida_inventario",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                String js_salida_inv = req.queryParams("js_salida_inv");
                String js_detalle_sal = req.queryParams("js_detalle_sal");
                Salida_Inventario si = new Salida_Inventario();
                r = si.salida_inventario(js_salida_inv, js_detalle_sal);
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        /*--FIN--- Servicios de Inventario*/
        /*Servicio de Cliente*/
        get("/buscar_cliente/:token/:n1", (req, res) -> {
            String token = req.params("token"); 
            if(Token.check_token(token)){
                String nombre = req.params("n1");
                Cliente p = new Cliente();
                String resp = p.get_cliente(nombre);
                return resp;
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        
        get("/buscar_cliente_id/:token/:n1", (req, res) -> {
            String token = req.params("token"); 
            if(Token.check_token(token)){
                int id = Integer.parseInt(req.params("n1"));
                Cliente p = new Cliente();
                String resp = p.get_cliente(id);
                return resp;
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
       
        post("/insertar_cliente", (req, res) -> {
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Cliente p = new Cliente();
                p.setNombre(req.queryParams("p1"));
                p.setApellido(req.queryParams("p2"));
                p.setDireccion(req.queryParams("p3"));
                p.setTelefono(req.queryParams("p4"));
                p.setEmail(req.queryParams("p5"));
                return  p.insertar_cliente(p);      
            }
            else{
                Respuesta r = new Respuesta();
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        /*--Fin-- Servicios cliente*/
        /*Servicios de Factura*/
        post("/insertar_factura",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                String js_factura = req.queryParams("js_factura");
                String js_detalle_factura = req.queryParams("js_detalle_factura");
                Factura f = new Factura();
                r = f.factura(js_factura, js_detalle_factura);
                return Respuesta.ToJson(r);
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        
        get("/buscar_proxima_factura/:token", (req, res) -> {
            String token = req.params("token"); 
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                
                Factura f = new Factura();
                int id = f.getLastId();
                
                r.setId(1);
                r.setMensaje(Integer.toString(id));
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        
        get("/buscar_factura/:token", (req, res) -> {
            String token = req.params("token"); 
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                Factura f = new Factura();
                return Respuesta.ToJson(f.getFacturas());
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });   
        
        get("/buscar_factura/:token/:id_cliente", (req, res) -> {
            String token = req.params("token"); 
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                int id_cliente = Integer.parseInt(req.params("id_cliente"));
                Factura f = new Factura();
                return Respuesta.ToJson(f.getFacturas(id_cliente));
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });  
        
        get("/buscar_factura_for_recibo/:token/:id_cliente", (req, res) -> {
            String token = req.params("token"); 
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                int id_cliente = Integer.parseInt(req.params("id_cliente"));
                Factura f = new Factura();
                return Respuesta.ToJson(f.getFacturas_forRecibo(id_cliente));
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });  
        
        get("/buscar_detalle_factura/:token/:id_factura", (req, res) -> {
            String token = req.params("token"); 
            int id_factura = Integer.parseInt(req.params("id_factura"));
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                Detalle_Factura f = new Detalle_Factura();
                r = f.get_detalle_factura(id_factura);
                return Respuesta.ToJson(r);
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });   
        /*Fin de servicio de factua*/
        /*servicio de recibo*/
        post("/insertar_recibo",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Recibo rec = new Recibo();
                rec.setFecha( Timestamp.valueOf(req.queryParams("p1")));
                rec.setId_cliente(Integer.parseInt(req.queryParams("p2")));
                rec.setMonto(Float.parseFloat(req.queryParams("p3")));
                rec.setConcepto_recibo(req.queryParams("p4"));
                rec.setId_usuario(req.queryParams("p5"));
                
                return Respuesta.ToJson(rec.insert_recibo());
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        
        get("/buscar_recibos/:token", (req, res) -> {
            String token = req.params("token"); 
            
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                Recibo rec = new Recibo();
                return Respuesta.ToJson(rec.getRecibos());
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });
        
        get("/buscar_recibos/:token/:id_cliente", (req, res) -> {
            String token = req.params("token"); 
            int id_cliente = Integer.parseInt(req.params("id_cliente"));
            Respuesta r= new Respuesta();
            if(Token.check_token(token)){
                Recibo rec = new Recibo();
                return Respuesta.ToJson(rec.getRecibos(id_cliente));
            }
            else{
                
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
        });   
        /*Fin de servicio de recibo*/
        /*Detalle_Pago*/
        post("/insertar_detalle_pago",(req,res) ->{
            Respuesta r = new Respuesta();
            String token = req.queryParams("token");
            if(Token.check_token(token)){
                Detalle_Pago rec = new Detalle_Pago();
                rec.setId_factura(Integer.parseInt(req.queryParams("p1")));
                rec.setMonto(Float.parseFloat(req.queryParams("p2")));
                rec.setId_recibo(Integer.parseInt(req.queryParams("p3")));
                return Respuesta.ToJson(rec.insert_detalle_pago());
            }
            else{
                r.setId(-1);
                r.setMensaje("token invalido");
                return Respuesta.ToJson(r);
            }
            
        });
        /*Fin detalle_pago*/
    }
    
    
}
