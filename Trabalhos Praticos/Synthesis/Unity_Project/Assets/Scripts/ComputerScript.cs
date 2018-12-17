using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ComputerScript : MonoBehaviour {

    bool FaseAcabou;
    GameObject TelaDeFim;
    GameObject TelaDeJogo;

	// Use this for initialization
	void Start () {
        TelaDeJogo = FindIncludingInactive("Tela de Jogo");
        TelaDeFim = FindIncludingInactive("Tela de Fim");
        FaseAcabou = false;
	}
	
	// Update is called once per frame
	void Update () {
		if(FaseAcabou)
        {
            TelaDeJogo.SetActive(false);
            TelaDeFim.SetActive(true);
            Cursor.visible = true;
            Time.timeScale = 0;
        }
	}

    public void onClickEvent()
    {
        FaseAcabou = true;
    }

    //hideously slow as it iterates all objects, so don't overuse!
    public static GameObject FindInChildrenIncludingInactive(GameObject go, string name)
    {

        for (int i = 0; i < go.transform.childCount; i++)
        {
            if (go.transform.GetChild(i).gameObject.name == name) return go.transform.GetChild(i).gameObject;
            GameObject found = FindInChildrenIncludingInactive(go.transform.GetChild(i).gameObject, name);
            if (found != null) return found;
        }

        return null;  //couldn't find crap
    }

    public static GameObject FindIncludingInactive(string name)
    {
        Scene scene = SceneManager.GetActiveScene();
        var game_objects = new List<GameObject>();
        scene.GetRootGameObjects(game_objects);

        foreach (GameObject obj in game_objects)
        {
            GameObject found = FindInChildrenIncludingInactive(obj, name);
            if (found) return found;
        }

        return null;
    }
}
