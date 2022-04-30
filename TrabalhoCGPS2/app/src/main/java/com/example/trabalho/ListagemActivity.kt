package com.example.trabalho

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class ListagemActivity : AppCompatActivity() {

    private fun AlterarAlpha(Id: Int, valor: Float){
        val elemento: TextView = findViewById<View>(Id) as TextView
        if (valor != 1F) {
            elemento.alpha = valor
            elemento.backgroundTintMode = PorterDuff.Mode.SCREEN
            elemento.background.setTint(ContextCompat.getColor(this, R.color.disc_transp))
        }else{
            elemento.alpha = valor
            elemento.backgroundTintMode = PorterDuff.Mode.SCREEN
            elemento.background.setTint(ContextCompat.getColor(this, R.color.disc_reset))
        }
    }

    private fun CriarEvento(Id: Int,Id2: Int){
        val elemento: TextView = findViewById<View>(Id) as TextView
        val elemento2: TextView = findViewById<View>(Id2) as TextView
        elemento.setOnClickListener {
            val intent = Intent(this, DisciplinaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("disciplinaalvo", elemento2.text);
            startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)
        /**AlterarAlpha(R.id.Segunda_Feira, 0.5F) altera o textview escolhido para cinza
         * e com transparencia reduzida **/
        /**AlterarAlpha(R.id.Segunda_Feira, 1F) desativa a transparencia**/
        val btn_logout : Button = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener {
            /**Remover usuario aqui**/
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        CriarEvento(R.id.Segunda_Feira,R.id.disc_seg)
        CriarEvento(R.id.Terça_Feira,R.id.disc_ter)
        CriarEvento(R.id.Quarta_Feira,R.id.disc_qua)
        CriarEvento(R.id.Quinta_Feira,R.id.disc_qui)
        CriarEvento(R.id.Sexta_Feira,R.id.disc_sex)
    }

}

