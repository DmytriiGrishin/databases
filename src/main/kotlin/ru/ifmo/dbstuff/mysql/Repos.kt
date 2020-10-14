package ru.ifmo.dbstuff.mysql

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ConferenceRepo: JpaRepository<Conference, String>

@Repository
interface ParticipantRepo: JpaRepository<Participant, String>

@Repository
interface PublicationRepo: JpaRepository<Publication, String>

@Repository
interface EditionRepo: JpaRepository<Edition, String>

@Repository
interface LibraryRecordRepo: JpaRepository<LibraryRecord, String>