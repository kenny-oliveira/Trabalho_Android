package com.example.trabalho

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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


        val dao = AgendaDAO(baseContext)
        dao.InserirDados()

        val AlunosLista = dao.retornarTodosAluno()

        val btnfechar : Button = findViewById(R.id.btn_fechar)
        btnfechar.setOnClickListener {
            fecharApp()
        }
        val btnentrar : Button = findViewById(R.id.btn_entrar)

        val rgm_entrada : EditText = findViewById(R.id.rgm_entrada)
        val senha_entrada : EditText = findViewById(R.id.senha_entrada)
        var sucesso = 0
        btnentrar.setOnClickListener {
            for (estealuno in AlunosLista!!) {
                Log.i("Aluno: ",estealuno!!.nome)
              if (rgm_entrada.text.toString() == estealuno.rgm.toString()){
                  if (senha_entrada.text.toString() == estealuno.senha){
                      sucesso = 1
                      val intent = Intent(this, ListagemActivity::class.java)
                      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                      intent.putExtra("idcursoaluno", estealuno.idcurso);
                      intent.putExtra("rgm", estealuno.rgm);
                      intent.putExtra("idaluno", estealuno.id);
                      startActivity(intent)
                  }
              }
            }
            if (sucesso == 0){
                Toast.makeText(this, "Usuario ou senha incorreto", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
