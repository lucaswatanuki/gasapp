package br.unicamp.ft.l202143_f197054;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private Spinner spinnerPagamento;
    private RadioGroup rgTipo;
    private String tipoCombustivel, pagamento, data;
    private Double total, litros, preco, odometro;

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
        rgTipo = view.findViewById(R.id.rgTipo);



        btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        int id = rgTipo.getCheckedRadioButtonId();
                        if (id == R.id.btnEtanol){
                            tipoCombustivel = "Etanol";
                        } else {
                            tipoCombustivel = "Gasolina";
                        }

                        litros = Double.parseDouble(etLitros.getText().toString());
                        preco = Double.parseDouble(etPreco.getText().toString());
                        total = Double.parseDouble(etTotal.getText().toString());
                        pagamento = spinnerPagamento.getSelectedItem().toString();
                        odometro = Double.parseDouble(etOdometro.getText().toString());
                        data = etData.getText().toString();

                        Abastecimentos.addItem(data, odometro, tipoCombustivel, total, litros, preco, pagamento);

                        Toast.makeText(getContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

                    } catch (Exception erro){
                        erro.printStackTrace();
                        Toast.makeText(getContext(), "Erro ao salvar informações", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return view;
    }

}
