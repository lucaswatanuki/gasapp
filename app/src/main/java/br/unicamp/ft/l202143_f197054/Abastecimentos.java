package br.unicamp.ft.l202143_f197054;

import java.util.ArrayList;

public class Abastecimentos {

    public static ArrayList<Abastecimento> abastecimentosList = new ArrayList<>();

    public static void addItem(String data, double odometro, String combustivel, double total, double litros, double preco, String pagamento, String posto) {
        abastecimentosList.add(new Abastecimento(data,odometro,combustivel,total,litros,preco,pagamento, posto));
    }
}
