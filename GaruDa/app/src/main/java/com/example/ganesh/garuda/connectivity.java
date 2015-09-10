package com.example.ganesh.garuda;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHExec;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;


public class connectivity extends ActionBarActivity {


	String command ;
	private Button button;
	private TextView finalResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	Toast.makeText(connectivity.this,"Click Button For Connection Check", Toast.LENGTH_LONG).show();	
	
        setContentView(R.layout.activity_connectivity);
	button = (Button) findViewById(R.id.btn_do_it);
        finalResult = (TextView) findViewById(R.id.Connected);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }
        });
    }

	   private class AsyncTaskRunner extends AsyncTask<Void, Void, Boolean> {

        private String resp;
	ProgressDialog simpleWaitDialog;
        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isConnect = false;
            String TAG = "rakshak";
            String str;
                  JSch jsch = new JSch();
                 // Session session = jsch.getSession(username, hostname, port);
            Session session = null;
            try {
                session = jsch.getSession("pi", "106.51.36.110", 2222);
            } catch (JSchException e) {
                e.printStackTrace();
            }
            session.setPassword("raspberry");

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
                   session.setConfig(prop);

            try {
                      session.connect(10000);
            } catch (Exception e) {

                Log.v(TAG, "Unsuccessful Connection");
                e.printStackTrace();
            }

                  isConnect = session.isConnected();

		  if (isConnect) {
			  Log.v(TAG, "Connected");
			  try {
				  upload();
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
			  try {
				  delete();
			  } catch (Exception e) {
				  e.printStackTrace();
			  }

			  str = "Connected";
		  } else {
			  Log.v(TAG, "Connection Failed");
			  str = "Connection Failed";
		  }
//		  return (str);
               return (isConnect); 
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final Boolean success) {
		simpleWaitDialog.dismiss();
            // execution of result of Long time consuming operation
		if(success){
            		finalResult.setText("Connected");
		}else{
            		finalResult.setText("Connection Unsuccessful");
				
		}

        }
	   /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
		simpleWaitDialog = ProgressDialog.show(connectivity.this,
                        "Pls Wait", "Connection Check and Transferring Footage");
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
       // @Override
      //  protected void onProgressUpdate(String... text) {
        //      finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
       // }
    }

    public static void delete()
            throws Exception {

        try {
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("root");
            scp.setPort(portSSH);
            scp.setPassword("garuda");
            scp.setTimeout(20000);
            scp.setCommand("rm -f /home/pi/video/vid*");
            scp.setTrust( true );
            scp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void upload()
            throws Exception {

        try{
            org.apache.tools.ant.taskdefs.optional.ssh.Scp scp = new Scp();
            //   scp.init();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("root");
            scp.setPort(portSSH);
            scp.setPassword("garuda");

            scp.setRemoteFile("root:garuda@106.51.36.110:/home/pi/video/vid*");
            //  scp.setDirMode("root:garuda@106.51.36.110:/home/pi/video/");
            scp.setLocalTodir("/sdcard/rakshak/");
            scp.setTrust(true);
            scp.setFailonerror(true);
            scp.setProject(new Project());
            scp.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connectivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
