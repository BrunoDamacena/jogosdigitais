package br.cefetmg.games;

import br.cefetmg.games.movement.AlgoritmoMovimentacao;
import br.cefetmg.games.movement.Agente;
import br.cefetmg.games.movement.Alvo;
import br.cefetmg.games.graphics.RenderizadorAgente;
import br.cefetmg.games.graphics.RenderizadorObjetivo;
import br.cefetmg.games.movement.behavior.*;
import br.cefetmg.games.physics.Colisoes;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Iterator;

/**
 * Classe principal do programa.
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Cinematica extends ApplicationAdapter {

    private Viewport viewport;
    private OrthographicCamera camera;
    private BitmapFont fonte;
    private SpriteBatch batch;
    private Array<Agente> agentes;
    private RenderizadorAgente renderizador;
    private RenderizadorObjetivo renderizadorObjetivo;

    private Alvo objetivo;
    private Buscar buscar;
    private Vagar vagar;
    private Fugir fugir;
    private Chegar chegar;
    private AlgoritmoMovimentacao algoritmoCorrente;
    private Array<AlgoritmoMovimentacao> algoritmos;
    private String stringAlgoritmoCorrente;

    private static final float FATOR_ZOOM_A_CADA_SCROLL = 20.0f;

    @Override
    public void create() {
        // configura a janela e elementos gráficos
        camera = new OrthographicCamera(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);
        batch = new SpriteBatch();
        renderizador = new RenderizadorAgente(camera, batch);
        renderizadorObjetivo = new RenderizadorObjetivo(camera);
        fonte = new BitmapFont();

        // define o objetivo (perseguição, fuga) inicialmente no centro do mundo 
        objetivo = new Alvo(new Vector3(0, 0, 0));

        // configura e registra os comportamentos disponíveis
        algoritmos = new Array<>();
        buscar = new Buscar(40);
        buscar.alvo = objetivo;
        vagar = new Vagar(40, 2);
        fugir = new Fugir(40);
        chegar = new Chegar(40);
        fugir.alvo = buscar.alvo;
        chegar.alvo = buscar.alvo;
        algoritmos.add(buscar);
        algoritmos.add(vagar);
        algoritmos.add(fugir);
        algoritmos.add(chegar);
        algoritmoCorrente = vagar;
        stringAlgoritmoCorrente = "Algoritmo corrente: "
                + algoritmoCorrente.getNome();

        agentes = new Array<>();
        Agente agente = novoAgente(posicaoAleatoria());
        agente.defineComportamento(buscar);
        novoAgente(posicaoAleatoria());
        novoAgente(posicaoAleatoria());
        novoAgente(posicaoAleatoria());

        // escuta por eventos de interação
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                Vector3 clique = new Vector3(x, y, 0);
                viewport.unproject(clique);

                // Botão DIREITO: posiciona objetivo no mapa ou em um agente
                if (button == Buttons.RIGHT) {
                    boolean definiuObjetivo = false;

                    // passo 1: verifica se o clique "acertou" um agente
                    Iterator<Agente> it = agentes.iterator();
                    Agente atual = null;
                    while (it.hasNext() && !definiuObjetivo) {
                        atual = it.next();
                        definiuObjetivo = Colisoes.colideCom(
                                new Circle(
                                        new Vector2(
                                                atual.pose.posicao.x,
                                                atual.pose.posicao.y),
                                        RenderizadorAgente.RAIO),
                                clique);
                    }
                    if (definiuObjetivo) {
                        objetivo.setObjetivo(atual);
                    } // passo 2: se não tiver acertado um agente, define o 
                    // objetivo no mapa                    
                    else {
                        objetivo.setObjetivo(clique);
                    }
                } // Botão ESQUERDO: novo agente
                else if (button == Buttons.LEFT) {
                    novoAgente(clique);
                }
                return true;
            }

            @Override
            public boolean scrolled(int quanto) {
                // faz um zoom na câmera
                if (camera.zoom + quanto < 0) {
                    return true;
                }
                camera.zoom += quanto / FATOR_ZOOM_A_CADA_SCROLL;
                camera.update();
                viewport.setWorldSize(
                        camera.zoom * camera.viewportWidth,
                        camera.zoom * camera.viewportHeight);
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                // verifica se pressionaram uma tecla referente a um algoritmo
                // nesse caso, torna ele o algoritmo ativo para se criar novos
                // agentes
                for (AlgoritmoMovimentacao algoritmo : algoritmos) {
                    if (keycode == algoritmo.getTeclaParaAtivacao()) {
                        algoritmoCorrente = algoritmo;
                        stringAlgoritmoCorrente = "Algoritmo corrente: "
                                + algoritmo.getNome();
                        return true;
                    }
                }
                // teclas gerais
                switch (keycode) {
                    case Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void dispose() {
        renderizador.dispose();
        renderizadorObjetivo.dispose();
    }

    /**
     * Atualiza o mundo virtual para ter as mesmas proporções que a janela.
     *
     * @param w Largura da janela.
     * @param h Altura da janela.
     */
    @Override
    public void resize(int w, int h) {
        viewport.update(w, h);
    }

    /**
     * Faz com que o mundo seja infinito. Quando um agente chega ao final do
     * mundo (1 dos 4 cantos), ele é teleportado para o outro lado.
     *
     * @param agente Agente
     */
    private void revolveCoordenadas(Agente agente) {
        float larguraMundo = viewport.getWorldWidth();
        float alturaMundo = viewport.getWorldHeight();
        float larguraMundo_2 = larguraMundo / 2.0f;
        float alturaMundo_2 = alturaMundo / 2.0f;

        if (agente.pose.posicao.x < -larguraMundo_2) {
            agente.pose.posicao.x += larguraMundo;
        } else if (agente.pose.posicao.x > larguraMundo_2) {
            agente.pose.posicao.x -= larguraMundo;
        }
        if (agente.pose.posicao.y < -alturaMundo_2) {
            agente.pose.posicao.y += alturaMundo;
        } else if (agente.pose.posicao.y > alturaMundo_2) {
            agente.pose.posicao.y -= alturaMundo;
        }
    }

    /**
     * Percorre a lista de agentes, atualiando sua lógica de movimentação.
     *
     * @param delta tempo desde a última atualização.
     */
    private void atualizaAgentes(float delta) {

        // percorre a lista de agentes e os atualiza (agente.atualiza)
        for (Agente agente : agentes) {
            // atualiza lógica
            agente.atualiza(delta);
            // contém os agentes dentro do mundo
            revolveCoordenadas(agente);
        }
    }

    /**
     * Percorre a lista de agentes, renderizando-os. Também invoca um método
     * (atualizaAgentes) para atualização da lógica de movimentação.
     */
    @Override
    public void render() {
        // limpa a tela
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // desenha os agentes
        batch.setProjectionMatrix(camera.combined);
        for (Agente agente : agentes) {
            renderizador.desenha(agente);
        }

        // desenha o objetivo
        renderizadorObjetivo.desenha(objetivo);

        // tempo desde a última atualização
        float delta = Gdx.graphics.getDeltaTime();
        // atualiza a lógica de movimento dos agentes
        atualizaAgentes(delta);

        renderizadorObjetivo.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        fonte.draw(batch, stringAlgoritmoCorrente,
                -viewport.getWorldWidth() / 2, 
                -viewport.getWorldHeight() / 2 + 15);
        batch.end();
    }

    /**
     * Cria um novo agente, com o algoritmo corrente, na posição desejada.
     *
     * @param posicao
     * @return
     */
    public Agente novoAgente(Vector3 posicao) {
        Agente agente = new Agente(posicao,
                new Color(
                        (float) Math.random(),
                        (float) Math.random(),
                        (float) Math.random(), 1));
        agente.pose.orientacao = (float) (Math.random() * Math.PI * 2);
        agente.defineComportamento(algoritmoCorrente);

        agentes.add(agente);
        return agente;
    }

    /**
     * Retorna uma posição aleatória dentro da tela.
     *
     * @return
     */
    private Vector3 posicaoAleatoria() {
        Vector3 posicao = new Vector3();
        posicao.x = camera.viewportWidth * (float) (Math.random() - 0.5f);
        posicao.y = camera.viewportHeight * (float) (Math.random() - 0.5f);
        return posicao;
    }

}
