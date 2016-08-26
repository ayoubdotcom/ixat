package ixat.ixat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerActivity extends AppCompatActivity {

    private TextView nbVueTextView;
    private TextView choisirJeuxTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        nbVueTextView = (TextView)findViewById(R.id.nbVue2textView);
        choisirJeuxTextView = (TextView)findViewById(R.id.choixJeuxTextView);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("publicite");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nbVue = dataSnapshot.getValue(Publicite.class).getNbAffiche();
                nbVueTextView.setText(""+nbVue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        choisirJeuxTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startChoixJeu = new Intent(getApplicationContext(), ChoixJeuActivity.class);
                startActivity(startChoixJeu);
            }
        });


    }
}
