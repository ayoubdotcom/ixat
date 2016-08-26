package ixat.ixat;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView animatedimgvw;
    private ImageView logoimgvw;
    private LayoutInflater inflater;
    private View layout;
    private PopupWindow pw;
    private Button Close;
    private EditText keyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.popup,(ViewGroup) findViewById(R.id.popup_1));

        Close = (Button)layout.findViewById(R.id.close_popup);
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyText = (EditText)layout.findViewById(R.id.keyEditText);
                String keyword = keyText.getText().toString();
                if(keyword.contains("ixatvp"))
                {
                    Intent startManager = new Intent(getApplicationContext(), ManagerActivity.class);
                    startActivity(startManager);
                    pw.dismiss();
                }
                else
                    pw.dismiss();


            }
        });


        animatedimgvw = (ImageView)findViewById(R.id.animatedImageView);
        logoimgvw = (ImageView)findViewById(R.id.logoImageView);
        String animepath = "android.resource://ixat.ixat/"+R.raw.appuyerici;
        String logopath = "android.resource://ixat.ixat/"+R.raw.logoixat;
        Uri uri = Uri.parse(logopath);
        logoimgvw.setImageURI(uri);
        Ion.with(animatedimgvw).load(animepath);
    }


    public void mainOnClick(View view)
    {
        Intent startAccueil = new Intent(this, AccueilActivity.class);
        startActivity(startAccueil);


        /*long startTime = 0;
        long stopTime = 0;
        long timeLong;

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            startTime = System.currentTimeMillis();
        }
        if(motionEvent.getAction() == MotionEvent.ACTION_UP)
        {
            stopTime = System.currentTimeMillis();
        }

        timeLong = stopTime - startTime;

        Toast.makeText(this, "" + timeLong, Toast.LENGTH_SHORT);

        if (timeLong > 3)
        {
            Intent Choixjeux = new Intent(this, ChoixJeuActivity.class);
            startActivity(Choixjeux);
        }
        else
        {
            Intent startAccueil = new Intent(this, AccueilActivity.class);
            startActivity(startAccueil);
        }
*/
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent startServiceIntent = new Intent(context, MainActivity.class);
            context.startService(startServiceIntent);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public void choixjeuClick(View view)
    {
        pw = new PopupWindow(layout, 300, 370, true);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed()
    {

    }
}
