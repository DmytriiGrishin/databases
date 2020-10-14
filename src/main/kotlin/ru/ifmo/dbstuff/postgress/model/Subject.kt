package ru.ifmo.dbstuff.postgress.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Subject (@Id val id: String,
                    @ManyToOne
                    var discipline: Discipline,
                    @ManyToOne
                    var spec: Spec,
                    @ManyToOne
                    var teacher: Teacher,
                    var semester: Int,
                    var year: Int,
                    var maxScore: Int)