package com.dobby.backend.infrastructure.database.repository

import com.dobby.backend.infrastructure.database.entity.ParticipantEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository: JpaRepository<ParticipantEntity, Long> {
}