package org.mozilla.social.core.ui.poll

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.mozilla.social.core.designsystem.icon.MoSoIcons
import org.mozilla.social.core.designsystem.theme.MoSoTheme
import org.mozilla.social.core.designsystem.utils.NoRipple
import org.mozilla.social.core.ui.R

@Composable
fun Poll(
    pollUiState: PollUiState,
    pollInteractions: PollInteractions,
) {
    Column {
        val userVotes = rememberSaveable(pollUiState) { mutableStateOf(pollUiState.usersVotes) }
        pollUiState.pollOptions.forEachIndexed { index, pollOptionUiState ->
            PollOption(
                userVotes = userVotes,
                optionIndex = index,
                pollUiState = pollUiState,
                pollOptionUiState = pollOptionUiState,
                onOptionSelected = { optionIndex ->
                    userVotes.value = if (pollUiState.isMultipleChoice) {
                        if (userVotes.value.contains(optionIndex)) {
                            userVotes.value.toMutableList().apply { remove(optionIndex) }
                        } else {
                            userVotes.value.toMutableList().apply { add(optionIndex) }
                        }
                    } else {
                        if (userVotes.value.contains(optionIndex)) {
                            userVotes.value.toMutableList().apply { remove(optionIndex) }
                        } else {
                            listOf(optionIndex)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
        }
        Text(text = pollUiState.pollInfoText.build(LocalContext.current))
        if (pollUiState.canVote) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { pollInteractions.onVoteClicked(pollUiState.pollId, userVotes.value) },
                enabled = userVotes.value.isNotEmpty(),
            ) {
                Text(text = stringResource(id = R.string.vote_button))
            }
        }
    }
}

@Composable
private fun PollOption(
    userVotes: MutableState<List<Int>>,
    optionIndex: Int,
    pollUiState: PollUiState,
    pollOptionUiState: PollOptionUiState,
    onOptionSelected: (index: Int) -> Unit,
) {
    val height = 40.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(90.dp))
            .border(
                width = 1.dp,
                color =  MoSoTheme.colors.borderPrimary,
                shape = RoundedCornerShape(90.dp),
            ),
    ) {
        PollOptionFill(
            height = height,
            pollUiState = pollUiState,
            pollOptionUiState = pollOptionUiState
        )
        PollOptionText(
            height = height,
            userVotes = userVotes,
            optionIndex = optionIndex,
            pollUiState = pollUiState,
            pollOptionUiState = pollOptionUiState,
            onOptionSelected = onOptionSelected,
        )
    }
}

@Composable
private fun PollOptionFill(
    height: Dp,
    pollUiState: PollUiState,
    pollOptionUiState: PollOptionUiState,
) {
    if (pollUiState.showResults) {
        Box(
            modifier = Modifier
                .height(height)
                .fillMaxWidth(fraction = pollOptionUiState.fillFraction)
                .clip(RoundedCornerShape(90.dp)),
        ) {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun PollOptionText(
    height: Dp,
    userVotes: MutableState<List<Int>>,
    optionIndex: Int,
    pollUiState: PollUiState,
    pollOptionUiState: PollOptionUiState,
    onOptionSelected: (index: Int) -> Unit,
) {
    NoRipple {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clickable(
                    enabled = pollUiState.canVote
                ) {
                    onOptionSelected(optionIndex)
                },
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                text = pollOptionUiState.title
            )
            if (userVotes.value.contains(optionIndex)) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    imageVector = MoSoIcons.Check,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (pollUiState.showResults) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    text = pollOptionUiState.voteInfo.build(LocalContext.current)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PollPreview() {
    MoSoTheme {
//        Poll(
//            isUserCreatedPoll = false,
//            poll = Poll(
//                pollId = "1",
//                isExpired = false,
//                allowsMultipleChoices = false,
//                votesCount = 4,
//                options = listOf(
//                    PollOption(
//                        title = "option 1",
//                        votesCount = 1L
//                    ),
//                    PollOption(
//                        title = "option 2",
//                        votesCount = 3L
//                    )
//                ),
//                emojis = listOf(),
//                hasVoted = true,
//            ),
//            pollInteractions = object : PollInteractions {},
//        )
    }
}