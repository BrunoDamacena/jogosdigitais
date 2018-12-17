package br.cefetmg.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class CauldronGame extends ApplicationAdapter {

    private Camera camera;
    private ModelBatch modelBatch;
    private Environment ambiente;
    private AssetManager assets;

    private ModelInstance caldeirao;
    private ModelInstance fogueira;
    private Node sopa;
    private ParticleSystem sistemaParticulas;
    private CameraInputController cameraController;
    private ParticleEffect fogo, bolhas;
    private Music musica;

    private boolean aindaEstaCarregando = false;
    private float anguloRotacaoSopa = 0;

    @Override
    public void create() {
        // define a cor da borracha (fundo)
        Gdx.gl20.glClearColor(1, 1, 1, 1);

        // instancia batch, asset manager e ambiente 3D
        modelBatch = new ModelBatch();
        assets = new AssetManager();
        ambiente = new Environment();
        ambiente.set(new ColorAttribute(ColorAttribute.AmbientLight,
                0.4f, 0.4f, 0.4f, 1f));
        ambiente.add(new DirectionalLight()
                .set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // configura a câmera
        float razaoAspecto = ((float) Gdx.graphics.getWidth())
                / Gdx.graphics.getHeight();
        camera = new PerspectiveCamera(67, 480f * razaoAspecto, 480f);
        camera.position.set(1f, 1.75f, 3f);
        camera.lookAt(0, 0.35f, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraController);

        // solicita carregamento dos 2 modelos 3D da cena
        assets.load("caldeirao.obj", Model.class);
        assets.load("fogueira.obj", Model.class);

        // instancia e configura 2 tipos de renderizadores de partículas:
        // 1. Billboards (para fogo)
        // 2. PointSprites (para bolhas)
        BillboardParticleBatch billboardBatch
                = new BillboardParticleBatch(
                        ParticleShader.AlignMode.Screen, true, 500);
        PointSpriteParticleBatch pointSpriteBatch
                = new PointSpriteParticleBatch();
        sistemaParticulas = new ParticleSystem();
        billboardBatch.setCamera(camera);
        pointSpriteBatch.setCamera(camera);
        sistemaParticulas.add(billboardBatch);
        sistemaParticulas.add(pointSpriteBatch);

        // solicita o carregamento dos efeitos de partículas
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(sistemaParticulas.getBatches());
        assets.load("fogo.pfx", ParticleEffect.class, loadParam);
        assets.load("bolhas.pfx", ParticleEffect.class, loadParam);
        
        // solicita carregamento da música
        musica = Gdx.audio.newMusic(Gdx.files.internal("zelda-potion-shop.mp3"));

        aindaEstaCarregando = true;
    }

    private void aoTerminoDoCarregamento() {
        // configura instâncias de caldeirão e fogueira
        caldeirao = new ModelInstance(assets.get("caldeirao.obj", Model.class));
        fogueira = new ModelInstance(assets.get("fogueira.obj", Model.class));
        fogueira.transform.setToTranslation(0, -0.08f, 0);
        sopa = caldeirao.getNode("topoDaSopa");

        // instancia, configura e dá início ao efeito de fogo
        fogo = ((ParticleEffect) assets.get("fogo.pfx")).copy();
        fogo.init();
        fogo.start();
        fogo.translate(new Vector3(0, 0.1f, 0));
        sistemaParticulas.add(fogo);

        // instancia, configura e dá início ao efeito das bolhas
        bolhas = ((ParticleEffect) assets.get("bolhas.pfx")).copy();
        bolhas.init();
        bolhas.start();
        bolhas.translate(new Vector3(0, 1, 0));
        sistemaParticulas.add(bolhas);
        
        // use o campo ParticleEffect bolhas definido na linha #38
        // ...
        // ...
        // ...
        
        // começa a música
        musica.setLooping(true);
        musica.play();

        aindaEstaCarregando = false;
    }

    @Override
    public void resize(int width, int height) {
        Gdx.gl.glViewport(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // configura a câmera para as novas dimensões da janela
        float razaoAspecto = (float) width / (float) height;
        camera.viewportWidth = 480f * razaoAspecto;
        camera.viewportHeight = 480f;
        camera.update();
    }

    @Override
    public void render() {
        // assim que tiver terminado de carregar os modelos e efeitos de
        // partículas, invoca a função para instanciar e configurar a cena
        // (aoTerminoDoCarregamento())
        if (aindaEstaCarregando && assets.update()) {
            aoTerminoDoCarregamento();
        }

        // atualiza a câmera de acordo com as configurações do mouse
        cameraController.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // começa a desenhar os modelos da cena
        modelBatch.begin(camera);
        // se já tiver carregado o caldeirão, renderiza-o
        if (caldeirao != null) {
            sopa.scale.set(0.99f, 1, 0.99f);
            sopa.rotation.set(Vector3.Y, anguloRotacaoSopa++);
            sopa.calculateTransforms(true);
            modelBatch.render(caldeirao, ambiente);
        }

        // se já tiver carregado o fogueira, renderiza-a
        if (fogueira != null) {
            modelBatch.render(fogueira, ambiente);
        }

        // se já tiver terminado todo o carregamento, renderiza os efeitos 
        // de partículas
        if (!aindaEstaCarregando) {
            sistemaParticulas.update();
            sistemaParticulas.begin();
            sistemaParticulas.draw();
            sistemaParticulas.end();
            modelBatch.render(sistemaParticulas);
        }
        modelBatch.end();
    }

    @Override
    public void dispose() {
        // desaloca os recursos da cena
        modelBatch.dispose();
        assets.dispose();
        fogo.dispose();
        bolhas.dispose();
        musica.dispose();
    }
}
