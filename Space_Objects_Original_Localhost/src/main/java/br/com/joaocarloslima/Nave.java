package br.com.joaocarloslima;


public class Nave extends Asset {
    public Nave(int x, int y, int velocidade, Direcao direcao) {
        super(x, y, velocidade, direcao);
        setImagem("images/ships/playerShip1_blue.png");
    }

    public Tiro atirar(int poder) {
        return new Tiro(getX(), getY(), 10, Direcao.CIMA, poder);
    }

    @Override
    public void mover() {
        super.mover();
        setX(Math.max(0, Math.min(590, getX())));
        setY(Math.max(0, Math.min(430, getY())));
    }
}
