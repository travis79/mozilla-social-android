package org.mozilla.social.core.network.model.request

/**
 * Push keys used for notifications.
 */
data class NetworkPushKeys(

    /**
     * User agent public key.
     *
     * Base64 encoded string of public key of ECDH key using prime256v1 curve.
     */
    val p256dhBase64: String,

    /**
     * Auth secret.
     *
     * Base64 encoded string of 16 bytes of random data.
     */
    val authBase64: String
)
