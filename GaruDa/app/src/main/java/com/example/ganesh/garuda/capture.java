package com.example.ganesh.garuda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import com.jcraft.jsch.*;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHExec;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;




public class capture extends ActionBarActivity {

	String TAG = "rakshak";
	private TextView Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
	Toast.makeText(capture.this,"Capturing Video For 10 Sec", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_capture);
	Result = (TextView) findViewById(R.id.textView);

        new AsyncTask<Void, Void, Boolean>(){

             ProgressDialog simpleWaitDialog;
            protected void onPreExecute() {
                super.onPreExecute();
                simpleWaitDialog = ProgressDialog.show(capture.this,
                        "Wait", "Downloading Video");
            }
            protected Boolean doInBackground(Void... params) {

               try {
                    executeRemoteCommand();
                    changeFormat();
		            deleteBinFile();
		           upload();
		           delete();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

        protected void onPostExecute(final Boolean success) {
		        Log.v(TAG, "capture : onPostExecute");
			Result.setText("Copied to Mobile ...");

            simpleWaitDialog.dismiss();
        }
        }.execute();

    }

    public static void executeRemoteCommand()
            throws Exception {

        try {
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("root");
            scp.setPort(portSSH);
            scp.setPassword("garuda");
            scp.setCommand("raspivid --width 640 --height 480 --timeout 10000 -vf -hf --output /home/pi/video/vid.h264");
            scp.setTrust( true );
            scp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changeFormat()
            throws Exception {

        try {
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("root");
            scp.setPort(portSSH);
            scp.setPassword("garuda");
	//$(date +'-%m%d%y-%H%M%S')
            scp.setCommand("MP4Box -add /home/pi/video/vid.h264 /home/pi/video/vid$(date +'-%m%d%y-%H%M%S').mp4");
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
            scp.setTrust( true );
            scp.setProject( new Project() );
            scp.execute();
	}
	catch (Exception e) {
            e.printStackTrace();
        }
    }

 public static void deleteBinFile()
            throws Exception {

        try {
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("root");
            scp.setPort(portSSH);
            scp.setPassword("garuda");
            scp.setCommand("rm -f /home/pi/video/vid*.h264");
            scp.setTrust( true );
            scp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            scp.setCommand("rm -f /home/pi/video/vid*");
            scp.setTrust( true );
            scp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capture, menu);
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
