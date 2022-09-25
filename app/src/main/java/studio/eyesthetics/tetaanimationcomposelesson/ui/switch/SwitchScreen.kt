package studio.eyesthetics.tetaanimationcomposelesson.ui.switch

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import studio.eyesthetics.tetaanimationcomposelesson.ui.custom.CustomSwitch

@Composable
fun SwitchScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        val isSwitched = remember { mutableStateOf(false) }

        CustomSwitch(
            switchRadius = 22.dp,
            switchPadding = 4.dp,
            titleText = "Order 1000 rub",
            subtitleText = "Swipe to confirm",
            onCheckedChanged = {
                isSwitched.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}