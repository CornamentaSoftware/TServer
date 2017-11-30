/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tserver;
import Encriptacion.AES;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 *
 * @author Karla
 */
public class TServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, Exception{
       
        int puertoC= 4000;
        int miPuerto= 3000;
        ServerSocket socketC = new ServerSocket(miPuerto);
        System.out.println("Preparado para aceptar conexion");
        SocketS socketE = new SocketS(socketC.accept());
        System.out.println("Conexion aceptada");
        
        ServerSocket socketC2 = new ServerSocket(2999);
        SocketS socketR = new SocketS("192.168.9.39", puertoC);
        //Direccion del cliente
        
        ServerSocket socketC3 = new ServerSocket(2998);
        SocketS socketR2 = new SocketS("192.168.9.160", 5000);
        //Direccion del servidor de autenticacion
        
        //Recibe el ticketInicial y lo descifra con la llave secreta que ya conocía
        AES aes = new AES();
        String mensaje1 = socketR.recibe();
        socketR.close();
        System.out.println(mensaje1);
        String mensaje2 = aes.Desencriptar(socketR2.recibe(), "CFRR");
        socketR2.close();
        String ipC="", ipC2="", srvc="", srvc2="";
        
        //Aquí checa qué datos recibió 
        for(int i=0; i<mensaje1.length(); i++){
            if(mensaje1.charAt(i)==' '){
                ipC = mensaje1.substring(0,i);
                ipC2 = mensaje2.substring(0,i);
                srvc = mensaje1.substring(i+1);
                srvc2 = mensaje2.substring(i+1);
            }
        }
        
        if(ipC.equals(ipC2) && srvc.equals(srvc2)){
            socketE.envia("Correcto " + srvc);
            System.out.println("Solicitud respondida");
        }
        
        socketR.close();
        socketE.close();
    }
    
}
