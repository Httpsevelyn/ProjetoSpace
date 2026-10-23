package br.com.joaocarloslima;

public class Controller {
    private Jogo jogo = new Jogo();
    private int frame = 0;

    public String atualizar(String comando) {
        if (comando.contains("esquerda")) jogo.moverNave(Direcao.ESQUERDA);
        if (comando.contains("direita")) jogo.moverNave(Direcao.DIREITA);
        if (comando.contains("tiro")) jogo.atirar();
        if (frame % 50 == 0) jogo.criarMeteoro();
        jogo.atualizar();
        frame++;
        return jogo.estadoJson();
    }
}
