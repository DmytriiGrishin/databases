package ru.ifmo.dbstuff.full

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface FactTable1Repo: JpaRepository<FactTable1, String>

@Repository
interface FactTable2Repo: JpaRepository<FactTable2, String>

@Repository
interface FactTable3Repo: JpaRepository<FactTable3, String>

@Repository
interface FactTable4Repo: JpaRepository<FactTable4, String>

@Repository
interface TimeRepo: JpaRepository<Time, String>

@Repository
interface CampusRepo: JpaRepository<Campus, String>

@Repository
interface RoomRepo: JpaRepository<Room, String>

@Repository
interface BirthPlaceRepo: JpaRepository<BirthPlace, String>

@Repository
interface PublisherRepo: JpaRepository<Publisher, String>
