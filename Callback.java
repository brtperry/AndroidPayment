/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

/**
 *
 * @author brtperry
 */
public class Callback implements IEvent 
{
    private EventNotifier en; 
    
    public Output OutputResult;
    
    /**
     * Callback class constructor. 
     *
     * @author brtperry
     */     
    public Callback() 
    {
        // Create the event notifier and pass ourself to it.
        en = new EventNotifier (this);
    } 

    /**
     * Method to pass a value to the EventNotifier class which
     * is used to exchange data. 
     * <p>
     * This method always returns immediately. 
     *
     * @param  LoginRecord  user and password details
     * @param  value        the amount to pay
     */    
    public void Payment(LoginRecord lr, double value)
    {
        if (!en.Login(lr)) {
            
            return;
        }
        
        en.doWork(value);
    }
    
    // Define the actual handler for the event.
    @Override
    public void processMessageEvent (String s)
    {
        System.out.println(s);
    }      
    
    // Define the actual handler for the event.
    @Override
    public void finalOutputEvent (Output th)
    {
        OutputResult = th;
    }  
    
}
