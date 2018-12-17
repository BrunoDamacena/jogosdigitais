using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScriptPortaSenha : MonoBehaviour
{
    private Rigidbody rb;
    private bool portaAberta;
    private bool continueToOpen = true;
    private float doorSize = 5;
    private float originalPosition;
    // Use this for initialization
    void Start()
    {
        rb = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {
        if (portaAberta && continueToOpen)
        {
            rb.transform.position += new Vector3(0, 0, -1) * Time.deltaTime * 5;
            if (originalPosition - rb.transform.position.z > doorSize) continueToOpen = false;
        }
    }

    public void abrirPorta()
    {
        GetComponent<BoxCollider>().enabled = false;
        originalPosition = rb.transform.position.z;
        portaAberta = true;
    }
}
