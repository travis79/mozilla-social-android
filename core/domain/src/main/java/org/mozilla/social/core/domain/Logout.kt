package org.mozilla.social.core.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mozilla.social.core.database.SocialDatabase
import org.mozilla.social.core.datastore.UserPreferencesDatastore

class Logout(
    private val userPreferencesDatastore: UserPreferencesDatastore,
    private val socialDatabase: SocialDatabase,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        userPreferencesDatastore.clearData()
        socialDatabase.clearAllTables()
    }
}