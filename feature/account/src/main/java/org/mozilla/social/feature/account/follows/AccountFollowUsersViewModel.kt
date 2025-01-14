package org.mozilla.social.feature.account.follows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.mozilla.social.core.data.repository.AccountRepository
import org.mozilla.social.core.domain.AccountIdFlow
import org.mozilla.social.model.Account

class AccountFollowUsersViewModel(
    accountIdFlow: AccountIdFlow,
    private val accountRepository: AccountRepository
) : ViewModel() {
    val accountId: StateFlow<String?> =
        accountIdFlow()
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val accountFollowers: Flow<List<Account>> =
        accountId.flatMapLatest {
            if (it != null) {
                getAccountFollowers(it)
            } else flowOf()
        }

    val accountFollowing: Flow<List<Account>> =
        accountId.flatMapLatest {
            if (it != null) {
                getAccountFollowing(it)
            } else flowOf()
        }

    private fun getAccountFollowers(accountId: String): Flow<List<Account>> {
        return flow {
            val response = accountRepository.getAccountFollowers(accountId)
            try {
                emit(response)
            } catch (e: Exception) {

            }
        }
    }

    private fun getAccountFollowing(accountId: String): Flow<List<Account>> {
        return flow {
            val response = accountRepository.getAccountFollowing(accountId)
            try {
                emit(response)
            } catch (e: Exception) {

            }
        }
    }
}