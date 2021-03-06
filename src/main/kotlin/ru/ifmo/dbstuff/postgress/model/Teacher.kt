package ru.ifmo.dbstuff.postgress.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Teacher (@Id val id: String,
                    @OneToOne
                    var person: Person,
                    @ManyToOne
                    var uni: University)