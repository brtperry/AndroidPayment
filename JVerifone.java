/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

/**
 *
 * @author brtperry
 */
public class JVerifone {
    

    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Beginning verifone callback.");
       
        Verifone vf = new Verifone("192.2.107.1");
        
        if (!vf.Connect()) { return; }
        
        vf.Login();
        
        
        /** Old code
         * 
         * LoginRecord lr = new LoginRecord();
         * lr.Username = "1234";
         * lr.Password = "1234";
         * 
         * Callback cb = new Callback();
         * cb.Payment(lr, 20.66);
         * 
         * 
         * Transaction tx = new Transaction();
         * System.out.println(tx.Serialize());
         * 
         * VerifoneSocket vs = new VerifoneSocket("192.2.107.1");
         * boolean connected = vs.Connect();
         * 
         *  try {
            
                vs.write("L2,1234,1234,4\r\n");
            
                String data = vs.read();
            
                if (data.startsWith("100", 0))
                {
                    vs.write("CONTTXN,2,\r\n");
                }
            
                vs.write("T,001,01,0000,50\r\n");

                vs.Close();
            
            } catch (IOException ex) {
                                
                System.out.println(ex.getMessage()); 
            } 
         * 
         */

    }
}






