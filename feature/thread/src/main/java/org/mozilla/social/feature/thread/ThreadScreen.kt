package org.mozilla.social.feature.thread

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.mozilla.social.core.designsystem.component.MoSoTopBar
import org.mozilla.social.core.ui.postcard.PostCardList
import org.mozilla.social.core.ui.postcard.PostCardNavigation

@Composable
fun ThreadScreen(
    threadStatusId: String,
    onCloseClicked: () -> Unit,
    postCardNavigation: PostCardNavigation,
    viewModel: ThreadViewModel = koinViewModel(parameters = { parametersOf(
        threadStatusId,
        postCardNavigation,
    ) })
) {
    Column {
        MoSoTopBar(
            title = stringResource(id = R.string.thread_screen_title),
            onCloseClicked = onCloseClicked,
        )

        PostCardList(
            items = viewModel.statuses.collectAsState(emptyList()).value,
            postCardInteractions = viewModel.postCardDelegate
        )
    }
}
