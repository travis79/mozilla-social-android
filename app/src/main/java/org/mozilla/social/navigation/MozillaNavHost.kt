package org.mozilla.social.navigation

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.mozilla.social.core.ui.postcard.PostCardNavigation
import org.mozilla.social.feature.account.accountScreen
import org.mozilla.social.feature.account.follows.accountFollowersScreen
import org.mozilla.social.feature.account.follows.accountFollowingScreen
import org.mozilla.social.feature.auth.loginScreen
import org.mozilla.social.feature.hashtag.hashTagScreen
import org.mozilla.social.feature.report.reportScreen
import org.mozilla.social.feature.settings.settingsScreen
import org.mozilla.social.feature.thread.threadScreen
import org.mozilla.social.feed.FEED_ROUTE
import org.mozilla.social.feed.feedScreen
import org.mozilla.social.post.newPostScreen
import org.mozilla.social.search.SEARCH_ROUTE
import org.mozilla.social.search.SearchScreen
import org.mozilla.social.search.searchScreen
import org.mozilla.social.ui.AppState

@Composable
fun MozillaNavHost(appState: AppState) {
    val context = LocalContext.current
    NavHost(navController = appState.navController, startDestination = Routes.SPLASH) {
        splashScreen(
            navigateToLogin = appState::navigateToLoginScreen,
            navigateToLoggedInGraph = appState::navigateToLoggedInGraph,
        )
        loginScreen(navigateToLoggedInGraph = appState::navigateToLoggedInGraph)
        mainGraph(
            appState = appState,
            context = context,
        )
    }
}

fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit,
    navigateToLoggedInGraph: () -> Unit
) {
    composable(route = Routes.SPLASH) {
        SplashScreen(
            navigateToLogin = navigateToLogin,
            navigateToLoggedInGraph = navigateToLoggedInGraph
        )
    }
}

private fun NavGraphBuilder.mainGraph(
    appState: AppState,
    context: Context
) {
    navigation(startDestination = FEED_ROUTE, route = MAIN_ROUTE) {
        val postCardNavigation = object : PostCardNavigation {
            override fun onReplyClicked(statusId: String) = appState.navigateToNewPost(statusId)
            override fun onPostClicked(statusId: String) = appState.navigateToThread(statusId)
            override fun onReportClicked(accountId: String, statusId: String) =
                appState.navigateToReport(accountId, statusId)

            override fun onAccountClicked(accountId: String) = appState.navigateToAccount(accountId)
            override fun onHashTagClicked(hashTag: String) = appState.navigateToHashTag(hashTag)
            override fun onLinkClicked(url: String) {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(
                        context,
                        url.toUri(),
                    )
            }
        }

        feedScreen(
            postCardNavigation = postCardNavigation,
        )
        composable(route = Routes.DISCOVER) {
            SearchScreen()
        }
        settingsScreen(onLogout = appState::navigateToLoginScreen)
        accountScreen(
            onFollowingClicked = appState::navigateToAccountFollowing,
            onFollowersClicked = appState::navigateToAccountFollowers,
            onCloseClicked = { appState.popBackStack() },
            postCardNavigation = postCardNavigation,
        )
        accountFollowingScreen()
        accountFollowersScreen()
        newPostScreen(
            onStatusPosted = { appState.popBackStack() },
            onCloseClicked = { appState.popBackStack() },
        )
        threadScreen(
            onCloseClicked = { appState.popBackStack() },
            postCardNavigation = postCardNavigation,
        )
        reportScreen(
            onReported = { appState.popBackStack() },
            onCloseClicked = { appState.popBackStack() },
        )
        hashTagScreen(
            onCloseClicked = { appState.popBackStack() },
            postCardNavigation = postCardNavigation,
        )

        composable(route = Routes.BOOKMARKS) { Text(text = "bookmarks") }
    }
}

const val MAIN_ROUTE = "main"