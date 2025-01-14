package org.mozilla.social.model

/**
 * Represents an API error.
 */
data class Error(

    /**
     * The error message.
     */
    val error: String,

    /**
     * A longer description of the error.
     */
    val errorDescription: String? = null
)
