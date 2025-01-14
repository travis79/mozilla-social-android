package org.mozilla.social.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mozilla.social.core.designsystem.icon.MoSoIcons
import org.mozilla.social.core.designsystem.theme.MoSoTheme
import org.mozilla.social.model.StatusVisibility

@Composable
fun VisibilityDropDownButton(
    visibility: StatusVisibility,
    onVisibilitySelected: (StatusVisibility) -> Unit,
) {
    val expanded = remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded.value = true },
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    ) {
        when (visibility) {
            StatusVisibility.Public -> ButtonContent(
                icon = MoSoIcons.Public,
                text = stringResource(id = R.string.visibility_public)
            )
            StatusVisibility.Unlisted -> ButtonContent(
                icon = MoSoIcons.LockOpen,
                text = stringResource(id = R.string.visibility_unlisted)
            )
            StatusVisibility.Private -> ButtonContent(
                icon = MoSoIcons.Lock,
                text = stringResource(id = R.string.visibility_private)
            )
            StatusVisibility.Direct -> ButtonContent(
                icon = MoSoIcons.Message,
                text = stringResource(id = R.string.visibility_direct)
            )
        }
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Icon(
            MoSoIcons.ArrowDropDown,
            "",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }
    ) {
        DropDownItem(
            type = StatusVisibility.Public,
            icon = MoSoIcons.Public,
            text = stringResource(id = R.string.visibility_public),
            expanded = expanded,
            onVisibilitySelected = onVisibilitySelected
        )
        DropDownItem(
            type = StatusVisibility.Unlisted,
            icon = MoSoIcons.LockOpen,
            text = stringResource(id = R.string.visibility_unlisted),
            expanded = expanded,
            onVisibilitySelected = onVisibilitySelected
        )
        DropDownItem(
            type = StatusVisibility.Private,
            icon = MoSoIcons.Lock,
            text = stringResource(id = R.string.visibility_private),
            expanded = expanded,
            onVisibilitySelected = onVisibilitySelected
        )
        DropDownItem(
            type = StatusVisibility.Direct,
            icon = MoSoIcons.Message,
            text = stringResource(id = R.string.visibility_direct),
            expanded = expanded,
            onVisibilitySelected = onVisibilitySelected
        )
    }
}

@Composable
private fun ButtonContent(
    icon: ImageVector,
    text: String,
) {
    Icon(
        icon,
        "",
        tint = MaterialTheme.colorScheme.onSurface,
    )
    Spacer(modifier = Modifier.padding(start = 8.dp))
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
private fun DropDownItem(
    type: StatusVisibility,
    icon: ImageVector,
    text: String,
    expanded: MutableState<Boolean>,
    onVisibilitySelected: (StatusVisibility) -> Unit,
    contentDescription: String = "",
) {
    DropdownMenuItem(
        text = {
            Row {
                Icon(icon, contentDescription)
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text(text = text)
            }
        },
        onClick = {
            onVisibilitySelected(type)
            expanded.value = false
        }
    )
}

@Preview
@Composable
private fun VisibilityDropDownPreview() {
    MoSoTheme(
        true
    ) {
        VisibilityDropDownButton(
            visibility = StatusVisibility.Private,
            onVisibilitySelected = {}
        )
    }
}