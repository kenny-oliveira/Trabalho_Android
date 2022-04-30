package com.example.trabalho

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private fun fecharApp(){
        finishAffinity()
        exitProcess(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**setContentView(R.layout.activity_listagem)**/
        val btnfechar : Button = findViewById(R.id.btn_fechar)
        btnfechar.setOnClickListener {
            fecharApp()
        }
        val btnentrar : Button = findViewById(R.id.btn_entrar)
        btnentrar.setOnClickListener {
            /**validar a senha do usuario aqui**/
            val intent = Intent(this, ListagemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
