/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

import java.io.IOException;

/**
 *
 * @author brtperry
 */
public class Verifone implements IEvent  {
    
    private Subscriber  subscribe; 
    public  Output      output;
    
    private String      message;
    
    public Verifone(String ip)
    {
        subscribe = new Subscriber (this, ip);
    }
    
    public boolean Connect()
    {
        subscribe.Connect();
        
        return subscribe.connected;
    }
    
    public void Login() {
        
        if (subscribe.connected) { //message .startsWith("100")
            
            message = "";
            
            try {
                
                subscribe.write("L2,1234,1234,4\r\n");
                
                // get response from login
                subscribe.read();
                
            } catch (IOException ex) {
                
                System.out.println(ex.getMessage());
                
            }
        }        
    }

    void process(String d) {
        
        String[] result = d.split(",");

        output = new Output ();  
        output.Field0 = result[0];
        output.Field1 = result[1];
        output.Field2 = result[2];
        output.Field3 = result[3];

        try {

            switch (output.Field3) {

                case "Ready": 
                    
                    subscribe.write("T,001,01,0000,50\r\n");  

                default: ;

            }                

        } catch (IOException ex) {

            System.out.println(ex.getMessage());

        }            
    }

    @Override
    public void processMessageEvent(String s) {
        
        message = s;
        
        System.out.println(s);
        
        if (s.contains("connected")) { return; }
        
        process(message);       
        
    }

    @Override
    public void finalOutputEvent(Output out) {
        
        output = out;
        
    }
}
