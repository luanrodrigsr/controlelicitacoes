package com.luanrodrigsr.controlelicitacoes

import android.app.Application
import com.luanrodrigsr.controlelicitacoes.data.database.Database
import io.realm.kotlin.Realm

// Classe que extende a aplicação, responsável por armazenar uma referência única ao banco de dados.
class MainApplication : Application() {
    // O banco de dados do Realm será inicializado de forma "preguiçosa", apenas quando
    // for solicitado.
    val database: Realm by lazy {
        Database.get()
    }
}