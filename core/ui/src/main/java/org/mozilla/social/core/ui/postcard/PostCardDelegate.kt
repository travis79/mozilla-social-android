package org.mozilla.social.core.ui.postcard

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.mozilla.social.common.logging.Log
import org.mozilla.social.core.data.repository.StatusRepository

class PostCardDelegate(
    private val coroutineScope: CoroutineScope,
    private val statusRepository: StatusRepository,
    private val log: Log,
    private val onReplyClicked: (String) -> Unit,
) : PostCardInteractions {

    private val _errorToastMessage = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val errorToastMessage = _errorToastMessage.asSharedFlow()

    override fun onVoteClicked(pollId: String, choices: List<Int>) {
        coroutineScope.launch {
            try {
                statusRepository.voteOnPoll(pollId, choices)
            } catch (e: Exception) {
                log.e(e)
                _errorToastMessage.emit("Error Voting")
            }
        }
    }

    override fun onReplyClicked(statusId: String) {
        onReplyClicked.invoke(statusId)
    }

    override fun onBoostClicked(statusId: String, isBoosting: Boolean) {
        coroutineScope.launch {
            if (isBoosting) {
                try {
                    statusRepository.boostStatus(statusId)
                } catch (e: Exception) {
                    log.e(e)
                    _errorToastMessage.emit("Error boosting")
                }
            } else {
                try {
                    statusRepository.undoStatusBoost(statusId)
                } catch (e: Exception) {
                    log.e(e)
                    _errorToastMessage.emit("Error undoing boost")
                }
            }
        }
    }

    override fun onFavoriteClicked(statusId: String, isFavoriting: Boolean) {
        coroutineScope.launch {
            if (isFavoriting) {
                try {
                    statusRepository.favoriteStatus(statusId)
                } catch (e: Exception) {
                    log.e(e)
                    _errorToastMessage.emit("Error adding favorite")
                }
            } else {
                try {
                    statusRepository.undoFavoriteStatus(statusId)
                } catch (e: Exception) {
                    log.e(e)
                    _errorToastMessage.emit("Error removing favorite")
                }
            }
        }
    }

    override fun onShareClicked() {
        super.onShareClicked()
    }
}