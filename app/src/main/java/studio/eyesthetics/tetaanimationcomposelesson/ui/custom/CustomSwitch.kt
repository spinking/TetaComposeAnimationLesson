package studio.eyesthetics.tetaanimationcomposelesson.ui.custom

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.CombinedModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorRedMonza
import studio.eyesthetics.tetaanimationcomposelesson.R

@Composable
fun CustomSwitch(
    switchRadius: Dp,
    switchPadding: Dp,
    titleText: String,
    subtitleText: String,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val offsetX = remember { mutableStateOf(with(localDensity) { switchPadding.toPx() }) }
    val alphaPercent = remember { mutableStateOf(0f) }

    val animatePosition = animateFloatAsState(targetValue = offsetX.value)
    val animateAlpha: Float by animateFloatAsState(
        targetValue = alphaPercent.value
    )

    Box(
        modifier = CombinedModifier(modifier, Modifier
            .height(switchPadding * 2 + switchRadius * 2)
            .background(ColorRedMonza, RoundedCornerShape(switchRadius + switchPadding))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        val newValue = offsetX.value + dragAmount.x
                        offsetX.value = when {
                            newValue >= size.width - switchRadius.toPx() * 2 -> (size.width - switchRadius.toPx() * 2 - switchPadding.toPx())
                            newValue <= switchPadding.toPx() -> switchPadding.toPx()
                            else -> newValue
                        }
                        alphaPercent.value = offsetX.value / (size.width / 100) / 100
                    },
                    onDragEnd = {
                        scope.launch {
                            when {
                                offsetX.value > size.width / 2 -> {
                                    offsetX.value =
                                        size.width.toFloat() - switchRadius.toPx() * 2 - switchPadding.toPx()
                                    alphaPercent.value = 1f
                                    onCheckedChanged.invoke(true)
                                }
                                else -> {
                                    offsetX.value = switchPadding.toPx()
                                    alphaPercent.value = 0f
                                    onCheckedChanged.invoke(false)
                                }
                            }
                        }
                    }
                )
            }
        )
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = titleText,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .graphicsLayer(alpha = animateAlpha)
            )

            Text(
                text = subtitleText,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .graphicsLayer(alpha = animateAlpha)
            )
        }

        Crossfade(
            targetState = alphaPercent,
            //TODO разобраться с длительностью анимации crossfade
            animationSpec = tween(300)
        ) {
            Box(modifier = Modifier
                .offset {
                    IntOffset(
                        x = animatePosition.value.toInt(),
                        y = switchPadding
                            .toPx()
                            .toInt()
                    )
                }
                .width(switchRadius * 2)
                .height(switchRadius * 2)
                .background(Color.White, RoundedCornerShape(switchRadius))
            ) {
                Icon(
                    painter = if (it.value > 0.5f)
                        painterResource(id = R.drawable.ic_done)
                    else
                        painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center),
                    tint = ColorRedMonza
                )
            }
        }
    }
}