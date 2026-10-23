package br.com.joaocarloslima;


public class Tiro extends Asset {
    private int poder;

    public Tiro(int x, int y, int velocidade, Direcao direcao, int poder) {
        super(x, y, velocidade, direcao);
        this.poder = poder;
        setImagem("images/laser/laser" + poder + ".png");
    }

    public int getPoder() { return poder; }
}
