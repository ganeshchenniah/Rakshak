package com.example.ganesh.garuda;
 import com.pushbots.push.Pushbots;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
 import android.widget.Toast;

 import org.apache.tools.ant.taskdefs.optional.ssh.SSHExec;


public class MainActivity extends ActionBarActivity {

    public static Button button1,button2,button3,button4,button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"Welcome To Rakshak",Toast.LENGTH_SHORT).show();
		Pushbots.sharedInstance().init(this);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(onClickListener);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(onClickListener);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(onClickListener);
        button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(onClickListener);
        button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(onClickListener);
      }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    Intent intent1 = new Intent("com.example.ganesh.garuda.connectivity");
                    startActivity(intent1);
                    break;
                case R.id.button2:
                    Intent intent2 = new Intent("com.example.ganesh.garuda.video");
                    startActivity(intent2);
                    break;
                case R.id.button3:
                    Intent intent3 = new Intent("com.example.ganesh.garuda.capture");
                    startActivity(intent3);
                    break;
                case R.id.button4:

                    Intent intent4 = new Intent("com.example.ganesh.garuda.virtual_stream");
                    startActivity(intent4);
                    break;
                case R.id.button5:
                    Intent intent5 = new Intent("com.example.ganesh.garuda.about");
                    startActivity(intent5);
                    break;

            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
