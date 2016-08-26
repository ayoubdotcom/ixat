package ixat.ixat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    // User appuye sur linearlayout2
    public void aleatoireClick(View view)
    {
        Intent startAleatoir = new Intent(this, publicityActivity.class);
        startActivity(startAleatoir);
    }

    // User appuye sur linearlayout3
    public void choixClick(View view)
    {
        Intent startChoix = new Intent(this, GamesActivity.class);
        startActivity(startChoix);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // cacher la barre de statut
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed()
    {

    }

    public void returnMainOnClick(View v)
    {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
    }
}
