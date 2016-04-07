package com.brett.vpayment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity
{
    private Button button;
    private TextView textview; 
    private EditText textedit;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        addListenerOnButton();
    }
    
    public void addListenerOnButton() {
 
        button = (Button) findViewById(R.id.button1);
 
        button.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                
                button.setText("Please wait...");
                button.setEnabled(false);
                
                textedit = (EditText) findViewById(R.id.amount1);
                String amount = textedit.getText().toString();
                    
                new Payment().execute("2.2.107.60", amount);
                
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mkyong.com"));
                //startActivity(browserIntent);
             } 
        });
    }
        
    public void updateUi(String... params) {
            
        Toast.makeText(getApplicationContext(), params[0], Toast.LENGTH_SHORT).show();
    }
        
    public void receiveResponse(Response result) {
        
        button = (Button) findViewById(R.id.button1);
        button.setText("Make Payment");
        button.setEnabled(true); 
        
        textview = (TextView) findViewById(R.id.output);
        textview.setText(result.Pan);
        
        textedit = (EditText) findViewById(R.id.amount1);
        textedit.setText(result.Amount);
        
        Toast.makeText(getApplicationContext(), result.Pan, Toast.LENGTH_LONG).show();
    }
        
    private class Payment extends AsyncTask<String, String, Response> {

        @Override
        protected Response doInBackground(String... params) {
            
            publishProgress("Connecting to " + params[0]);
            publishProgress("Sending payment of £" + params[1]);
          
            JSocket js = new JSocket(params[0]);
            
            Response response = new Response(); 
            
            response.Pan = "698483923402482";
            response.Amount = params[1];
            
            boolean connected = js.Connect();
            
            if (connected) {
            
                try {
                
                    //js.write("T,001,01,0000,,,,,,,20,0,,,,,,,,,,,20,\r\n");
                    js.write("T,001,01,0000,,,,,,," + response.Amount + ",0,,,,,,,,,,,20,\r\n");
                
                    String data = "";
                
                    while ("".equals(data)) {

                        data = js.read();
                        
                        if (data.isEmpty()) { continue; }
                        
                        publishProgress(data);
                        
                        int result = Integer.parseInt(data.substring(0, data.indexOf(",")));
                        
                        switch (result) {
                            
                            case 100:
                                data = "";
                                break;
                                
                            case 7:
                                break;
                                
                            case 0:
                                response.setValues(data); 
                                data = "Finished";  
                                break;
                                
                            case -99:
                                break;
                            
                        }
                    }   
                
                    js.Close();                   
                
                } catch (IOException ex) {
                    
                    publishProgress(ex.getMessage());

                }
            } else {
            
                publishProgress ("Unable to connect to server!\r\n\nMesage from socket is\n\n" + js.LastMessage);
            }
            
            return response;

        }      

        @Override
        protected void onPostExecute(Response result) { 
            receiveResponse(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... values) {
          updateUi(values);
        }
    } 
    
    public class Response {
    
        int Result;
        String Pan;
        String ExpiryDate;
        int IssueNumber;    
        Date TransactionDateTime;
        int MerchantNumber;
        int TerminalId;
        String SchemeName;
        int Eft;
        String Authcode;
        String CustomerVerification;
        String Amount;
    
        public Response() {}  
    
        public void setValues(String val) {

            String[] values = val.split(",");

            Result                  = Integer.parseInt(values[0]);
            Pan                     = values[5];
            ExpiryDate              = values[6];
            IssueNumber             = values[7].isEmpty() ? 0 : Integer.parseInt(values[7]);
            MerchantNumber          = Integer.parseInt(values[10]);
            TerminalId              = Integer.parseInt(values[11]);
            SchemeName              = values[12].trim();
            Eft                     = Integer.parseInt(values[14]);
            Authcode                = values[15];
            CustomerVerification    = values[17];

            try {

                TransactionDateTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(values[9]);

            } catch (ParseException pe) {

                TransactionDateTime = new Date();          
            }  
        }
  
        @Override 
        public String toString() {

            StringBuilder result = new StringBuilder();

            Field[] fields = this.getClass().getDeclaredFields();

            for ( Field field : fields  ) {

                try {

                    result.append( field.get(this).toString() );

                } catch ( IllegalAccessException ex ) {

                    System.out.println(ex.getMessage());

                }
            }
            return result.toString();        
        }

    }
    
    public class JSocket {

        private InetSocketAddress address;
        private Socket echo = null;
        private static final int LF = 0x0a; // Linefeed character or \n
        private static final int CR = 0x0d;
        private static final int port = 25000;
        //private static final int timeout = 10000;
        public String LastMessage;

        public JSocket(String ip) {  

            address = new InetSocketAddress(ip, port);
        }

        public boolean Connect(){

            try {

                echo = new Socket();
                //echo.setSoTimeout(timeout);
                echo.connect(address);
                /*
                 * Verifone advise is to remove any socket timeout.
                 * and use a system timer if necessary.
                 * 
                 * echo.setSoTimeout(30000);
                 */ 

                return echo.isConnected();

            } catch (UnknownHostException ex) {
                
                LastMessage = ex.getMessage(); 

                return false;

            } catch (IOException ex) {

                LastMessage = ex.getMessage();  

                return false;
            }
        }  

        public void write(String s) throws IOException {

            OutputStream O = echo.getOutputStream();

            O.write(s.getBytes());
            O.flush();        
        }  

        public String read() throws IOException {

            BufferedReader br = new BufferedReader(new InputStreamReader(echo.getInputStream()));

            String response = br.readLine();

            return response.isEmpty() ? "" : response.trim();
        }    

        public void Close() throws IOException {
            
            if (echo != null) {
                
                if (echo.isConnected()) {
                    
                    echo.close(); 
                    
                }
            }      
        } 

    }    
    
}


