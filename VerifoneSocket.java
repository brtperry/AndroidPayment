/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

import java.io.*;
import java.net.*;

/**
 *
 * @author brtperry
 */
public class VerifoneSocket {
    
    private InetSocketAddress address;
    private Socket echo = null;
    
    private static final int LF = 0x0A; // Linefeed character or \n
    private static int port = 25001;

    public VerifoneSocket(String ip) {
        
        address = new InetSocketAddress(ip, port);

    }
    
    public boolean Connect(){
        
        try {
            
            echo = new Socket();
            echo.setSoTimeout(30000);
            echo.connect(address);
            
            if (!echo.isConnected()) {
                
                return false;
                
            }
            
        } catch (UnknownHostException ex) {
            
            System.out.println(ex.getMessage()); 
            
            return false;
            
        } catch (IOException ex) {
            
            System.out.println(ex.getMessage()); 
            
            return false;
        }
        
        return true;
    }
    
    public void write(String s) throws IOException {
        
        PrintWriter pw = new PrintWriter(echo.getOutputStream(), true);
        
        pw.write(s);
        pw.flush();
        
    }  
    
    public String read() throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(echo.getInputStream()));
        
        int c;
        
        String response = "";
            
        try {  
            
            for (c = br.read(); c != LF; c = br.read())
            {
                response += (char)c;
            }
            
            System.out.println( response );
            
            /*
            while ((data = receiver.readLine()) != null)
            {
                results  += data;

                System.out.println( data );

            }
            */
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return response;       

    }    
    
    public void Close() throws IOException {

        echo.close();
        
    }

}
