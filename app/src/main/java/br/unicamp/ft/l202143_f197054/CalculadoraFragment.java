package br.unicamp.ft.l202143_f197054;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculadoraFragment extends Fragment {

    private EditText precoGas;
    private EditText precoEta;
    private TextView tvResult;
    private Button calculo;

    public CalculadoraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculadora, container, false);


        tvResult = v.findViewById(R.id.resultado);

        calculo = v.findViewById(R.id.button_calculo);
        precoGas = v.findViewById(R.id.preco_gasolina);
        precoEta = v.findViewById(R.id.preco_etanol);


        calculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Double gas = Double.parseDouble(precoGas.getText().toString());
               Double eta = Double.parseDouble(precoEta.getText().toString());

               double resultado;

               resultado = (gas * 0.7);

                if(resultado>eta){
                    tvResult.setText("Etanol será melhor!");
                }else{
                    tvResult.setText("Gasolina será melhor!");
                }
            }
        });

        return v;

    }

}


