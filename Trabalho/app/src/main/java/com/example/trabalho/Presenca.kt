package com.example.trabalho


class Presenca(val id: Int,val id_materia : Int ,val id_aluno : Int,val data : String,val horario : String) {
    override fun equals(o: Any?): Boolean {
        return id == (o as Presenca?)!!.id
    }
    override fun hashCode(): Int {
        return id
    }
}