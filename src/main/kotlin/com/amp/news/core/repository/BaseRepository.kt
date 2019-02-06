package com.amp.news.core.repository

import com.amp.news.core.domain.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

/**
 * Base repository of all entities in application
 * Can be used to add more common methods in all repositories
 * @user Ilya Snitavets
 */
@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Long>, JpaSpecificationExecutor<T>
