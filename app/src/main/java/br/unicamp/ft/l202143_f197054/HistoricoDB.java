package br.unicamp.ft.l202143_f197054;

import java.util.ArrayList;

public class HistoricoDB {

    private String data, tipo, posto;
    private double total, litros;

    public HistoricoDB(String data, String tipo, double litros, double total, String posto) {
        this.data = data;
        this.tipo = tipo;
        this.litros = litros;
        this.total = total;
        this.posto = posto;
    }

    public HistoricoDB() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }
}
