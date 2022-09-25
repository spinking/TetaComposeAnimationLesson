package studio.eyesthetics.tetaanimationcomposelesson.ui.cards

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.eyesthetics.tetaanimationcomposelesson.R
import studio.eyesthetics.tetaanimationcomposelesson.ui.custom.Card
import studio.eyesthetics.tetaanimationcomposelesson.ui.custom.ExtendedCard
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorBlack
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorBlue50
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.ColorRedMonza50

@Composable
fun CardsScreen() {
    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val interactionSource = remember { MutableInteractionSource() }

    val isBlur = remember { mutableStateOf(false) }

    //Transition animation
    val firstCardOffset = remember { mutableStateOf(CardsCompanion.DEFAULT_OFFSET) }
    val firstCardAnimatePositionX = animateFloatAsState(targetValue = firstCardOffset.value.first)
    val firstCardAnimatePositionY = animateFloatAsState(targetValue = firstCardOffset.value.second)

    val secondCardOffset = remember { mutableStateOf(CardsCompanion.DEFAULT_OFFSET) }
    val secondCardAnimatePositionX = animateFloatAsState(targetValue = secondCardOffset.value.first)
    val secondCardAnimatePositionY = animateFloatAsState(targetValue = secondCardOffset.value.second)

    val thirdCardOffset = remember { mutableStateOf(CardsCompanion.DEFAULT_OFFSET) }
    val thirdCardAnimatePositionX = animateFloatAsState(targetValue = thirdCardOffset.value.first)
    val thirdCardAnimatePositionY = animateFloatAsState(targetValue = thirdCardOffset.value.second)

    //Rotation animation
    val firstCardRotate = remember { mutableStateOf(CardsCompanion.FIRST_CARD_ROTATE_DEGREE) }
    val firstCardAnimateRotate = animateFloatAsState(targetValue = firstCardRotate.value)

    val secondCardRotate = remember { mutableStateOf(CardsCompanion.SECOND_CARD_ROTATE_DEGREE) }
    val secondCardAnimateRotate = animateFloatAsState(targetValue = secondCardRotate.value)

    //Scale animation
    val firstCardScale = remember { mutableStateOf(CardsCompanion.FIRST_CARD_SCALE) }
    val firstCardAnimateScale = animateFloatAsState(targetValue = firstCardScale.value)

    val secondCardScale = remember { mutableStateOf(CardsCompanion.SECOND_CARD_SCALE) }
    val secondCardAnimateScale = animateFloatAsState(targetValue = secondCardScale.value)

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    firstCardOffset.value = firstCardOffset.value.first to firstCardOffset.value.second - with(localDensity) { CardsCompanion.VERTICAL_CARD_PADDING * density * 2 }
                    firstCardRotate.value = CardsCompanion.DEFAULT_CARD_ROTATE_DEGREE
                    firstCardScale.value = CardsCompanion.DEFAULT_SCALE

                    secondCardOffset.value = secondCardOffset.value.first to secondCardOffset.value.second - with(localDensity) { CardsCompanion.VERTICAL_CARD_PADDING * density }
                    secondCardRotate.value = CardsCompanion.DEFAULT_CARD_ROTATE_DEGREE
                    secondCardScale.value = CardsCompanion.DEFAULT_SCALE

                    isBlur.value = true
                }
                is PressInteraction.Release -> {
                    //Do this transformation when press off without drag
                    firstCardOffset.value = CardsCompanion.DEFAULT_OFFSET
                    firstCardRotate.value = CardsCompanion.FIRST_CARD_ROTATE_DEGREE
                    firstCardScale.value = CardsCompanion.FIRST_CARD_SCALE

                    secondCardOffset.value = CardsCompanion.DEFAULT_OFFSET
                    secondCardRotate.value = CardsCompanion.SECOND_CARD_ROTATE_DEGREE
                    secondCardScale.value = CardsCompanion.SECOND_CARD_SCALE

                    thirdCardOffset.value = CardsCompanion.DEFAULT_OFFSET

                    isBlur.value = false
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Certificates",
            style = MaterialTheme.typography.h5,
            color = ColorBlack,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                //Worked only for 12 Android and above
                .blur(radius = if (isBlur.value) 8.dp else 0.dp),
            textAlign = TextAlign.Center
        )

        Card(
            color = ColorRedMonza50,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = firstCardAnimateScale.value
                    scaleY = firstCardAnimateScale.value
                    rotationZ = firstCardAnimateRotate.value
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 1f,
                        pivotFractionY = 0f
                    )
                    translationX = firstCardAnimatePositionX.value
                    translationY = firstCardAnimatePositionY.value
                }
        )

        Card(
            color = ColorBlue50,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = secondCardAnimateScale.value
                    scaleY = secondCardAnimateScale.value
                    rotationZ = secondCardAnimateRotate.value
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 1f,
                        pivotFractionY = 0f
                    )
                    translationX = secondCardAnimatePositionX.value
                    translationY = secondCardAnimatePositionY.value
                }
        )

        ExtendedCard(
            color = ColorBlack,
            titleText = stringResource(R.string.cards_title),
            subtitleText = stringResource(R.string.cards_subtitle),
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    translationX = thirdCardAnimatePositionX.value
                    translationY = thirdCardAnimatePositionY.value
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isBlur.value = true
                        },
                        onDrag = { change, dragAmount ->
                            scope.launch {
                                thirdCardOffset.value =
                                    thirdCardOffset.value.first + dragAmount.x to thirdCardOffset.value.second + dragAmount.y

                                delay(CardsCompanion.DEFAULT_CARD_ANIMATION_DELAY)
                                secondCardOffset.value =
                                    secondCardOffset.value.first + dragAmount.x to secondCardOffset.value.second + dragAmount.y

                                delay(CardsCompanion.DEFAULT_CARD_ANIMATION_DELAY)
                                firstCardOffset.value =
                                    firstCardOffset.value.first + dragAmount.x to firstCardOffset.value.second + dragAmount.y
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                thirdCardOffset.value = CardsCompanion.DEFAULT_OFFSET

                                delay(CardsCompanion.DEFAULT_CARD_ANIMATION_DELAY)
                                secondCardOffset.value = CardsCompanion.DEFAULT_OFFSET
                                secondCardRotate.value =
                                    CardsCompanion.SECOND_CARD_ROTATE_DEGREE
                                secondCardScale.value = CardsCompanion.SECOND_CARD_SCALE

                                delay(CardsCompanion.DEFAULT_CARD_ANIMATION_DELAY)
                                firstCardOffset.value = CardsCompanion.DEFAULT_OFFSET
                                firstCardRotate.value =
                                    CardsCompanion.FIRST_CARD_ROTATE_DEGREE
                                firstCardScale.value = CardsCompanion.FIRST_CARD_SCALE

                                isBlur.value = false
                            }
                        }
                    )
                },
            interactionSource = interactionSource
        )
    }
}