package br.unicamp.ft.l202143_f197054;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private EditText etData;
    private EditText etOdometro;
    private EditText etPreco;
    private EditText etLitros;
    private EditText etTotal;
    private Button btnAdd;
    private Spinner spinnerPagamento, spinnerPosto;
    private RadioGroup rgTipo;
    private String tipoCombustivel, pagamento, data, posto;
    private Double total, litros, preco, odometro;
    private DatabaseReference mFirebaseDatabaseReference;
    TextWatcher textWatcher;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etData = view.findViewById(R.id.etData);
        etOdometro = view.findViewById(R.id.etOdometro);
        etPreco = view.findViewById(R.id.etPreco);
        etTotal = view.findViewById(R.id.etTotal);
        etLitros = view.findViewById(R.id.etLitros);
        btnAdd = view.findViewById(R.id.btnSalvar);
        spinnerPagamento = view.findViewById(R.id.spinnerPagamento);
        spinnerPosto = view.findViewById(R.id.spPosto);
        rgTipo = view.findViewById(R.id.rgTipo);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double preco = Double.parseDouble(etPreco.getText().toString());
                Double litro = Double.parseDouble(etLitros.getText().toString());
                etLitros.removeTextChangedListener(textWatcher);

                Double resultado = (preco * litro);
                etTotal.setText(resultado.toString());

                etLitros.addTextChangedListener(textWatcher);
            }

            @Override
            public void afterTextChanged(Editable s) {
                etLitros.addTextChangedListener(textWatcher);
            }
        };

        etLitros.addTextChangedListener(textWatcher);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    int id = rgTipo.getCheckedRadioButtonId();
                    if (id == R.id.btnEtanol) {
                        tipoCombustivel = "Etanol";
                    } else {
                        tipoCombustivel = "Gasolina";
                    }

                    litros = Double.parseDouble(etLitros.getText().toString());
                    preco = Double.parseDouble(etPreco.getText().toString());
                    total = Double.parseDouble(etTotal.getText().toString());
                    pagamento = spinnerPagamento.getSelectedItem().toString();
                    posto = spinnerPosto.getSelectedItem().toString();
                    odometro = Double.parseDouble(etOdometro.getText().toString());
                    data = etData.getText().toString();


                    Abastecimentos.addItem(data, odometro, tipoCombustivel, total, litros, preco, pagamento, posto);
                    HistoricoDB historicoDB = new HistoricoDB(data, tipoCombustivel, litros, total, posto);
                    mFirebaseDatabaseReference.child("historico").push().setValue(historicoDB);

                    Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                } catch (Exception erro) {
                    erro.printStackTrace();
                    Toast.makeText(getContext(), "Erro ao salvar informações", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
