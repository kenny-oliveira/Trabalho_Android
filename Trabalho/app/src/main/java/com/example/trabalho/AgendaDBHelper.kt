@file:Suppress("SyntaxError")

package com.example.trabalho

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AgendaDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQLCREATECURSO = "CREATE TABLE Curso ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "nome TEXT NOT NULL);"

    private val SQLCREATEMATERIA = "CREATE TABLE Materia ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "nome TEXT NOT NULL, " +
            "dia TEXT," +
            "horarioinicio TEXT," +
            "horariofinal TEXT," +
            "id_curso INTEGER);"

    private val SQLCREATEALUNO = "CREATE TABLE Aluno ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "rgm INTEGER NOT NULL, " +
            "nome TEXT NOT NULL, " +
            "senha TEXT NOT NULL, " +
            "id_curso INTEGER);"

    private val SQLCREATEPRESENCA = "CREATE TABLE Presenca ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "id_aluno INTEGER,"+
            "id_materia INTEGER, " +
            "data TEXT," +
            "horario TEXT);"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQLCREATECURSO)
        db.execSQL(SQLCREATEMATERIA)
        db.execSQL(SQLCREATEALUNO)
        db.execSQL(SQLCREATEPRESENCA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_Aluno)
        db.execSQL(SQL_DELETE_Curso)
        db.execSQL(SQL_DELETE_Materia)
        db.execSQL(SQL_DELETE_Presenca)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DATABASE_NAME = "Faculdade.db"
        private const val DATABASE_VERSION = 16
        private const val SQL_DELETE_Aluno = "DROP TABLE IF EXISTS Aluno"
        private const val SQL_DELETE_Curso = "DROP TABLE IF EXISTS Curso"
        private const val SQL_DELETE_Materia = "DROP TABLE IF EXISTS Materia"
        private const val SQL_DELETE_Presenca = "DROP TABLE IF EXISTS Presenca"
    }
}
