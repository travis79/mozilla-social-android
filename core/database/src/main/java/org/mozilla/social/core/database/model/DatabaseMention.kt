package org.mozilla.social.core.database.model

import kotlinx.serialization.Serializable

/**
 * Represents a mention of a user within the content of a status.
 */
@Serializable
data class DatabaseMention(
    val accountId: String,

    /**
     * The username of the mentioned user.
     */
    val username: String,

    /**
     * The WebFinger URI of the mentioned user.
     *
     * Equivalent to [username] for local users, or username@domain for remote users.
     */
    val acct: String,

    /**
     * The URL of the mentioned user's profile.
     */
    val url: String
)
