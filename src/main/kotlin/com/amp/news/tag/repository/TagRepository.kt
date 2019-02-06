package com.amp.news.tag.repository

import com.amp.news.core.repository.BaseRepository
import com.amp.news.tag.domain.Tag
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : BaseRepository<Tag>
