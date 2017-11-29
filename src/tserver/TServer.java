/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tserver;
import Encriptacion.AES;
import java.io.IOException;
import java.net.InetAddress;
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
        Socket socket = new Socket(miPuerto);
        
        //Recibe el ticketInicial y lo descifra con la llave secreta que ya conocía
        AES aes = new AES();
        String mensaje1 = socket.recibe();
        String mensaje2 = aes.Desencriptar(socket.recibe(), "CFRR");
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
            socket.envia(InetAddress.getByName(ipC), puertoC, "Correcto " + srvc);
        }
        
        socket.close();
    }
    
}
