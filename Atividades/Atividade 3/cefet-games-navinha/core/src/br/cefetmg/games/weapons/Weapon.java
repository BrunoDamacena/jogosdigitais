package br.cefetmg.games.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Uma arma.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public interface Weapon {
    Array<Shot> createShot(Vector2 origin);
    
    /**
     * Quanto tempo para poder dar o próximo tiro.
     * @return tempo em milissegundos para poder atirar novamente.
     */
    long getCadenceInMillis();
}
