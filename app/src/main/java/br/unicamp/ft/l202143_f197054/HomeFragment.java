package br.unicamp.ft.l202143_f197054;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private EditText etData;
    private TextView tvData;
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

    private DatePickerDialog.OnDateSetListener onDateSetListener;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }

        etData = view.findViewById(R.id.etData);
        etOdometro = view.findViewById(R.id.etOdometro);
        etPreco = view.findViewById(R.id.etPreco);
        etTotal = view.findViewById(R.id.etTotal);
        etLitros = view.findViewById(R.id.etLitros);
        btnAdd = view.findViewById(R.id.btnSalvar);
        spinnerPagamento = view.findViewById(R.id.spinnerPagamento);
        spinnerPosto = view.findViewById(R.id.spPosto);
        rgTipo = view.findViewById(R.id.rgTipo);
        tvData = view.findViewById(R.id.tvData);


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //Listener para calculo dinamico do total (Preço x Litro)
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double preco = 0d;
                Double litro = 0d;
                try {
                    preco = Double.parseDouble(etPreco.getText().toString());
                    litro = Double.parseDouble(etLitros.getText().toString());
                } catch (NumberFormatException e) {
                }
                Double resultado = (preco * litro);
                etTotal.setText(resultado.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etLitros.addTextChangedListener(textWatcher);


        //Listener para aparecer calendário calendário ao selecionar Data

        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
       });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = (String.valueOf(day) + '/' + String.valueOf(month) + "/" + String.valueOf(year));
                etData.setText(date);
            }
        };


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
