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
    private Double odometro;
    private String combustivel;
    private Double total;
    private Double litros;
    private Double preco;
    private String pagamento;
    private String posto;

}
