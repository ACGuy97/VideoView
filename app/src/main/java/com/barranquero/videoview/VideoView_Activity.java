package com.barranquero.videoview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Activity which plays a video and continues playing even when the phone is rotated
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class VideoView_Activity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = (VideoView) findViewById(R.id.videoView);
        // We get the path of our video
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.buzo);

        /* We can use a URI to access our video
        * videoView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"buzo");
        * Uri uri = Uri.parse("android:resource://" + getPackageName() + "/" + R.raw.buzo);
        * videoView.setVideoUri(uri); */

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.suspend();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    /*
    * In this pair of methods, it's not necessary to callback to super
    * We use them, to save the current playtime of the video and restore them later
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int position = videoView.getCurrentPosition();
        outState.putInt("position", position);
        // In the second call to onSaveInstanceState, we check not to modify the playing value

        outState.putBoolean("playing", videoView.isPlaying());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        videoView.seekTo(savedInstanceState.getInt("position"));
        if (!(savedInstanceState.getBoolean("playing")))
            videoView.pause();
    }
}
