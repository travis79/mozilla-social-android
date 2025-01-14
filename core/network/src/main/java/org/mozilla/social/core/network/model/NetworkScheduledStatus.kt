package org.mozilla.social.core.network.model

import kotlinx.datetime.Instant
import org.mozilla.social.core.network.model.paging.NetworkPageable

/**
 * Represents a status that will be published at a future scheduled date.
 */
data class NetworkScheduledStatus(
    val statusId: String,

    /**
     * Time at which the status should be posted.
     */
    val scheduledAt: Instant,

    /**
     * Status details.
     */
    val params: NetworkScheduledStatusParams,

    /**
     * Media attached to this status.
     */
    val mediaAttachments: List<NetworkAttachment>
) : NetworkPageable {

    override val id: String
        get() = statusId
}
