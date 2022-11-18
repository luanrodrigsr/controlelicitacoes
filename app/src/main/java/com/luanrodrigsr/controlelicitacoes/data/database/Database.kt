package com.luanrodrigsr.controlelicitacoes.data.database

import com.luanrodrigsr.controlelicitacoes.data.model.Process
import com.luanrodrigsr.controlelicitacoes.data.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

/**
 * Classe que representa o banco de dados.
 */
abstract class Database {
    companion object {
        // Instância do banco de dados.
        @Volatile
        private var INSTANCE: Realm? = null

        /**
         * Retorna a instância do banco de dados Realm com o padrão singleton.
         */
        fun get(): Realm {
            return INSTANCE ?: synchronized(this) {
                // Criar configuração do banco de dados Realm.
                val configuration = RealmConfiguration.Builder(
                    // Criando o esquema do banco de dados com as
                    // classes: User, Process, ...
                    schema = setOf(
                        User::class, Process::class
                    )
                ).build()

                // Atribuir o banco de dados Realm a instância com o padrão singleton.
                val instance = Realm.open(configuration = configuration)
                INSTANCE = instance

                // Retornar a instância recém criada do banco de dados Realm.
                instance
            }
        }

        /**
         * Encerra o banco de dados Realm, caso o mesmo esteja aberto.
         */
        fun close() {
            // Continuar apenas se a instância do banco de dados não for nula.
            INSTANCE?.let {
                // Ignorar caso o banco de dados já tenha sido encerrado.
                if (!it.isClosed()) {
                    INSTANCE?.close()
                }
            }
        }

        fun getFakeData(): List<Process> {
            return listOf(
                Process().apply {
                    city = "Nova Venécia"
                    date = "03/12/2022"
                    description =
                        "Aquisição de gêneros alimentícios para atender a demanda da " +
                                "Secretaria Municipal de Educação."
                },
                Process().apply {
                    city = "Colatina"
                    date = "08/12/2022"
                    description =
                        "Aquisição de produtos de limpeza para atender a demanda da " +
                                "Secretaria Municipal de Saúde."
                },
                Process().apply {
                    city = "Sooretama"
                    date = "21/12/2022"
                    description =
                        "Aquisição de produtos de higiêne para atender a demanda da " +
                                "Secretaria Municipal de Assistência Social."
                }
            )
        }
    }
}