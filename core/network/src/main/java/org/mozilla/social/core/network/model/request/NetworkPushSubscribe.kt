package org.mozilla.social.core.network.model.request

/**
 * Object used to subscribe to push notifications.
 */
data class NetworkPushSubscribe(

    /**
     * Push subscription settings.
     */
    val subscription: NetworkPushSubscription,

    /**
     * Notification alert settings.
     */
    val data: NetworkPushData
)
