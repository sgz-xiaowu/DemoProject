package com.myle.favorite_album;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FavoriteMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_favorite_main, menu);
        return true;
    }
}
