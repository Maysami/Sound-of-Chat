package util;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by elenoon on 12/22/2014.
 */
public class PlayPlaylist {

    private Activity mThis;

    public void PlaySongsFromAPlaylist(int playListID){
    String[] ARG_STRING = {MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.MediaColumns.DATA};

    Uri membersUri = MediaStore.Audio.Playlists.Members.getContentUri("external", playListID);
    Cursor songsWithingAPlayList = mThis.managedQuery(membersUri, ARG_STRING, null, null, null);
    int theSongIDIwantToPlay = 0; // PLAYING FROM THE FIRST SONG
    if(songsWithingAPlayList!=null)

    {
        songsWithingAPlayList.moveToPosition(theSongIDIwantToPlay);
        String DataStream = songsWithingAPlayList.getString(4);
        PlayMusic(DataStream);
        songsWithingAPlayList.close();
    }

}

    public static void PlayMusic(String DataStream){
        MediaPlayer mpObject = new MediaPlayer();
        if(DataStream == null)
            return;
        try {
            mpObject.setDataSource(DataStream);
            mpObject.prepare();
            mpObject.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
