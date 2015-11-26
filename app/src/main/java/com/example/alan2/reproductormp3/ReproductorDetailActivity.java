package com.example.alan2.reproductormp3;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An activity representing a single Reproductor detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ReproductorListActivity}.
 */
public class ReproductorDetailActivity extends AppCompatActivity {
    // Media Player
    private MediaPlayer mp;
    private ImageButton btnPlay;
    int ban_play=0;
    public static final String ARG_ITEM_ID = "item_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        mp = new MediaPlayer();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ReproductorDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ReproductorDetailFragment.ARG_ITEM_ID));
            ReproductorDetailFragment fragment = new ReproductorDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.reproductor_detail_container, fragment)
                    .commit();
        }

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ReproductorListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void play_song(View v){
        if(v.getId() == R.id.btnPlay){
            if(ban_play==0) {
                ban_play=1;
                playSong(0);
            }else{
                if(ban_play==1){//pausar
                    ban_play++;
                    if(mp.isPlaying()) {
                        if (mp != null) {
                            mp.pause();
                            // Changing button image to play button
                            btnPlay.setImageResource(R.drawable.btn_play);
                        }
                    }
                }else{//resumir
                    ban_play=1;
                    if(mp!=null){
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        }
    }
    public void  playSong(int songIndex){
        songIndex=Integer.parseInt(getIntent().getStringExtra(ARG_ITEM_ID));
        // Play song
        ArrayList<cancion> songsList=SongManager.getSongsList();
        try {
            if(mp.isPlaying()) {
                if (mp != null) {
                    mp.stop();
                }
            }
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).getRuta());
            mp.prepare();
            mp.start();
            // Displaying Song title
            String songTitle = songsList.get(songIndex).getNombre();
            //songTitleLabel.setText(songTitle);

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            //songProgressBar.setProgress(0);
            //songProgressBar.setMax(100);

            // Updating progress bar
            //updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
