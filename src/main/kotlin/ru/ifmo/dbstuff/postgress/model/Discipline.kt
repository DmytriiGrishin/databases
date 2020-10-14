package ru.ifmo.dbstuff.postgress.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Discipline (@Id var id: String,
                       val name: String,
                       var lectureHours: Int,
                       var practiceHours: Int,
                       var labHours: Int,
                       val controlType: String)