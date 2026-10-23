package br.com.joaocarloslima;

import java.util.Random;

public class Meteoro extends Asset {
    private int tamanho;
    private int poder;

    public Meteoro(int x, int y, int velocidade, Direcao direcao) {
        super(x, y, velocidade, direcao);
        tamanho = new Random().nextInt(8) + 1;
        poder = tamanho;
        setImagem("images/meteoro/meteoro" + tamanho + ".png");
    }

    public void tomarTiro(Tiro tiro) { poder -= tiro.getPoder(); }
    public int getTamanho() { return tamanho; }
    public int getPoder() { return poder; }
}
