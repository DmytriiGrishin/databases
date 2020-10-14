package ru.ifmo.dbstuff.oracle.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Employee (@Id val id: String,
                     @OneToOne
                     var person: Person,
                     @ManyToOne
                     var uni: University,
                     val unit: String,
                     val position: String,
                     val startDate: LocalDate,
                     val endDate: LocalDate)