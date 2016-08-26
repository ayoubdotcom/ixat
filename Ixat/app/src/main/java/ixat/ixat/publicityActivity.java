package ixat.ixat;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class publicityActivity extends AppCompatActivity {

    private VideoView videoView;
    private DatabaseReference dataRef;
    private List<ApplicationInfo> appInfoList;
    private String gamePackage;
    //long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);
        videoView = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://ixat.ixat/"+R.raw.skittles;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        dataRef = FirebaseDatabase.getInstance().getReference("jeux");
        ArrayList<ApplicationInfo> selectedItems = new ArrayList<ApplicationInfo>();

        appInfoList = new ArrayList<>();


        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    appInfoList.add(postSnapshot.getValue(ApplicationInfo.class));
                }
                int nbJeux = appInfoList.size();
                Random rn = new Random();
                int jeu = rn.nextInt(nbJeux);
                gamePackage = appInfoList.get(jeu).processName;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration = videoView.getDuration();
            }
        });
        Log.i(getPackageName(), "duration: " + duration);*/
        videoView.start();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("publicite");

        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Publicite pub = mutableData.getValue(Publicite.class);
                if(pub == null)
                {
                    return Transaction.success(mutableData);
                }
                else{
                    int nbVue;
                    nbVue = pub.getNbAffiche();
                    nbVue++;
                    Log.e("Nombre de vue:","" + nbVue);
                    pub.setNbAffiche(nbVue);
                    pub.setDescriptionPub("Description nouveau");
                }
                mutableData.setValue(pub);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.e("Etat de Transaction:","Terminer");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(gamePackage);
                startActivity(launchIntent);
                Intent serviceIntent = new Intent(getApplicationContext(), FloatingViewService.class);
                serviceIntent.putExtra("packageName",gamePackage);
                startService(serviceIntent);

                finish();

            }
        }, 21000);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
   /* public void gameOnClick(View view)
    {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.wishabi.flipp");
        startActivity(launchIntent);
    }*/

    @Override
    public void onBackPressed()
    {

    }
}
