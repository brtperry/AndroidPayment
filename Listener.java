/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

import java.net.*;
import java.io.*;

/**
 *
 * @author brtperry
 */
public class Listener implements ISocket {
    
    private InetSocketAddress   address;
    private PrintWriter         sender  = null;
    private Socket              echo    = null;
    
    public Listener(String host, int port) {
        
        address = new InetSocketAddress(host, port);
        
    }
    
    @Override
    public boolean Connect() throws IOException  {
        
         echo = new Socket();
         echo.connect(address);
         
         return echo.isConnected();
    }

    @Override
    public void send(String message) {
        
        sender.write(message);
        
    }

    @Override
    public String[] receive() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
