/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tserver;
import Encriptacion.AES;
import java.io.*;
import java.net.*;
/**
 *
 * @author Karla
 */
public class TServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, Exception{
        //Creacion del socket para el cliente
        int miPuerto= 3000;
        ServerSocket socket1 = new ServerSocket(miPuerto);
        System.out.println("Preparado para aceptar conexion");
        Socket socketC = socket1.accept();
        InputStream flujoE = socketC.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(flujoE));
        OutputStream flujoS = socketC.getOutputStream();
        PrintWriter escritor = new PrintWriter(new OutputStreamWriter(flujoS));
        System.out.println("Conexion aceptada");
        String mensaje1 = reader.readLine();
        System.out.println(mensaje1);

        
        //Creacion del socket para el servidor
        ServerSocket socket2 = new ServerSocket(3500);
        Socket socketS = socket2.accept();
        System.out.println("Conexion aceptada con el servidor de autenticacion");
        InputStream flujoE2 = socketS.getInputStream();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(flujoE2));
        
        //Recibe el ticketInicial y lo descifra con la llave secreta que ya conocía
        AES aes = new AES();
        String mensaje2 = aes.Desencriptar(reader2.readLine(), "CFRR");
        System.out.println(mensaje2);
        String ipC="", ipC2="", srvc="", srvc2="";
        
        //Aquí checa qué datos recibió 
        for(int i=0; i<mensaje1.length(); i++){
            if(mensaje1.charAt(i)=='&'){
                ipC = mensaje1.substring(0,i);
                ipC2 = mensaje2.substring(0,i);
                srvc = mensaje1.substring(i+1);
                srvc2 = mensaje2.substring(i+1);
            }
        }
        
        if(ipC.equals(ipC2) && srvc.equals(srvc2)){
            escritor.println(srvc);
            escritor.flush();
            System.out.println(srvc);
            System.out.println("Solicitud respondida");
        }
    }
    
}
