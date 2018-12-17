package br.cefetmg.games.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Arma que cria tiros de laser (cor de rosa).
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class LaserWeapon implements Weapon {

    private final Vector2 originRight;
    private final Vector2 originLeft;

    public LaserWeapon(Vector2 originLeft, Vector2 originRight) {
        this.originLeft = originLeft;
        this.originRight = originRight;
    }

    @Override
    public Array<Shot> createShot(Vector2 position) {
        // dá 2 tiros (esquerda e direita)
        return new Array<Shot>(new Shot[]{
            new LaserShot(new Vector2(originLeft).add(position)),
            new LaserShot(new Vector2(originRight).add(position))
        });
    }

    @Override
    public long getCadenceInMillis() {
        return 200;
    }
}
