package ru.ifmo.dbstuff.oracle.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Person (@Id var id: String,
                   val name: String,
                   val sex: String,
                   var birthDay: LocalDate,
                   val birthPlace: String)