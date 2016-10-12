/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author brtperry
 */
public class Subscriber {
    
    public enum Status {
    
        successful  (0),
        connecting  (-1),
        writing     (-2),
        reading     (-3),
        connected   (-4),
        offline     (-5),
        cancelling  (-6),
        cancelled   (-7),
        ack         (-8);

        public int value;

        Status(int v) {
            this.value = v;
        }
    }  
    
    public  Status state;
    public  boolean connected;
        
    private static final int LF = 0x0A; // Linefeed character or \n
    private static int port = 25001;     
    
    private IEvent ie;
    private InetSocketAddress address;
    private Socket echo = null;
    
    
    public Subscriber (IEvent event, String ip) 
    {
        // Save the event object for later use.
        ie = event; 
        
        address = new InetSocketAddress(ip, port);
    } 
    
    public void Connect(){
        
        String message;
        
        try {
            
            state = Subscriber.Status.connecting;
            
            echo = new Socket();
            echo.setSoTimeout(30000);
            echo.connect(address);
           
            if (echo.isConnected()) {
                
                connected = true;
                state = Subscriber.Status.connected;
            }
            
            message = connected ? "Connected" : "Disconnected";           
            
        } catch (UnknownHostException ex) { 
            
            message = "Host exception " + ex.getMessage();
            
        } catch (IOException ex) {
            
             message = "IO exception " + ex.getMessage();

        }
        
        ie.processMessageEvent (message);
        
    }    
    
    public void write(String s) throws IOException {
        
        PrintWriter pw = new PrintWriter(echo.getOutputStream(), true);
        
        pw.write(s);
        pw.flush();
        
    }  
    
    public void read() throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(echo.getInputStream()));
        
        int c;
        
        String response = "";
            
        try {  
            
            for (c = br.read(); c != LF; c = br.read())
            {
                response += (char)c;
            }
            
            System.out.println( response );

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        ie.processMessageEvent (response);

    }    
    
    public void Close() throws IOException {

        echo.close();
        
    }    
  
}
