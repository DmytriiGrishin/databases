package ru.ifmo.dbstuff.oracle.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Score (@Id val id: String,
                  @ManyToOne
                  var student: Student,
                  @ManyToOne
                  var subject: Subject,
                  var score: Int,
                  val scoreLetter: String,
                  val scoreDate: LocalDate)