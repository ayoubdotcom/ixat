package ixat.ixat;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChoixJeuActivity extends AppCompatActivity {


    private ArrayList<ApplicationInfo> selectedItems = new ArrayList<ApplicationInfo>();
    private Games game;
    private ListView listView;
    private Button btn;
    private EditText searchEditText;


    private List<ApplicationInfo> apps;
    private String jeuDescription;
    private Drawable icon;
    private ApplicationInfo apinfo;
    private String packageName;
    private VideoView videoView;
    private int position1;
    private ArrayAdapter<String> adapter;
    private CheckedTextView chkdTxtVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_jeu);

        chkdTxtVw = (CheckedTextView)findViewById(R.id.checkedTxtvw);
        listView = (ListView)findViewById(R.id.listView);
        searchEditText = (EditText)findViewById(R.id.searchEditText);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        try {

            apps = getPackageManager().getInstalledApplications(getPackageManager().GET_META_DATA);

        }
        catch(Exception ex){
            ex.getMessage();
        }

        List<String> items = new ArrayList<>();

        try
        {
            for(int i = 0; i < apps.size(); i++)
            {
                packageName = apps.get(i).packageName;
                apinfo = getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                jeuDescription = getPackageManager().getApplicationLabel(apinfo).toString();
                items.add(jeuDescription);
            }
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.list_choisir_jeu, R.id.checkedTxtvw, items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String selectedItem = ((TextView) view).getText().toString();

                ApplicationInfo selectedItem = apps.get(position);

                game = new Games();

                if (!selectedItems.contains(selectedItem)) {
                    selectedItems.add(selectedItem);
                }
                else
                {
                    selectedItems.remove(selectedItem);
                }


            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ChoixJeuActivity.this.adapter.getFilter().filter(s);
                uncheckAllChildrenCascade(listView);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


       // listApp();
    }



    public void ajouterJeuClick(View view)
    {

        ApplicationInfo appInfo;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Games jeu;

       /* for(int i = 0; i < selectedItems.size(); i++)
        {
            appInfo = selectedItems.get(i);
            DatabaseReference myRef = database.getReference("jeux");
            jeu = new Games(appInfo.packageName);
            myRef.setValue(jeu);
        }*/

        DatabaseReference myRef = database.getReference("jeux");
        myRef.setValue(selectedItems);

        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                List<ApplicationInfo> p = new ArrayList<ApplicationInfo>();

                for(MutableData md: mutableData.getChildren())
                {
                    p.add(md.getValue(ApplicationInfo.class));
                }
/*
                if (p.isEmpty()) {
                    return Transaction.success(mutableData);
                }*/


                for(int i = 0; i < p.size(); i++)
                {
                    p.add(selectedItems.get(i));
                }


                // Set value and report transaction success
                mutableData.setValue(p);
                Toast.makeText(getApplicationContext(), "Jeu ajouté avec succès", Toast.LENGTH_LONG).show();
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("Error", "postTransaction:onComplete:" + databaseError);
            }
        });




        /*String items = "";

        for(String item:selectedItems)
        {
            items += "-"+item+"\n";
        }

        Toast.makeText(view.getContext(),"You have selected: ",Toast.LENGTH_SHORT).show();*/
    }



    private void uncheckAllChildrenCascade(ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof CheckedTextView) {
                ((CheckedTextView) v).setChecked(false);
            } else if (v instanceof ViewGroup) {
                uncheckAllChildrenCascade((ViewGroup) v);
            }
        }
    }


   /* private void listApp()
    {

        try {

            apps = getPackageManager().getInstalledApplications(getPackageManager().GET_META_DATA);


        }
        catch(Exception ex){
            ex.getMessage();
        }

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new AppsAdapter(getApplication(), apps));
    }*/
}
