package br.unicamp.ft.l202143_f197054;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private EditText etData, etLitros;
    private RadioGroup radioGroup;
    private Button btnUpdate;
    private View view;
    String cData, cLitros, cTipo;
    DatabaseReference databaseReference;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_edit, container, false);
        }

        etData = view.findViewById(R.id.etEditData);
        etLitros = view.findViewById(R.id.etEditLitros);
        radioGroup = view.findViewById(R.id.rgEditTipo);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase();
            }
        });
        return view;
    }

    private void updateDatabase() {
        final String data = etData.getText().toString();
        final String litros = etLitros.getText().toString();
        String tipoCombustivel = "";
        try {

            int id = radioGroup.getCheckedRadioButtonId();
            if (id == R.id.btnEtanol) {
                tipoCombustivel = "Etanol";
            } else {
                tipoCombustivel = "Gasolina";
            }
        } catch (Exception erro) {
            erro.printStackTrace();
            Toast.makeText(getContext(), "Erro ao salvar informações", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mFirebaseDatabase.getReference("historico");

        Query query = mRef.orderByChild("data").equalTo(cData);
        final String finalTipoCombustivel = tipoCombustivel;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    dataSnapshot1.getRef().child("data").setValue(data);
                    dataSnapshot1.getRef().child("litros").setValue(litros);
                    dataSnapshot1.getRef().child("tipo").setValue(finalTipoCombustivel);
                }
                Toast.makeText(getContext(), "Update com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
