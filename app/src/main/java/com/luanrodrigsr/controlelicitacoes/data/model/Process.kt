package com.luanrodrigsr.controlelicitacoes.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Process : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var city: String = ""
    var date: String = ""
    var description: String = ""
}