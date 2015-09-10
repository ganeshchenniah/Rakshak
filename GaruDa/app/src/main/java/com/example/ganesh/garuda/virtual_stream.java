package com.example.ganesh.garuda;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.tools.ant.taskdefs.optional.ssh.SSHExec;


public class virtual_stream extends ActionBarActivity {

public static Button button1,button2,button3;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	Toast.makeText(virtual_stream.this,"Please follow the Steps", Toast.LENGTH_SHORT).show();
	Toast.makeText(virtual_stream.this,"Open the camera which turns on the camera for infinite Time", Toast.LENGTH_SHORT).show();
	Toast.makeText(virtual_stream.this,"Use Stream to view the Live Video", Toast.LENGTH_SHORT).show();
	Toast.makeText(virtual_stream.this,"When you are done please kill camera", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_virtual_stream);
        button1 = (Button)findViewById(R.id.opencamera);
        button1.setOnClickListener(onClickListener);
        button2 = (Button)findViewById(R.id.streaming);
        button2.setOnClickListener(onClickListener);
        button3 = (Button)findViewById(R.id.killcamera);
        button3.setOnClickListener(onClickListener);

}
private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.opencamera:
                    Intent intent1 = new Intent("com.example.ganesh.garuda.opencamera");
		            startActivity(intent1);
                    break;
                case R.id.streaming:
                    Intent intent2 = new Intent("com.example.ganesh.garuda.stream");
                    startActivity(intent2);
                    break;
                case R.id.killcamera:
                    Intent intent3 = new Intent("com.example.ganesh.garuda.killcamera");
                    startActivity(intent3);
                    break;
            }
        }
    };

 
public static void executeRemoteCommand()
{
            org.apache.tools.ant.taskdefs.optional.ssh.SSHExec scp = new SSHExec();
            int portSSH = 2222;
            scp.setHost("106.51.36.110");
            scp.setUsername("pi");
            scp.setPort(portSSH);
            scp.setPassword("raspberry");
            scp.setCommand("sh /home/stream.sh &");
            scp.setTrust( true );
            scp.execute();
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_virtual_stream, menu);
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
