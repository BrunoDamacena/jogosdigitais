using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour {	
	private float speed;
    private bool haveKeyCard;
	private bool isSolvingPuzzle;
	private Rigidbody rb;
	void Start (){
		rb = GetComponent<Rigidbody>();
        haveKeyCard = false;
		isSolvingPuzzle = false;
	}
	void FixedUpdate () {
		Vector3 frente = new Vector3(GameObject.Find("CameraPersonagem").transform.forward.x, 0.0f, GameObject.Find("CameraPersonagem").transform.forward.z);
		Vector3 direita = new Vector3(GameObject.Find("CameraPersonagem").transform.right.x, 0.0f, GameObject.Find("CameraPersonagem").transform.right.z);
		if(!isSolvingPuzzle) {
			if(Input.GetKey(KeyCode.LeftShift)) speed = 16;
			else speed = 10;
			if(Input.GetKey(KeyCode.W)){
				rb.transform.position += frente * Time.deltaTime * speed;
			}if(Input.GetKey(KeyCode.S)){
				rb.transform.position -= frente * Time.deltaTime * speed;
			}if(Input.GetKey(KeyCode.D)){
				rb.transform.position += direita * Time.deltaTime * speed;
			}if(Input.GetKey(KeyCode.A)){
				rb.transform.position -= direita * Time.deltaTime * speed;
			}
		}
		
	}

    public void setKeyCard(bool keyCard)
    {
        haveKeyCard = keyCard;
    }

    public bool getKeyCard()
    {
        return haveKeyCard;
    }

	public void setIsSolvingPuzzle(bool puzzle) {
		isSolvingPuzzle = puzzle;
	}
}
