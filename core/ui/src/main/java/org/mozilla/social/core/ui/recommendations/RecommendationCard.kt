package org.mozilla.social.core.ui.recommendations

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.mozilla.social.core.designsystem.component.MoSoCard
import org.mozilla.social.core.designsystem.icon.MoSoIcons
import org.mozilla.social.core.designsystem.theme.Typography
import org.mozilla.social.model.Recommendation

@Composable
fun RecommendationCarouselCard(modifier: Modifier, recommendation: Recommendation) {
    val context = LocalContext.current

    MoSoCard(
        modifier = modifier
            .height(140.dp)
            .padding(4.dp),
    ) {
        Row(modifier = Modifier
            .clickable {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, Uri.parse(recommendation.url))
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {

                Row(
                    modifier = Modifier
                        .weight(2f)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = recommendation.title,
                        modifier = Modifier
                            .weight(2f),
                        style = Typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp)),
                        model = recommendation.imageUrl,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Text(
                        text = recommendation.publisher,
                        style = Typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,

                        )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ) {
                        BottomIconButton(
                            onClick = { },
                            imageVector = MoSoIcons.BookmarkBorder,
                        )
                        BottomIconButton(
                            onClick = { },
                            imageVector = MoSoIcons.Share,
                        )
                    }
                }


            }

        }


//            Column(
//                modifier = Modifier
//                    .padding(8.dp)
//                    .weight(1f)
//            )
    }

}


@Composable
private fun BottomIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.wrapContentSize()
    ) {
        Icon(
            imageVector = imageVector,
            "",
        )
    }
}
