package com.example.trabalho

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class DisciplinaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_disciplina)

        val Disciplina : TextView = findViewById(R.id.discalvo)
        val extras = intent.extras
        val latitudeunicid  = -23.5362861
        val longitudeunicid = -46.5603371
        val latitude : TextView = findViewById(R.id.latitude)
        val longitude : TextView = findViewById(R.id.longitude)

        if (extras != null) {
            val Diadadisc = extras.getString("dia_semana_disc")
            val Disc_do_dia = extras.getString("disciplina_do_dia")
            val DiscliplinAlvo = extras.getString("disciplinaalvo")
            val mpresenca : Button = findViewById(R.id.mpresenca)

            if (Disc_do_dia != DiscliplinAlvo) {
                mpresenca.isEnabled = false;
            }

            Disciplina.text = DiscliplinAlvo
        }

        val btn_voltar : Button = findViewById(R.id.btn_voltar)
        btn_voltar.setOnClickListener {
            /**voltar para os dias da semana**/
            val intent = Intent(this, ListagemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}

