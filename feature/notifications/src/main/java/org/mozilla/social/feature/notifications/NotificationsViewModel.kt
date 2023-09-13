package org.mozilla.social.feature.notifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mozilla.social.core.data.repository.AccountRepository
import org.mozilla.social.model.Notification

class NotificationsViewModel(
    private val accountRepository: AccountRepository,
) : ViewModel() {

    val notifications: Flow<List<Notification>> =
        getUserNotifications()
    private fun getUserNotifications(): Flow<List<Notification>> {
        return flow {
            val response = accountRepository.getNotifications()
            try {
                emit(response)
            } catch(e: Exception) {

            }
        }
    }
}