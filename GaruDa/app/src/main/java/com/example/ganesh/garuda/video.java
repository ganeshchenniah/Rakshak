package com.example.ganesh.garuda;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;


import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenVideo;
import com.kbeanie.imagechooser.api.VideoChooserListener;
import com.kbeanie.imagechooser.api.VideoChooserManager;

public class video extends ActionBarActivity implements
        VideoChooserListener {
    private VideoChooserManager videoChooserManager;

    private ProgressBar pbar;

    private ImageView imageViewThumb;

    private ImageView imageViewThumbSmall;

    private VideoView videoView;

    private String filePath;

    private int chooserType;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chooser);

        pbar = (ProgressBar) findViewById(R.id.pBar);
        pbar.setVisibility(View.GONE);

     //   imageViewThumb = (ImageView) findViewById(R.id.imageViewThumbnail);
     //   imageViewThumbSmall = (ImageView) findViewById(R.id.imageViewThumbnailSmall);

        videoView = (VideoView) findViewById(R.id.videoView);


    }

    
  public void pickVideo(View view) {
        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO);
        videoChooserManager.setVideoChooserListener(this);
        try {
            videoChooserManager.choose();
            pbar.setVisibility(View.VISIBLE);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoChosen(final ChosenVideo video) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pbar.setVisibility(View.GONE);
                if (video != null) {
                    videoView.setVideoURI(Uri.parse(new File(video
                            .getVideoFilePath()).toString()));
                    videoView.start();
                 //   imageViewThumb.setImageURI(Uri.parse(new File(video
                   //         .getThumbnailPath()).toString()));
                    //imageViewThumbSmall.setImageURI(Uri.parse(new File(video
                      //      .getThumbnailSmallPath()).toString()));
                }
            }
        });
    }

  @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                pbar.setVisibility(View.GONE);
                Toast.makeText(video.this, reason,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_CAPTURE_VIDEO || requestCode == ChooserType.REQUEST_PICK_VIDEO)) {
            if (videoChooserManager == null) {
                reinitializeVideoChooser();
            }
            videoChooserManager.submit(requestCode, data);
        } else {
     //       pbar.setVisibility(View.GONE);
        }
    }

    // Should be called if for some reason the VideoChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeVideoChooser() {
        videoChooserManager = new VideoChooserManager(this, chooserType, true);
        videoChooserManager.setVideoChooserListener(this);
        videoChooserManager.reinitialize(filePath);
    }
 @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }

            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}


