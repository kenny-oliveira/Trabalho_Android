package com.example.trabalho

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.util.*

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
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"))
        val ano: Int = calendar.get(Calendar.YEAR)
        val mes: Int = calendar.get(Calendar.MONTH)
        val dia: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hora: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minuto: Int = calendar.get(Calendar.MINUTE)


        if (extras != null) {
            val Diadadisc = extras.getString("dia_semana_disc")
            val Disc_do_dia = extras.getString("disciplina_do_dia")
            val DiscliplinAlvo = extras.getString("disciplinaalvo")
            val mpresenca : Button = findViewById(R.id.mpresenca)
            Disciplina.text = DiscliplinAlvo

            val dao = AgendaDAO(baseContext)
            val MateriaLista = dao.retornarTodosMateria()
            val PresencaLista = dao.retornarTodosPresenca()
            val AlunoLista = dao.retornarTodosAluno()
            val idaluno = extras.getInt("idaluno")
            val btn_presenca : Button = findViewById(R.id.mpresenca)
            val txt_horariopre : TextView = findViewById(R.id.horariopresenca)

            for (presenca in PresencaLista!!){
                val Lista = presenca!!.data.split("/")
                var diah = dia.toString().padStart(2, '0')
                var mesh = mes.toString().padStart(2, '0')

                if (presenca!!.id_aluno == idaluno && Lista[0] == diah && Lista[1] == mesh && Lista[2] == ano.toString()){
                    for (materia in MateriaLista!!){
                        if (materia!!.id == presenca!!.id_materia && materia!!.nome == DiscliplinAlvo){
                            txt_horariopre.text = presenca!!.horario
                            btn_presenca.isEnabled = false
                            btn_presenca.text = "Você ja marcou presença nesse dia"
                        }
                    }
                }
            }

            for(materia in MateriaLista!!){
                val Lista = materia!!.horarioinicio.split(".")
                val Lista2 = materia!!.horariofinal.split(".")
                val horapad = hora.toString().padStart(2, '0').toInt()
                val minpad = minuto.toString().padStart(2, '0').toInt()
                val atualminutos = (minpad+(horapad*60))
                val minutosaulainicio = Lista[1].toInt()+(Lista[0].toInt()*60)
                val minutosaulafinal = Lista2[1].toInt()+(Lista2[0].toInt()*60)


                if (materia!!.nome == DiscliplinAlvo){
                    val horarioaula : TextView = findViewById(R.id.horarioaula)
                    horarioaula.text = materia!!.horarioinicio.toString() + " a "+materia!!.horariofinal.toString()
                    var dataatual = (dia).toString().padStart(2, '0')+"/"+(mes).toString().padStart(2, '0')+"/"+(ano).toString()
                    val minpad = minuto.toString().padStart(2, '0')
                    Log.i("Atual Minutos: ",atualminutos.toString())
                    Log.i("Horario Inicio Minutos: ",minutosaulainicio.toString())
                    Log.i("Horario Final Minutos: ",minutosaulafinal.toString())
                    Log.i("Horario Test Minutos: ",minutosaulafinal.toString())
                    /**flag do gps**/
                    if (atualminutos >= minutosaulainicio && atualminutos <= minutosaulafinal){
                    btn_presenca.setOnClickListener {
                        if (dao.salvarPresenca(materia!!.id, idaluno!!, dataatual!!, ("$hora:" + minpad)!!)) {
                            btn_presenca.isEnabled = false
                            txt_horariopre.text = ("$hora:" + minpad)
                            btn_presenca.text = "Você ja marcou presença nesse dia"
                        }
                    }
                    }else{
                        btn_presenca.isEnabled = false
                        btn_presenca.text = "Não está no horario da aula"
                    }
                }
            }


            if (Disc_do_dia != DiscliplinAlvo) {
                mpresenca.isEnabled = false;
            }

        }

        val btn_voltar : Button = findViewById(R.id.btn_voltar)
        btn_voltar.setOnClickListener {
            /**voltar para os dias da semana**/
            val intent = Intent(this, ListagemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("idcursoaluno", extras!!.getInt("idcursoaluno"));
            intent.putExtra("rgm", extras!!.getInt("rgm"));
            intent.putExtra("idaluno", extras.getInt("idaluno"));

            startActivity(intent)
        }
    }

}

