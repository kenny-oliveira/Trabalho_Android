package com.example.trabalho

import java.io.Serializable

class Aluno(val id : Int,val rgm : Int ,val nome: String,val senha : String, val idcurso: Int) : Serializable {
    override fun equals(o: Any?): Boolean {
        return id == (o as Aluno?)!!.id
    }

    override fun hashCode(): Int {
        return id
    }
    /*"SELECT Id,rgm, nome, senha,id_curso FROM AGENDA ORDER BY NOME"*/
}