package com.luanrodrigsr.controlelicitacoes.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

/**
 * A classe que representa o objeto usuário.
 */
class User : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()

    // O nome do usuário.
    var username: String = ""

    // A senha do usuário.
    var password: String = ""
}