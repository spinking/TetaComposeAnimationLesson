package studio.eyesthetics.tetaanimationcomposelesson.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import studio.eyesthetics.tetaanimationcomposelesson.R
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorWhite

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExtendedCard(
    color: Color,
    titleText: String,
    subtitleText: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) {
    Surface(
        onClick = {},
        modifier = CombinedModifier(modifier, Modifier
            .width(300.dp)
            .height(180.dp)
        ),
        shape = RoundedCornerShape(16.dp),
        color = color,
        elevation = 8.dp,
        interactionSource = interactionSource
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (title, subtitle, icon) = createRefs()

            Text(
                text = titleText,
                style = MaterialTheme.typography.h6,
                color = ColorWhite,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(icon.start)
                    top.linkTo(icon.top)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = subtitleText,
                style = MaterialTheme.typography.body1,
                color = ColorWhite,
                modifier = Modifier.constrainAs(subtitle) {
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom, margin = 8.dp)
                    end.linkTo(icon.start)
                    width = Dimension.fillToConstraints
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_badge),
                contentDescription = null,
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}