using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PuzzleScript : MonoBehaviour {
	private bool puzzleResolvido;
	bool isSolving;
	bool portaAberta;
	GameObject scriptPersonagem, scriptCamera, scriptMaze;
	// Use this for initialization
	void Start () {
		isSolving = false;
		puzzleResolvido = false;
		scriptPersonagem = GameObject.Find("Personagem");
		scriptCamera = GameObject.Find("CameraPersonagem");
		scriptMaze = GameObject.Find("Panel");
	}
	
	// Update is called once per frame
	void Update () {
		if(isSolving) {
			scriptPersonagem.GetComponent<PlayerController>().setIsSolvingPuzzle(true);
			//playsom
			scriptCamera.GetComponent<CameraScript>().setIsSolvingPuzzle(true);
			scriptMaze.GetComponent<MazeScript>().solvePuzzle(true);
		}
		else {
			scriptPersonagem.GetComponent<PlayerController>().setIsSolvingPuzzle(false);
			//playoutrosom
			scriptCamera.GetComponent<CameraScript>().setIsSolvingPuzzle(false);
			scriptMaze.GetComponent<MazeScript>().solvePuzzle(false);
		}

		if(puzzleResolvido && !portaAberta){
			GameObject.Find("Porta Recepção 1").GetComponent<ScriptPorta>().abrirPorta();
			GameObject.Find("Porta Recepção 2").GetComponent<ScriptPorta>().abrirPorta();
			portaAberta = true;
		}
	}

	public void onClickEvent() {
		isSolving = !isSolving;
	}

	public void setPuzzleResolvido(bool isResolvido) {
		puzzleResolvido = isResolvido;
		isSolving = false;
	}
}


