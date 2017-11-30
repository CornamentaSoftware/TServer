/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tserver;
import java.net.*;
import java.io.*;
/**
 *
 * @author Ana
 */
public class SocketS extends Socket{
	
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
   
    public SocketS(String maquinaA, int puertoA) 
            throws SocketException, IOException{
        socket = new Socket(maquinaA, puertoA);
        establecerFlujos();
    }
    
    public SocketS (Socket socket) throws IOException{
        this.socket = socket;
        establecerFlujos();
    }
    
    private void establecerFlujos() throws IOException{
        InputStream flujoE = socket.getInputStream();
        entrada = new BufferedReader(new InputStreamReader(flujoE));
        OutputStream flujoS = socket.getOutputStream();
        salida = new PrintWriter(new OutputStreamWriter(flujoS));
    }
	
    public void envia(String mensaje) throws IOException{
        salida.println(mensaje);
        salida.flush();
    }
    
    public String recibe() throws IOException{
        String mensaje = entrada.readLine();
        return mensaje;
    }
    
    public void close() throws IOException{
        socket.close();
    }
}// fin de class

