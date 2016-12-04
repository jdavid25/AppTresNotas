package andorid.appnotas.com.appnotas;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference personaRef;

    private EditText txtnc1;
    private EditText txtnc2;
    private EditText txtnc3;
    private EditText txtpc1;
    private EditText txtpc2;
    private EditText txtpc3;
    private EditText txtmateria;
    private TextView txtnfinal;
    private Button btenviar;
    private Button bttraer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtnc1= (EditText) findViewById(R.id.txtnc1);
        txtnc2= (EditText) findViewById(R.id.txtnc2);
        txtnc3= (EditText) findViewById(R.id.txtnc3);
        txtpc1= (EditText) findViewById(R.id.txtpc1);
        txtpc2= (EditText) findViewById(R.id.txtpc2);
        txtpc3= (EditText) findViewById(R.id.txtpc3);
        txtmateria= (EditText) findViewById(R.id.txtmateria);
        txtnfinal= (TextView) findViewById(R.id.txtnfinal);
        btenviar= (Button) findViewById(R.id.btenviar);
        bttraer= (Button) findViewById(R.id.bttraer);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("home", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("home", "onAuthStateChanged:signed_out");
                }
            }
        };

        database= FirebaseDatabase.getInstance();
        personaRef = database.getReference("notas");
        // if(personaRef.child("").)

        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*UserProfileChangeRequest usuario = new UserProfileChangeRequest.Builder()
                        .setDisplayName(txtNombre.getText().toString().trim())
                        //.setPhotoUri(Uri.parse("https://fbcdn-sphotos-e-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/30382_10151143006550458_1277185577_n.jpg?oh=75fd5ad8033c23b7deb3f30fbec965c1&oe=58CC7890&__gda__=1490377027_e3d19a221bbcaded909296e7fab737fe"))
                        .build();*/

                //mAuth.getCurrentUser().updateProfile(usuario);
                String nc1=txtnc1.getText().toString();
                Double vnc1=Double.parseDouble(nc1);
                String nc2=txtnc2.getText().toString();
                Double vnc2=Double.parseDouble(nc2);
                String nc3=txtnc3.getText().toString();
                Double vnc3=Double.parseDouble(nc3);
                String pc1=txtpc1.getText().toString();
                Double vpc1=Double.parseDouble(pc1);
                String pc2=txtpc2.getText().toString();
                Double vpc2=Double.parseDouble(pc2);
                String pc3=txtpc3.getText().toString();
                Double vpc3=Double.parseDouble(pc3);
                Double resultado=(vnc1*vpc1/100)+(vnc2*vpc2/100)+(vnc3*vpc3/100);
                String res=String.valueOf(resultado);
                txtnfinal.setText(res);
                personaRef = database.getReference("notas").child(mAuth.getCurrentUser().getUid());
                personaRef.child("materia").child("nombre").setValue(txtmateria.getText().toString());
                personaRef.child("materia").child("nota1").setValue(txtnc1.getText().toString());
                personaRef.child("materia").child("nota2").setValue(txtnc2.getText().toString());
                personaRef.child("materia").child("nota3").setValue(txtnc3.getText().toString());
                personaRef.child("materia").child("por1").setValue(txtpc1.getText().toString());
                personaRef.child("materia").child("por2").setValue(txtpc2.getText().toString());
                personaRef.child("materia").child("por3").setValue(txtpc3.getText().toString());
                personaRef.child("materia").child("nfinal").setValue(res);
                //personaRef.child("dispositivoDondeGuardo").setValue(FirebaseInstanceId.getInstance().getToken());

                personaRef.getParent().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        System.out.println(dataSnapshot.getChildrenCount());

                        //Log.d("Home Activity", "Value is: " + valor);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Home Activity", "Failed to read value.", error.toException());
                    }
                });

            }
        });



    }
}
