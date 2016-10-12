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
public interface ISocket {
    
    public boolean Connect() throws IOException;
    
    /**
     * 
     * @author brtperry
     * @param String  sends a message to the open socket. 
     */
    public void send (String message);
    
    public String[] receive() throws IOException;
    
}
