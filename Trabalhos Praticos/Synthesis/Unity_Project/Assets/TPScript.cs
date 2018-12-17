using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TPScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void onClickEvent() {
		GameObject.Find("Personagem").transform.position = new Vector3(370, 0, -30);
	}
}
