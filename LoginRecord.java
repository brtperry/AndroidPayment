/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

/**
 *
 * @author brtperry
 */
public class LoginRecord 
{
    public String Username;
    public String Password; 
    
    public String[] getLoginRecord()
    {
        String[] values = new String[2];
        
        values[0] = "1234";
        values[1] = "1234";
        
        return values;     
    }
    
    public String formatLoginRecord() {
        
        return "L2," + Username + "," + Password + ",4\r\n";
    }
}
