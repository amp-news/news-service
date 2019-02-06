package com.amp.news.comment.repository

import com.amp.news.comment.domain.Comment
import com.amp.news.core.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : BaseRepository<Comment>
