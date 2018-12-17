package br.cefetmg.games.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Uma arma que cria tiros de vórtice.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class VortexWeapon implements Weapon {

    private final Vector2 origin;

    public VortexWeapon(Vector2 origin) {
        this.origin = origin;
    }

    @Override
    public Array<Shot> createShot(Vector2 position) {
        return new Array<Shot>(new Shot[]{
            new VortexShot(new Vector2(origin).add(position))
        });
    }

    @Override
    public long getCadenceInMillis() {
        return 600;
    }
}
