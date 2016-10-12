/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bpVerifone;

/**
 *
 * @author brtperry
 */
public class Transaction 
{
    public String RecordType;
    public String AccountNum;
    public String TransType;
    public String Modifier;
    public char BillID;
    public char Pan;
    public char CSC;
    public char Expiry;
    public char IssueNum;
    public char Start;
    public double TransVal;
    public double CashBack;
    public char BankAccNum;
    public char SortCode;
    public char ChequeNum;
    public char ChequeType;
    public char CardholderName;
    public char CardholderAddress;
    public char Eft;
    public char AuthSource;
    public char AuthCode;
    public char TransDate;
    public String Reference;
    public String AccountID; 

    public String Serialize()
    {
        String data = "Field1: %s, Name: %s, D1: %f";
        
        return String.format(data, "Toast", "Jam", 0.0001);

    }
}
