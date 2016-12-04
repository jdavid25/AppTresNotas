package andorid.appnotas.com.appnotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SegundaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
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
    private Estudiante estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);


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


        bttraer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pacienteRefCC = database.getReference("estudiante/"+mAuth.getCurrentUser().getUid());

                pacienteRefCC.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(txtmateria.getText().toString().trim())) {
                            dataSnapshot.child(txtmateria.getText().toString().trim());
                            Toast.makeText(getApplicationContext(), "Si existe materia ",
                                    Toast.LENGTH_SHORT).show();

                            estudiante = dataSnapshot.child(txtmateria.getText().toString().trim()).getValue(Estudiante.class);

                            txtnc1.setText(estudiante.getNota1().toString());
                            txtnc2.setText(estudiante.getNota2().toString());
                            txtnc3.setText(estudiante.getNota3().toString());
                            txtpc1.setText(estudiante.getPorc1().toString());
                            txtpc2.setText(estudiante.getPorc2().toString());
                            txtpc3.setText(estudiante.getPorc3().toString());
                            txtnfinal.setText(estudiante.getNfinal().toString());


                        } else {

                            Toast.makeText(getApplicationContext(), "No existe materia",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        System.out.println("Failed to read value." + error.toException());
                    }
                });
            }
        });

        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                estudiante = new Estudiante();
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

                estudiante.setMateria(txtmateria.getText().toString().trim());
                estudiante.setNota1(vnc1.doubleValue());
                estudiante.setNota2(vnc2.doubleValue());
                estudiante.setNota3(vnc3.doubleValue());
                estudiante.setPorc1(vpc1.doubleValue());
                estudiante.setPorc2(vpc2.doubleValue());
                estudiante.setPorc3(vpc3.doubleValue());
                estudiante.setNfinal(resultado.doubleValue());


                mAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pacientesRef = database.getReference("estudiante/"+mAuth.getCurrentUser().getUid()).child(estudiante.getMateria().trim());
                pacientesRef.setValue(estudiante);


            }
        });


    }

}
