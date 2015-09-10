package com.example.ganesh.garuda;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.SSHExec;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;

import java.io.ByteArrayOutputStream;
import java.util.Properties;


public class opencamera extends ActionBarActivity {

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_opencamera);
        new AsyncTask<Integer, Void, Void>(){

            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }


        }.execute(1);

    }
    public static void executeRemoteCommand()
            throws Exception {

        try {
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("pi");
            scp.setPort(portSSH);
            scp.setPassword("raspberry");
            scp.setCommand("raspivid -o - -t 99999 -vf -hf -w 640 -h 360 -fps 20  |cvlc -vvv stream:///dev/stdin --sout '#standard{access=http,mux=ts,dst=:8090}' :demux=h264 &");
            scp.setTrust( true );
            scp.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opencamera, menu);
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
