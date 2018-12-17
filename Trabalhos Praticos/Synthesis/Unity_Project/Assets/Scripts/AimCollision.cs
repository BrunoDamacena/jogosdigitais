using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class AimCollision : MonoBehaviour {
    void Update()
    {
        // Bit shift the index of the layer (8) to get a bit mask
        int layerMask = 1 << 8;

        // This would cast rays only against colliders in layer 8.
        // But instead we want to collide against everything except layer 8. The ~ operator does this, it inverts a bitmask.
        layerMask = ~layerMask;

        RaycastHit hit;
        // Does the ray intersect any objects excluding the player layer
		if (Input.GetButtonDown("Fire1")){
			if (Physics.Raycast(transform.position, transform.TransformDirection(Vector3.forward), out hit, 5, layerMask))
			{
                try {
                    if (hit.collider.ToString() == "Painel 1 (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("Painel").transform.Find("ScriptPuzzle").GetComponent<PuzzleScript>().onClickEvent();
                    }
                    else if (hit.collider.ToString() == "Computador (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("ScriptComputador").GetComponent<ComputerScript>().onClickEvent();
                    }
                    else if (hit.collider.ToString() == "Keycard_def (UnityEngine.BoxCollider)")
                    {
                        GameObject.Find("Personagem").GetComponent<PlayerController>().setKeyCard(true);
                        GameObject.Find("Keycard").SetActive(false);
                    }
                    else if(hit.collider.ToString() == "keyreader_def (UnityEngine.BoxCollider)")
                    {
                        if (GameObject.Find("Personagem").GetComponent<PlayerController>().getKeyCard())
                        {
                            GameObject.Find("Porta Sala Principal").GetComponent<ScriptPortaPrincipal>().abrirPorta();
                        }
                    }
                    else if(hit.collider.ToString() == "Painel Esquerda (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("Painel Esquerda").transform.Find("ScriptPuzzle").GetComponent<TPScript>().onClickEvent();
                    }
                    else if(hit.collider.ToString() == "Painel Direita (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("Painel Direita").transform.Find("ScriptPuzzle").GetComponent<TPScript>().onClickEvent();
                    }
                    else if(hit.collider.ToString() == "Painel TP (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("Painel").transform.Find("ScriptPuzzle").GetComponent<TPScript>().onClickEvent();
                    }
                    else if(hit.collider.ToString() == "Painel Fim Fase 3 (UnityEngine.BoxCollider)")
                    {
                        hit.collider.transform.Find("Painel").transform.Find("ScriptPuzzle").GetComponent<EndPhase3>().onClickEvent();
                    }
                    else {
                        for (int i = 0; i <= 9; i++)
                        {
                            if (hit.collider.ToString() == i + ".1 (UnityEngine.BoxCollider)") //if door n 1
                            {
                                GameObject.Find("ScriptSenha1").GetComponent<ScriptSenha1>().onClickEvent(i);
                            }
                            else if (hit.collider.ToString() == i + ".2 (UnityEngine.BoxCollider)") //if door n 2
                            {
                                GameObject.Find("ScriptSenha2").GetComponent<ScriptSenha2>().onClickEvent(i);
                            }
                        }
                    }
				} catch (Exception e){}  
			}
		}
    }
}