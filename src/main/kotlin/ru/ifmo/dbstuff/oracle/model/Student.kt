package ru.ifmo.dbstuff.oracle.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Student (@Id val id: String,
                    @OneToOne
                    var person: Person,
                    @ManyToOne
                    var uni: University,
                    @OneToOne
                    var spec: Spec,
                    val format: String,
                    val type: String,
                    val inDate: LocalDate,
                    val outDate: LocalDate?,
                    @ManyToOne
                    var group: Group)