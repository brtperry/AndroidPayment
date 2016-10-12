/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

/**
 *
 * @author brtperry
 */
public interface IEvent {
    
    /**
     * 
     * @author brtperry
     * @param String  sends a process message. 
     */
    public void processMessageEvent (String s);
    
    /**
     * 
     * @author Brett Perry
     * @param Output  sends the final output result. 
     */
    public void finalOutputEvent (Output out);
}
