package ru.ifmo.dbstuff.postgress.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Spec (@Id var id: String,
                 val name: String,
                 @ManyToOne
                 var uni: University)