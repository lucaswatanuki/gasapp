package br.unicamp.ft.l202143_f197054;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    private EditText etData, etLitros;
    private RadioGroup radioGroup;
    private Button btnUpdate;
    private View view;
    String cData, litrao, cTipo;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etData = findViewById(R.id.etEditData);
        etLitros = findViewById(R.id.etEditLitros);
        radioGroup = findViewById(R.id.rgEditTipo);
        btnUpdate = findViewById(R.id.btnUpdate);

        Bundle intent = getIntent().getExtras();
        if (intent != null){
            cData = intent.getString("cData");
            etData.setText(cData);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });

    }

    private void updateDatabase() {
        final String data = etData.getText().toString();
        String tipoCombustivel = "";
        try {

            int id = radioGroup.getCheckedRadioButtonId();
            if (id == R.id.rbEtanol) {
                tipoCombustivel = "Etanol";
            } else {
                tipoCombustivel = "Gasolina";
            }
        } catch (Exception erro) {
            erro.printStackTrace();
            Toast.makeText(EditActivity.this, "Erro ao salvar informações", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("historico");

        Query query = mRef.orderByChild("data").equalTo(cData);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    dataSnapshot1.getRef().child("data").setValue(data);
                }
                Toast.makeText(EditActivity.this, "Update com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
