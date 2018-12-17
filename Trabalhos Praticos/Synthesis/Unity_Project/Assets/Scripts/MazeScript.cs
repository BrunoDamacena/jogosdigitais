using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MazeScript : MonoBehaviour {
	public GameObject block, redblock, greenblock;
	private GameObject playableBlock, objectiveBlock;
	private GameObject[,] blocks;
	private float columnPosition=1, rowPosition=2, exitColumnPosition, exitRowPosisiton, resize;
	private int column, row;
	private float size;
	private Vector3 leftCorner;
	private bool isSolvingPuzzle;
	private bool isSolved;
	private bool[,] maze;
	void Start () {
		resize = 1.51f;
		size = 50;
		if(size == 20) leftCorner = new Vector3(0.79f, 0.67f, 0.2f);
		else leftCorner = new Vector3(0.775f, 0.7f, 0.2f);
		column = (int)columnPosition;
		row = (int)rowPosition;
		blocks = new GameObject[(int)size,(int)size];
		playableBlock = new GameObject();
		objectiveBlock = new GameObject();
		maze = new bool[50,50]
		{{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},{false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,false},{false,false,false,true,false,true,false,true,false,false,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,true,false,false,false,false,false,false,false,false,false,true,false,true,false,false,false,true,false,false},{false,true,false,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,false,true,false,true,true,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,false},{false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,false,true,false,true,true,true,false,false},{false,true,false,false,false,false,false,false,false,true,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,true,false,false,false,true,false,true,false,true,false,true,false,false},{false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,false,true,false,false},{false,true,false,true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false},{false,true,false,true,false,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,false},{false,false,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,true,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,false,true,false,false},{false,true,false,true,false,false,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,false,false,false,false,true,false,true,false,true,false,false,false,false},{false,true,false,true,true,true,false,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,true,true,false,true,false,true,true,true,false,false},{false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,false},{false,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,true,true,false,true,false,false},{false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,true,false,true,false,true,false,false,false,false,false,true,false,false,false,false,false,false},{false,true,false,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,true,true,false,false},{false,false,false,true,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,true,false,false},{false,true,true,true,false,true,true,true,false,true,false,true,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,true,true,false,false},{false,false,false,true,false,false,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,false},{false,true,false,true,true,true,true,true,true,true,true,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,false,false},{false,true,false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false},{false,true,true,true,false,true,true,true,false,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,false,true,true,true,false,false},{false,true,false,true,false,true,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false,false},{false,true,false,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,true,true,false,true,false,true,false,true,false,true,false,false},{false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,true,false,false},{false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,true,true,true,true,false,false},{false,false,false,true,false,false,false,false,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,false},{false,true,false,true,true,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,false,false},{false,true,false,false,false,true,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,false,false,true,false,true,false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,false},{false,true,true,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,false},{false,false,false,true,false,false,false,false,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,true,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,true,true,false,false},{false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,true,false,false,false,true,false,false,false,false,false,false},{false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,true,true,false,false},{false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,true,false,true,false,false},{false,true,true,true,false,true,false,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,false,false},{false,false,false,false,false,true,false,true,false,false,false,true,false,true,false,true,false,false,false,true,false,true,false,true,false,true,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,false},{false,true,true,true,true,true,false,true,true,true,false,true,false,true,false,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,false},{false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,true,false,true,false,false,false,false,false,false,false,true,false,true,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false},{false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,true,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,false},{false,false,false,false,false,true,false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,false,false,true,false,true,false,false,false,false,false,false,false,true,false,false},{false,true,true,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,false,true,false,true,true,true,true,true,true,true,false,false},{false,true,false,true,false,false,false,true,false,true,false,false,false,false,false,true,false,true,false,false,false,true,false,true,false,false,false,true,false,false,false,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,true,false,false},{false,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,false,true,true,true,false,true,true,true,false,true,true,true,true,true,false,true,true,true,false,true,true,true,false,true,false,false},{false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,false,false,true,false,true,false,false,false,true,false,true,false,false,false,true,false,true,false,true,false,false,false,false,false,true,false,true,false,true,false,false},{false,true,false,true,true,true,false,true,true,true,true,true,false,true,false,true,true,true,true,true,false,true,false,true,true,true,false,true,false,true,true,true,false,true,false,true,false,true,true,true,true,true,true,true,false,true,true,true,false,false},{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false}};
		size=size-1;


		for (int r = 1; r < maze.GetLength(0); r++) {
			for (int c = 0; c < maze.GetLength(1)-1; c++) {
				if(!maze[r,c]){
					blocks[r,c] = Instantiate(block, GameObject.Find("CanvasPanel").transform.position - leftCorner, GameObject.Find("CanvasPanel").transform.rotation) as GameObject;
					blocks[r,c].transform.Translate(new Vector3(r*resize/size, c*resize/size, 0));
					blocks[r,c].name = "Block " + r + "," + c;
					blocks[r,c].transform.localScale /= size;
					//blocks[r,c].transform.position += new Vector3(0.1f, 0, 0);
				}
			}
		}
		exitColumnPosition = maze.GetLength(1) - 3;
		exitRowPosisiton = maze.GetLength(0) - 2;
		playableBlock = Instantiate(redblock, GameObject.Find("CanvasPanel").transform.position - leftCorner, GameObject.Find("CanvasPanel").transform.rotation) as GameObject;;
		playableBlock.transform.Translate(new Vector3(rowPosition*resize/size, columnPosition*resize/size, 0));
		playableBlock.name = "RedBlock";
		playableBlock.transform.localScale /= size;
		objectiveBlock = Instantiate(greenblock, GameObject.Find("CanvasPanel").transform.position - leftCorner, GameObject.Find("CanvasPanel").transform.rotation) as GameObject;;
		objectiveBlock.transform.Translate(new Vector3(exitRowPosisiton*resize/size, exitColumnPosition*resize/size, 0));
		objectiveBlock.name = "GreenBlock";
		objectiveBlock.transform.localScale /= size;
		isSolvingPuzzle = false;
		isSolved = false;
		}	
	
	// Update is called once per frame
	void FixedUpdate () {
		if(isSolvingPuzzle && !isSolved) {
			Time.fixedDeltaTime = 0.1f;
			//ai ele vai poder movimentar o bloquinho vermelho
			if(Input.GetKey(KeyCode.W) && maze[row, column+1]){
				column++;
				playableBlock.transform.Translate(new Vector3(0, columnPosition*resize/size, 0));			
			}if(Input.GetKey(KeyCode.S)  && maze[row, column-1]){
				column--;
				playableBlock.transform.Translate(new Vector3(0, -columnPosition*resize/size, 0));
			}if(Input.GetKey(KeyCode.D)  && maze[row+1, column]){
				row++;
				playableBlock.transform.Translate(new Vector3(rowPosition*resize/(2*size), 0 , 0));
			}if(Input.GetKey(KeyCode.A)  && maze[row-1, column]){
				row--;
				playableBlock.transform.Translate(new Vector3(-rowPosition*resize/(2*size), 0, 0));
			}
			if(row == exitRowPosisiton && column == exitColumnPosition) cabosamerda();
		}
		else Time.fixedDeltaTime = 0.02f;

	}

	public void solvePuzzle(bool solving) {
		isSolvingPuzzle = solving;
	}

	public void cabosamerda() {
		isSolvingPuzzle = true;
		isSolved = true;
		GameObject.Find("ScriptPuzzle").GetComponent<PuzzleScript>().setPuzzleResolvido(true);
	}
}
