package br.unicamp.ft.l202143_f197054;

public class Abastecimento {

    private String data;
    private double odometro;
    private String combustivel;
    private double total;
    private double litros;
    private double preco;
    private String pagamento;

    public Abastecimento(String data, double odometro, String combustivel, double total, double litros, double preco, String pagamento) {
        this.data = data;
        this.odometro = odometro;
        this.combustivel = combustivel;
        this.total = total;
        this.litros = litros;
        this.preco = preco;
        this.pagamento = pagamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getOdometro() {
        return odometro;
    }

    public void setOdometro(double odometro) {
        this.odometro = odometro;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }
}
