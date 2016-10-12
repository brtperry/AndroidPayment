/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;


/**
 *
 * @author brtperry
 */
public class EventNotifier {
    
    private IEvent ie;
    
    /**
     * EventNotifier class constructor. 
     *
     * @param  IEvent  interface class for passing messages.
     */      
    public EventNotifier (IEvent event) 
    {
        // Save the event object for later use.
        ie = event; 
    }
    
    /**
     * Returns a boolean value determining if logging on 
     * was successful. 
     * <p>
     * In test this method will always return true. 
     *
     * @param  LoginRecord  username and password used for logging on.
     * @return boolean
     */     
    public boolean Login(LoginRecord lr)
    {
        return true;
    }

    /**
     * Method  a boolean value determining if logging on 
     * was successful. 
     * <p>
     * In test this method will always return true. 
     *
     * @param  double  value of the transaction.
     */     
    public void doWork (double thf) 
    {
        int five = 5;
        
        double result = thf * five;
        
        for (int i = 0; i < five; ++i) {
                            
            ie.processMessageEvent ("Message " + i);
                
            try {
 
                Thread.sleep(50);
            
            } catch (InterruptedException ex) {
            
                System.out.println(ex.getMessage());
            }
        }
        
        Output op = new Output();
        op.Field0 = "Ok";
        //op.Field1 = result;        

        ie.finalOutputEvent (op);
    }     
}
