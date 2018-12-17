using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RealMaze : MonoBehaviour {

	// Use this for initialization
	public GameObject block, greenblock;
	private GameObject[,] blocks;
	private float columnPosition=1, rowPosition=2, exitColumnPosition, exitRowPosisiton, resize;
	private int column, row;
	private bool isSolvingPuzzle;
	private bool isSolved;
	private bool[,] maze;
	void Start () {
		resize = 10f;
		column = (int)columnPosition;
		row = (int)rowPosition;
		blocks = new GameObject[20,20];
		maze = new bool[20,20]
		{{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,true,false,true,false,true,true,true,true,true,true,true,true,true,true,true,true,true,false,false},{false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,false,false},{false,true,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,false},{false,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,false,true,false,false},{false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,false,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,false,false},{false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false,false},{false,true,false,true,true,true,true,true,true,true,false,true,false,true,true,true,true,true,false,false},{false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,true,false,false},{false,true,true,true,false,true,false,true,false,true,true,true,false,true,true,true,true,true,false,false},{false,true,false,false,false,false,false,false,false,true,false,true,false,true,false,false,false,false,false,false},{false,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,false,false},{false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,true,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false},{false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false},{false,true,true,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}};

		for (int r = 1; r < maze.GetLength(0); r++) {
			for (int c = 0; c < maze.GetLength(1)-1; c++) {
				if(!maze[r,c]){
					blocks[r,c] = Instantiate(block, new Vector3(0, 0, 0), GameObject.Find("Labirinto20").transform.rotation) as GameObject;
					blocks[r,c].transform.Translate(new Vector3(r*resize, c*resize, 0));
					blocks[r,c].name = "Block " + r + "," + c;
					blocks[r,c].transform.localScale *= resize*2f/3;
				}
			}
		}
		exitColumnPosition = 9;
		exitRowPosisiton = 4;
		isSolvingPuzzle = false;
		isSolved = false;
	}

	void update() {

	}
}
