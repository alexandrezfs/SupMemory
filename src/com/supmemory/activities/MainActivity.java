package com.supmemory.activities;

import android.app.Activity;
import android.os.Bundle;
import com.supmemory.R;
import com.supmemory.beans.Game;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Game(this);
    }

}
