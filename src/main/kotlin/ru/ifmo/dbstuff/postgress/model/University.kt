package ru.ifmo.dbstuff.postgress.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class University (@Id var id: String,
                       val name: String)