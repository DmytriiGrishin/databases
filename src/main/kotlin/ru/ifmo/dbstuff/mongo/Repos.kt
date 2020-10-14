package ru.ifmo.dbstuff.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MongoPersonRepo: MongoRepository<Person, String> {
}

@Repository
interface MongoRoomRepo: MongoRepository<Room, String> {
}

@Repository
interface MongoStudyInfoRepo: MongoRepository<StudyInfo, String> {
}

@Repository
interface MongoCampusRepo: MongoRepository<Campus, String> {
}

@Repository
interface MongoStayRepo: MongoRepository<Stay, String> {
}