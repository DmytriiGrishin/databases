package ru.ifmo.dbstuff.oracle.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name ="Groups")
data class Group(@Id var id: String,
                 val code: String,
                 var course: Int,
                 var year: Int)