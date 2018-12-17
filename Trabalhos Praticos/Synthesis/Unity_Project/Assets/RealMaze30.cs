using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RealMaze30 : MonoBehaviour {

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
		blocks = new GameObject[30,30];
		maze = new bool[30,30]
		{{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,false},{false,true,false,false,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,false},{false,true,true,true,false,true,true,true,true,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,false,true,true,true,false,false},{false,true,false,true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false,true,false,true,false,false},{false,true,false,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,false,true,false,false},{false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,false},{false,true,true,true,true,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,true,true,false,false},{false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,false},{false,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,true,true,false,true,false,true,false,false},{false,true,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,false},{false,true,false,true,false,true,false,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,false},{false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,false,false,false,false,false},{false,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,false,false},{false,false,false,true,false,true,false,false,false,false,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,false,false,false,false,false},{false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,false,true,true,true,true,true,false,false},{false,true,false,false,false,true,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,false,true,false,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,false},{false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,false},{false,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,false,true,false,true,true,true,false,false},{false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false},{false,true,true,true,false,true,true,true,false,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,true,true,false,false},{false,false,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false},{false,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,false,true,false,true,false,true,false,true,false,true,false,false},{false,false,false,false,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,true,false,false},{false,true,false,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,false,true,false,false},{false,true,false,true,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,false,false,false,false,true,false,true,false,false},{false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}};

		for (int r = 1; r < maze.GetLength(0); r++) {
			for (int c = 0; c < maze.GetLength(1)-1; c++) {
				if(!maze[r,c]){
					blocks[r,c] = Instantiate(block, GameObject.Find("Labirinto30").transform.position, GameObject.Find("Labirinto30").transform.rotation) as GameObject;
					blocks[r,c].transform.Translate(new Vector3(r*resize, c*resize, 0));
					blocks[r,c].name = "Block " + r + "," + c;
					blocks[r,c].transform.localScale *= resize*2f/3;
				}
			}
		}
		exitColumnPosition = maze.GetLength(1) - 17;
		exitRowPosisiton = maze.GetLength(0) - 2;
		isSolvingPuzzle = false;
		isSolved = false;
		}
}
