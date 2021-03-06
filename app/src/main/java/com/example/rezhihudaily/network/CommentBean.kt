package com.example.rezhihudaily.network

class CommentBean {
    lateinit var comments: List<Comment>

    data class Comment(
        val author: String,
        val content: String,
        val avatar: String,
        val time: String,
        val id: String,
        val likes: Int,
        val reply_to: ReplyTo
    )

    data class ReplyTo(
        val author: String,
        val content: String,
        val status: String,
        val id: String
    )
}