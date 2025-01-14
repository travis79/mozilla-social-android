package org.mozilla.social.core.data

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.mozilla.social.core.datastore.UserPreferencesDatastore
import org.mozilla.social.core.network.interceptors.AuthCredentialInterceptor

/**
 * Keeps the domain and access token in [AuthCredentialInterceptor] up to date
 */
@OptIn(DelicateCoroutinesApi::class)
class AuthCredentialObserver(
    userPreferencesDatastore: UserPreferencesDatastore,
    authCredentialInterceptor: AuthCredentialInterceptor,
) {
    init {
        GlobalScope.launch {
            coroutineScope {
                userPreferencesDatastore.accessToken.filterNotNull().collectLatest {
                    if (it.isNotBlank()) {
                        authCredentialInterceptor.accessToken = it
                    }
                }
            }
        }

        GlobalScope.launch {
            userPreferencesDatastore.domain.filterNotNull().collectLatest {
                if (it.isNotBlank()) {
                    authCredentialInterceptor.domain = it
                }
            }
        }
    }
}