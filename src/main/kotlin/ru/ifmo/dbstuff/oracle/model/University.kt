package ru.ifmo.dbstuff.oracle.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class University (@Id val id: String,
                       val name: String)