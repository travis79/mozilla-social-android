package org.mozilla.social.feature.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val NOTIFICATIONS_ROUTE = "Notifications-route"

fun NavController.navigateToNotifications(navOptions: NavOptions? = null) {
    this.navigate(NOTIFICATIONS_ROUTE, navOptions)
}

fun NavGraphBuilder.notificationsScreen() {
    composable(route = NOTIFICATIONS_ROUTE) {
    }
}