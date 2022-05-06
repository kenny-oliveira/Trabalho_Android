package com.example.trabalho

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class ListagemActivity : AppCompatActivity() {

    private fun AlterarAlpha(elemento: TextView, valor: Float){
        if (valor != 1F) {
            elemento.text = "Nenhuma Disciplina"
            elemento.alpha = valor
            elemento.backgroundTintMode = PorterDuff.Mode.SCREEN
            elemento.background.setTint(ContextCompat.getColor(this, R.color.disc_transp))
        }else{
            elemento.alpha = valor
            elemento.backgroundTintMode = PorterDuff.Mode.SCREEN
            elemento.background.setTint(ContextCompat.getColor(this, R.color.disc_reset))
        }
    }

    fun RetornarDia(numero : Int): String {
        var dayWeek = ""
        when (numero) {
            1->  dayWeek = "Domingo"
            2 -> dayWeek = "Segunda-Feira"
            3 -> dayWeek = "Terça-Feira"
            4 -> dayWeek = "Quarta-Feira"
            5 -> dayWeek = "Quinta-Feira"
            6 -> dayWeek = "Sexta-Feira"
            7 -> dayWeek = "Sabado"
        }
        return dayWeek
    }

    fun RetornarDiaNumero(Dia : String): Int {
        var elemento = 0
        when (Dia) {
            "Segunda-Feira" -> elemento = 2
            "Terça-Feira" -> elemento = 3
            "Quarta-Feira" -> elemento = 4
            "Quinta-Feira" -> elemento = 5
            "Sexta-Feira" -> elemento = 6
        }
        return elemento
    }


    fun RetornarDiaElemento(Dia : String): TextView {
        var elemento : TextView = TextView(this);
        when (Dia) {
            "Segunda-Feira" -> elemento = findViewById(R.id.disc_seg)
            "Terça-Feira" -> elemento = findViewById(R.id.disc_ter)
            "Quarta-Feira" -> elemento = findViewById(R.id.disc_qua)
            "Quinta-Feira" -> elemento = findViewById(R.id.disc_qui)
            "Sexta-Feira" -> elemento = findViewById(R.id.disc_sex)
        }
        return elemento
    }

    fun RetornarDiaElementoImagem(Dia : String): TextView {
        var elemento : TextView = TextView(this);
        when (Dia) {
            "Segunda-Feira" -> elemento = findViewById(R.id.Segunda_Feira)
            "Terça-Feira" -> elemento = findViewById(R.id.Terça_Feira)
            "Quarta-Feira" -> elemento = findViewById(R.id.Quarta_Feira)
            "Quinta-Feira" -> elemento = findViewById(R.id.Quinta_Feira)
            "Sexta-Feira" -> elemento = findViewById(R.id.Sexta_Feira)
        }
        return elemento
    }

    private fun CriarEvento(elemento: TextView,elemento2: TextView){
        val DisciplinaDia : TextView = findViewById(R.id.disciplina_dia)
        val extras = intent.extras
        if (DisciplinaDia.text == "Nenhuma Disciplina"){
            DisciplinaDia.isEnabled = false
        }
        elemento.setOnClickListener {
            val intent = Intent(this, DisciplinaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("disciplinaalvo", elemento2.text);
            intent.putExtra("disciplina_do_dia",DisciplinaDia.text)
            intent.putExtra("idcursoaluno", extras!!.getInt("idcursoaluno"));
            intent.putExtra("rgm", extras!!.getInt("rgm"));
            intent.putExtra("idaluno", extras.getInt("idaluno"));
            startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        /**AlterarAlpha(R.id.Segunda_Feira, 0.5F) altera o textview escolhido para cinza
         * e com transparencia reduzida **/
        /**AlterarAlpha(R.id.Segunda_Feira, 1F) desativa a transparencia**/
        val extras = intent.extras
        val DisciplinaDia : TextView = findViewById(R.id.disciplina_dia)
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"))
        val dia: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hora: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minuto: Int = calendar.get(Calendar.MINUTE)

        val dao = AgendaDAO(baseContext)
        val MateriaLista = dao.retornarTodosMateria()
        val CursoLista = dao.retornarTodosCurso()

        val DiaSList = ArrayList<String>()
        val DiaNList = ArrayList<String>()

        DiaNList.add("Segunda-Feira")
        DiaNList.add("Terça-Feira")
        DiaNList.add("Quarta-Feira")
        DiaNList.add("Quinta-Feira")
        DiaNList.add("Sexta-Feira")

        if (extras != null) {
            val idcursoaluno = extras.getInt("idcursoaluno")
            val idaluno = extras.getInt("idaluno")
            val rgmaluno = extras.getInt("rgm")

            for (curso in CursoLista!!) {
                if (curso!!.id.toString() == idcursoaluno.toString()) {
                    Log.i("Curso: ", curso!!.nome)
                    Log.i("Dia Atual: ", (RetornarDia(dia)))
                    for (mater in MateriaLista!!) {
                        if (mater!!.idcurso == curso!!.id) {
                            DiaSList.add(mater!!.dia)
                            val Elemento =
                                RetornarDiaElemento(RetornarDia(RetornarDiaNumero(mater!!.dia)))
                            Elemento.text = mater!!.nome
                            if (mater!!.dia == RetornarDia(dia)) {
                                DisciplinaDia.text = mater!!.nome
                            }
                        }
                    }
                }
            }
            DiaNList.removeAll(DiaSList);
            for (dia in DiaNList){
                val elemento = RetornarDiaElementoImagem(dia)
                val elemento2 = RetornarDiaElemento(dia)
                AlterarAlpha(elemento, 0.5F)
                AlterarAlpha(elemento2, 0.5F)
            }
            for (dia in DiaSList){
                val elemento = RetornarDiaElementoImagem(dia)
                val elemento2 = RetornarDiaElemento(dia)
                CriarEvento(elemento,elemento2)
            }

            CriarEvento(findViewById(R.id.disciplina_dia),findViewById(R.id.disciplina_dia))
        }

        val btn_logout : Button = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

}

