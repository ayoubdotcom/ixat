package ixat.ixat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GamesActivity extends AppCompatActivity {

    private GridView gridView;
    private Button btn;


    private Games[] game;
    private Games game1;
    private Games game2;
    private Games game3;
    private Games game4;

    private String jeuDescription;
    private Drawable icon;
    private ApplicationInfo apinfo;
    private VideoView videoView;
    private int position1;
    private DatabaseReference dataRef;
    private List<Games> jeuxList;
    private List<ApplicationInfo> appInfoList;
    private List<Publicite> pubList;
    private Publicite pub;
    int nbVue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        jeuxList = new ArrayList<>();
        appInfoList = new ArrayList<>();
        pubList = new ArrayList<>();
        gridView = (GridView)findViewById(R.id.gridView);

        listJeux();




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // lancer la publicite
                position1 = position;
                setContentView(R.layout.activity_publicity);
                videoView = (VideoView) findViewById(R.id.videoView);
                String path = "android.resource://ixat.ixat/" + R.raw.skittles;
                Uri uri = Uri.parse(path);
                videoView.setVideoURI(uri);
                videoView.requestFocus();
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


                // lancer le jeu selectionne apres la fin de la publicite
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(jeuxList.get(position).getGameUri());
                        startActivity(launchIntent);
                        Intent serviceIntent = new Intent(getApplicationContext(), FloatingViewService.class);
                        serviceIntent.putExtra("packageName",jeuxList.get(position).getGameUri());
                        startService(serviceIntent);


                    }
                }, 21000);

            }
        });


    }

    private void listJeux()
    {

        dataRef = FirebaseDatabase.getInstance().getReference("jeux");
        ArrayList<ApplicationInfo> selectedItems = new ArrayList<ApplicationInfo>();


        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    appInfoList.add(postSnapshot.getValue(ApplicationInfo.class));
                }




                try
                {
                    for (ApplicationInfo appInfo : appInfoList) {
                        icon = getPackageManager().getApplicationIcon(appInfo.processName);
                        apinfo = getPackageManager().getApplicationInfo(appInfo.processName, PackageManager.GET_META_DATA);
                        jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
                        Games game = new Games(appInfo.processName,jeuDescription,icon);
                        jeuxList.add(game);
                    }
                }
                catch(Exception ex)
                {
                    ex.getMessage();
                }


                if(!jeuxList.isEmpty())
                {

                    gridView.setAdapter(new GamesAdapter(getApplication(), jeuxList));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*
        try {



            icon = getPackageManager().getApplicationIcon("com.supercell.clashroyale");
            apinfo = getPackageManager().getApplicationInfo("com.supercell.clashroyale", 1);
            jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
            game1 = new Games("com.supercell.clashroyale", jeuDescription, icon);

            icon = getPackageManager().getApplicationIcon("com.natenai.glowhockey");
            apinfo = getPackageManager().getApplicationInfo("com.natenai.glowhockey", 1);
            jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
            game2 = new Games("com.natenai.glowhockey", jeuDescription, icon);

            icon = getPackageManager().getApplicationIcon("com.natenai.glowhockey2");
            apinfo = getPackageManager().getApplicationInfo("com.natenai.glowhockey2", 1);
            jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
            game3 = new Games("com.natenai.glowhockey2", jeuDescription, icon);

            icon = getPackageManager().getApplicationIcon("me.msqrd.android");
            apinfo = getPackageManager().getApplicationInfo("me.msqrd.android", 1);
            jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
            game4 = new Games("me.msqrd.android", jeuDescription, icon);


        }
        catch(Exception ex){
            System.out.print(ex.getMessage());
        }


        game = new Games[]{game1,game2,game3,game4};

        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new GamesAdapter(getApplication(), game));
*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed()
    {

    }

    public void returnAccueilOnClick(View v)
    {
        Intent back = new Intent(getApplicationContext(), AccueilActivity.class);
        startActivity(back);
    }

}


