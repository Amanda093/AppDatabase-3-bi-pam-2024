package com.example.appdatabase.viewModel

import com.example.appdatabase.roomDB.Pessoa
import com.example.appdatabase.roomDB.PessoaDataBase

class Repository(private val db : PessoaDataBase) {
    suspend fun upsertPessoa(pessoa: Pessoa) {
        db.pessoaDAO().upsertPessoa(pessoa)
    }

    suspend fun deletePessoa(pessoa: Pessoa) {
        db.pessoaDAO().deletePessoa(pessoa)
    }

    fun getAllPessoa() = db.pessoaDAO().getAllPessoa()
}