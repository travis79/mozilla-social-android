package org.mozilla.social.model.request

import org.mozilla.social.model.Status
import org.mozilla.social.model.StatusVisibility

/**
 * Object used to post a new [Status].
 */
data class StatusCreate(

    /**
     * Text content of the status.
     *
     * If [mediaIds] is provided, this becomes optional.
     * Attaching a [poll] is optional while [status] is provided.
     */
    val status: String? = null,

    /**
     * Array of attachment ids to be attached as media.
     *
     * If provided, [status] becomes optional, and [poll] cannot be used.
     */
    val mediaIds: List<String>? = null,
    val poll: PollCreate? = null,

    /**
     * ID of the status being replied to, if status is a reply.
     */
    val inReplyToId: String? = null,

    /**
     * Mark status and attached media as sensitive?
     */
    val isSensitive: Boolean? = null,

    /**
     * Text to be shown as a warning or subject before the actual content.
     *
     * Statuses are generally collapsed behind this field.
     */
    val contentWarningText: String? = null,

    /**
     * Visibility of the posted status.
     */
    val visibility: StatusVisibility? = null,

    /**
     * ISO 639-1 language code for this status.
     */
    val language: String? = null
)
