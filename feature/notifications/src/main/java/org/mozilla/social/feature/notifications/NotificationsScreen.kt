@file:OptIn(ExperimentalMaterial3Api::class)

package org.mozilla.social.feature.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.mozilla.social.core.designsystem.theme.MozillaSocialTheme
import org.mozilla.social.model.Notification
import org.mozilla.social.model.NotificationType

@Composable
internal fun NotificationsRoute(
    viewModel: NotificationsViewModel = koinViewModel(),
) {
    val notifications = viewModel.notifications.collectAsState(initial = null).value

    if (notifications != null) {
        NotificationsScreen(notifications = notifications)
    } else {
        //TODO: Show Error Screen
    }
}

@Composable
internal fun NotificationsScreen(
    notifications: List<Notification>
) {
    MozillaSocialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                HandleNotificationType(notifications = notifications)
            }
        }
    }
}

@Composable
internal fun HandleNotificationType(notifications: List<Notification>) {
    notifications.forEach {
        when(it.type) {
            NotificationType.Follow -> Follow(it)
            NotificationType.FollowRequest -> FollowRequest(it)
            NotificationType.Mention -> Mention(it)
            NotificationType.Boost -> Boost(it)
            NotificationType.Favourite -> Favourite(it)
            NotificationType.Poll -> Poll(it)
            NotificationType.Status -> Status(it)
        }
    }
}

@Composable
internal fun Follow(notification: Notification) {
    Row {
        Text(text = "${notification.account.username} followed you")
    }
}

@Composable
internal fun FollowRequest(notification: Notification) {
    Row {
        Text(text = "${notification.account.username} requested to follow you")
    }
}

@Composable
internal fun Mention(notification: Notification) {
    Row {
        Text(text = "${notification.account.username} mentioned you in a post:")
    }
}

@Composable
internal fun Boost(notification: Notification) {
    Row {
        Text(text = "${notification.account.username} boosted your post:")
    }
}

@Composable
internal fun Favourite(notification: Notification) {
    Row {
        Text(text = "${notification.account.username} favorited your post:")
    }
}

@Composable
internal fun Poll(notification: Notification) {
    Row {
        Text(text = "Your poll ended:")
    }
}

@Composable
internal fun Status(notification: Notification) {
    Row {
        Text(text = "Status update:")
    }
}

@Composable
internal fun AdminSignUp() {}

@Composable
internal fun AdminReport() {}

@Preview
@Composable
fun NotificationsScreenPreview() {
    MozillaSocialTheme {
    }
}