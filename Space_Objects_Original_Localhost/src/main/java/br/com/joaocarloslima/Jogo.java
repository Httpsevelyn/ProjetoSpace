package br.com.joaocarloslima;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    protected List<Asset> assets = new ArrayList<>();
    protected Nave nave = new Nave(280, 400, 10, Direcao.DIREITA);
    private int pontos = 0;
    private int nivel = 1;

    public Tiro atirar() {
        Tiro tiro = nave.atirar(nivel);
        assets.add(tiro);
        return tiro;
    }

    public Meteoro criarMeteoro() {
        Meteoro meteoro = new Meteoro(new Random().nextInt(591), 0, 2, Direcao.BAIXO);
        assets.add(meteoro);
        return meteoro;
    }

    public void pontuar() {
        pontos++;
        nivel = Math.min(4, 1 + pontos / 10);
    }

    public int getPontos() { return pontos; }
    public int getNivel() { return nivel; }
    // Cada atualização avança dois quadros.
    public void atualizar() {
        for (int passo = 0; passo < 2; passo++) {
            List<Asset> remover = new ArrayList<>();
            for (Asset asset : assets) {
                asset.mover();
                if (asset.getY() < 0 || asset.getY() > 480) remover.add(asset);
            }
            for (Asset asset : assets) {
                if (!(asset instanceof Tiro) || remover.contains(asset)) continue;
                Tiro tiro = (Tiro) asset;
                for (Asset outro : assets) {
                    if (outro instanceof Meteoro && !remover.contains(outro)
                            && tiro.colidiuCom(outro)) {
                        Meteoro meteoro = (Meteoro) outro;
                        meteoro.tomarTiro(tiro);
                        if (meteoro.getPoder() <= 0) {
                            remover.add(meteoro);
                            pontuar();
                        }
                        remover.add(tiro);
                        break;
                    }
                }
            }
            assets.removeAll(remover);
        }
    }

    public void moverNave(Direcao direcao) {
        nave.setDirecao(direcao);
        nave.mover();
    }

    public String estadoJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\"pontos\":").append(pontos).append(",\"nivel\":").append(nivel);
        json.append(",\"nave\":").append(assetJson(nave)).append(",\"assets\":[");
        for (int i = 0; i < assets.size(); i++) {
            if (i > 0) json.append(',');
            json.append(assetJson(assets.get(i)));
        }
        return json.append("]}").toString();
    }

    private String assetJson(Asset asset) {
        return "{\"x\":" + asset.getX() + ",\"y\":" + asset.getY()
                + ",\"imagem\":\"" + asset.getImagem() + "\"}";
    }
}
