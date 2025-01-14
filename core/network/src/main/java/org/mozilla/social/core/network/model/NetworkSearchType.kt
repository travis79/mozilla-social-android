package org.mozilla.social.core.network.model

enum class NetworkSearchType(val value: String) {

    /**
     * Search for accounts.
     */
    Accounts("accounts"),

    /**
     * Search for hashtags.
     */
    Hashtags("hashtags"),

    /**
     * Search for statuses.
     */
    Statuses("statuses")
}
