package com.example.trabalho


class Materia(val id: Int, val nome: String, val dia: String, val horarioinicio: String, val horariofinal : String, val idcurso: Int) {
    override fun equals(o: Any?): Boolean {
        return id == (o as Materia?)!!.id
    }
    override fun hashCode(): Int {
        return id
    }
}