package studio.eyesthetics.tetaanimationcomposelesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import studio.eyesthetics.tetaanimationcomposelesson.ui.switch.SwitchScreen
import studio.eyesthetics.tetaanimationcomposelesson.ui.theme.TetaAnimationComposeLessonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TetaAnimationComposeLessonTheme {
                SwitchScreen()
            }
        }
    }
}