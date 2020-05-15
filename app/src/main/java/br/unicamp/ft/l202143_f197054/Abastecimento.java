package br.unicamp.ft.l202143_f197054;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Abastecimento {

    private String data;
    private double odometro;
    private String combustivel;
    private double total;
    private double litros;
    private double preco;
    private String pagamento;
    private String posto;

}
