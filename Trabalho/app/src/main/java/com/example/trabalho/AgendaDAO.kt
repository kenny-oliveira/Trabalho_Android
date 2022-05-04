package com.example.trabalho

import DBGateway
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log


class AgendaDAO(ctx: Context?) {
    private val TABLE_Curso = "Curso"
    private val TABLE_Materia = "Materia"
    private val TABLE_Aluno = "Aluno"
    private val TABLE_Presenca = "Presenca"
    private val gw: DBGateway?

    init {
        gw = DBGateway.getInstance(ctx!!)
    }


    fun CheckIsDataAlreadyInDBorNot(
        TableName: String,
        dbfield: String,
        fieldValue: String
    ): Boolean {
        val Query = "Select * from $TableName where $dbfield = $fieldValue"
        val cursor = gw!!.database2.rawQuery(Query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun salvarAluno(rgm: Int, nome: String?, senha: String?, id_curso: Int): Boolean {
        val cv = ContentValues()
            cv.put("rgm", rgm)
            cv.put("nome", nome)
            cv.put("senha", senha)
            cv.put("id_curso", id_curso)
            return gw!!.database.insert(TABLE_Aluno, null, cv) > 0
        }

    /*(val Id : Int,val nome: String,val dia : String,val horario : String, val idcurso : Int*/
    fun salvarMateria(nome: String?, dia: String?, horarioinicio: String, horariofinal:String, id_curso: Int): Boolean {
        val cv = ContentValues()
        cv.put("nome", nome)
        cv.put("dia", dia)
        cv.put("horarioinicio", horarioinicio)
        cv.put("horariofinal", horariofinal)
        cv.put("id_curso", id_curso)
        return gw!!.database.insert(TABLE_Materia, null, cv) > 0
    }

    fun salvarCurso(nome: String?): Boolean {
        val cv = ContentValues()
        cv.put("nome", nome)
        return gw!!.database.insert(TABLE_Curso, null, cv) > 0
    }

    fun salvarPresenca(idmateria: Int, idaluno: Int, data: String?): Boolean {
        val cv = ContentValues()
        cv.put("id_materia ", idmateria)
        cv.put("id_aluno", idaluno)
        cv.put("data", data)
        return gw!!.database.insert(TABLE_Presenca, null, cv) > 0
    }

    fun InserirDados() {
        val Query = "SELECT * FROM Curso"
        val Query2 = "SELECT * FROM Aluno"

        val cursor = gw!!.database.rawQuery(Query, null)
        val cursor2 = gw!!.database.rawQuery(Query2, null)
        Log.i("Qtd: ", cursor.count.toString() + " Cursos")
        Log.i("Qtd: ", cursor2.count.toString() + " Alunos")
        cursor.moveToFirst()
        cursor2.moveToFirst()
        if (!CheckIsDataAlreadyInDBorNot(TABLE_Curso,"Id","1")) {
            Log.i("Qtd: ", cursor.count.toString() + " Cursos")
            Log.i("Qtd: ", cursor2.count.toString() + " Alunos")
            salvarCurso("CIÊNCIA DA COMPUTAÇÂO")
            salvarAluno(20823096, "Kenny Giuliano Gonçalves De Oliveira", "12345678", 1)
            salvarAluno(20558619, "Gustavo Henrique de Carvalho", "02072001", 1)
            salvarAluno(20558619, "Everton Tunis Monteiro", "12344321", 1)
            salvarAluno(21571112, "Nicolas Matheus Rodrigues Viana", "343434", 1)
            salvarAluno(123, "Naruto", "123", 1)
            salvarMateria("TRABALHO DE GRADUAÇÃO INTERDISCIPLINAR I","Segunda-Feira","19:10","20:25",1)
            salvarMateria("PROGRAMAÇÃO PARA DISPOSITIVOS MÓVEIS","Quarta-Feira","19:10","21:50",1)
            salvarMateria("FUNDAMENTOS DE INTELIGÊNCIA ARTIFICIAL","Quinta-Feira","19:10","21:50",1)
            salvarMateria("LINGUAGENS FORMAIS E AUTÔMATOS","Sexta-Feira","19:10","21:50",1)
            Log.i("UPD-Qtd: ", cursor.count.toString() + " Cursos")
            Log.i("UPD-Qtd: ", cursor2.count.toString() + " Alunos")
        }
    }

    fun retornarTodosAluno(): List<Aluno>? {
        val aluno: MutableList<Aluno> = ArrayList()
        val cursor = gw!!.database2.query(TABLE_Aluno,null,null,null,null,null,null)
        while (cursor.moveToNext()) {
                val id: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val rgm: Int = cursor.getInt(cursor.getColumnIndexOrThrow("rgm"))
                val nome: String = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val senha: String = cursor.getString(cursor.getColumnIndexOrThrow("senha"))
                val idcurso: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id_curso"))
                aluno.add(Aluno(id, rgm, nome, senha, idcurso))
        }
        cursor.close()
        return aluno
    }

    fun retornarTodosMateria(): List<Materia>? {
        val materia: MutableList<Materia> = ArrayList()
        val cursor = gw!!.database2.query(TABLE_Materia,null,null,null,null,null,null)
        while(cursor.moveToNext()){
            val id: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome: String = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val dia: String = cursor.getString(cursor.getColumnIndexOrThrow("dia"))
            val horarioinicio: String = cursor.getString(cursor.getColumnIndexOrThrow("horarioinicio"))
            val horariofinal: String = cursor.getString(cursor.getColumnIndexOrThrow("horariofinal"))
            val idcurso: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id_curso"))
            materia.add(Materia(id, nome, dia, horarioinicio, horariofinal, idcurso))
        }
        cursor.close()
        return materia
    }

    fun retornarTodosCurso(): List<Curso?> {
        val curso: MutableList<Curso> = ArrayList()
        val cursor = gw!!.database2.query(TABLE_Curso,null,null,null,null,null,null)
        while(cursor.moveToNext()){
                val id: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome: String = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                curso.add(Curso(id, nome))
        }
        cursor.close()
        return curso
    }


    fun retornarTodosPresenca(): List<Presenca>? {
        val presenca: MutableList<Presenca> = ArrayList()
        val cursor = gw!!.database2.query(TABLE_Presenca, null, null, null, null, null, null)
        while(cursor.moveToNext()){
                val id: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val idmateria: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id_materia"))
                val idaluno: Int = cursor.getInt(cursor.getColumnIndexOrThrow("id_aluno"))
                val data: String = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                presenca.add(Presenca(id, idmateria, idaluno, data))
        }
        cursor.close()
        return presenca
    }
}