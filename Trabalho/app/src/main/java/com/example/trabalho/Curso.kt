package com.example.trabalho


class Curso(val id : Int,val nome: String) {
    override fun equals(o: Any?): Boolean {
        return id == (o as Curso?)!!.id
    }
    override fun hashCode(): Int {
        return id
    }
    /*"SELECT Id,rgm, nome, senha,id_curso FROM AGENDA ORDER BY NOME"*/
}