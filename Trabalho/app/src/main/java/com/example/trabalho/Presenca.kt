package com.example.trabalho


class Presenca(val id: Int,val id_materia : Int, id_aluno : Int,val data : String) {
    override fun equals(o: Any?): Boolean {
        return id == (o as Presenca?)!!.id
    }
    override fun hashCode(): Int {
        return id
    }
}