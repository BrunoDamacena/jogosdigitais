using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScriptSenha : MonoBehaviour {

    private string senhaCorreta;
    public string senhaAtual;
    private int tamanhoSenha;
    private int tamanhoAtual;
    private bool resolvido;

	// Use this for initialization
	void Start () {
        senhaCorreta = "1234";
        senhaAtual = "";
        tamanhoSenha = senhaCorreta.Length;
        tamanhoAtual = senhaAtual.Length;
        resolvido = false;
	}
	
	// Update is called once per frame
	void Update () {
	}

    private void checaSenha()
    {
        if(!resolvido)
        {
            if (tamanhoSenha == tamanhoAtual)
            {
                if (senhaCorreta == senhaAtual)
                {
                    GameObject.Find("Porta Senha 1").GetComponent<ScriptPortaSenha>().abrirPorta();
                    resolvido = true;
                }
                else
                {
                    senhaAtual = "";
                    tamanhoAtual = senhaAtual.Length;
                }
            }
        }
        
    }

    public void onClickEvent(int button)
    {
        if(!resolvido)
        {
            senhaAtual += button;
            tamanhoAtual++;
            checaSenha();
        }        
    }
}
