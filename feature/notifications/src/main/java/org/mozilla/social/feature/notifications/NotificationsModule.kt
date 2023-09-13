package org.mozilla.social.feature.notifications

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notificationsModule = module {
    viewModel { parameters -> NotificationsViewModel(get()) }
}