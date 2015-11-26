package com.example.alan2.reproductormp3;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by alan2 on 25/11/2015.
 */
public class SongManager {
    // SDCard Path
    String MEDIA_PATH = new String("/sdcard");
    private static ArrayList<cancion> songsList = new ArrayList<cancion>();

    // Constructor
    public SongManager(File file){
        setSongsList(getPlayList(file));
    }

    public static ArrayList<cancion> getSongsList() {
        return songsList;
    }

    public static void setSongsList(ArrayList<cancion> songsList) {
        SongManager.songsList = songsList;
    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     * */

    public ArrayList<cancion> getPlayList(File parentDir){
        String ruta="/";
        ArrayList<cancion> inFiles = new ArrayList<cancion>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles=agregarCanciones(getPlayList(file),inFiles);
            } else {
                if(file.getName().endsWith(".mp3")||file.getName().endsWith(".MP3")){
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(file.getPath());
                    String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                    String artista= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    String nombre= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    String año= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                    String author= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
                    String albumartist= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);

                    cancion song = new cancion();
                    song.setAlbum(albumName);
                    song.setArtista(artista);
                    song.setNombre(nombre);
                    song.setRuta(file.getPath()+"");
                    song.setAlbumartist(albumartist);
                    song.setAño(año);
                    song.setAuthor(author);
                    song.setNombre_completo(file.getName().substring(0, (file.getName().length() - 4)));
                    // Adding each song to SongList
                    inFiles.add(song);
                }
            }
        }

           /* File file2[] = home.listFiles(new FileExtensionFilter());
            if(file2!=null) {
                Log.d("Files", "Size: " + file2.length);
                if (home.listFiles(new FileExtensionFilter()).length > 0) {
                    for (File file : home.listFiles(new FileExtensionFilter())) {
                        cancion song = new cancion();
                        song.setNombre(file.getName().substring(0, (file.getName().length() - 4)));
                        song.setRuta(file.getPath());

                        // Adding each song to SongList
                        songsList.add(song);
                    }
                }
            }*/
        // return songs list array
        return inFiles;
    }

    private ArrayList<cancion> agregarCanciones(ArrayList<cancion> playList,ArrayList<cancion> inFiles ) {
        for (cancion ca : playList) {
            inFiles.add(ca);
        }
        return inFiles;
    }

    /**
     * Class to filter files which are having .mp3 extension
     * */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}
