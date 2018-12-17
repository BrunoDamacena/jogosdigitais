using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ScriptSenha1 : MonoBehaviour
{

    private string senhaCorreta;
    public string senhaAtual;
    private int tamanhoSenha;
    private int tamanhoAtual;
    private bool resolvido;
    Text textoVisor;
    Image corVisor;

    // Use this for initialization
    void Start()
    {
        senhaCorreta = "4519";
        senhaAtual = "";
        tamanhoSenha = senhaCorreta.Length;
        tamanhoAtual = senhaAtual.Length;
        resolvido = false;
        textoVisor = GameObject.Find("SenhaKeypad1").GetComponent<Text>();
        corVisor = GameObject.Find("PainelVisor1").GetComponent<Image>();
    }

    // Update is called once per frame
    void Update()
    {
    }

    private void checaSenha()
    {
        if (!resolvido)
        {
            if (tamanhoSenha == tamanhoAtual)
            {
                if (senhaCorreta == senhaAtual)
                {
                    GameObject.Find("Porta Senha 1").GetComponent<ScriptPortaSenha>().abrirPorta();
                    resolvido = true;
                    textoVisor.text = senhaAtual;
                    corVisor.color = Color.green;
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
        if (!resolvido)
        {
            senhaAtual += button;
            tamanhoAtual++;            
            checaSenha();
            textoVisor.text = senhaAtual;
        }
    }
}
