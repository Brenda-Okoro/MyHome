package com.example.brenda.myhome.interfaces;

import android.media.session.MediaSession;

/**
 * Created by brenda on 3/15/17.
 */
public interface CompleteOperations {
    public void onComplete(MediaSession.Token token);

    public void onComplete(String code);
}
