package com.example.trabalho

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.system.exitProcess

class AgendaDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_CREATE_CURSO = "CREATE TABLE Curso ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "nome TEXT NOT NULL);"

    private val SQL_CREATE_MATERIA = "CREATE TABLE Materia ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "nome TEXT NOT NULL, " +
            "dia TEXT," +
            "horario TEXT," +
            "id_Curso INTEGER, " +
            "FOREIGN KEY(Id_cursoO) REFERENCES Curso(id));"

    private val SQL_CREATE_ALUNO = "CREATE TABLE Aluno ( " +
            " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"+
            "rgm INTEGER NOT NULL, "+
            "nome TEXT NOT NULL, " +
            "senha TEXT NOT NULL, " +
            "id_curso INTEGER, " +
            " FOREIGN KEY(id_curso) REFERENCES Materia(id));"

    private val SQL_CREATE_PRESENÇA = "CREATE TABLE Presença ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "id_materia INTEGER, " +
            "data TEXT, " +
            "FOREIGN KEY(id_aluno) REFERENCES Aluno(id)," +
            "FOREIGN KEY(id_materia) REFERENCES Materia(id));"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CURSO)
        db.execSQL(SQL_CREATE_MATERIA)
        db.execSQL(SQL_CREATE_ALUNO)
        db.execSQL(SQL_CREATE_PRESENÇA)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_CREATE_CURSO)
        db.execSQL(SQL_CREATE_MATERIA)
        db.execSQL(SQL_CREATE_ALUNO)
        db.execSQL(SQL_CREATE_PRESENÇA)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        private const val DATABASE_NAME = "Faculdade.db"
        private const val DATABASE_VERSION = 1
        /*private const val SQL_DELETE_AGENDA = "DROP TABLE IF EXISTS AGENDA"*/
    }
}


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

            /**antes do intent**/
            val intent = Intent(this, ListagemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
