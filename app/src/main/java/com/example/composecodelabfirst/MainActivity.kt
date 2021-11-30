package com.example.composecodelabfirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.composecodelabfirst.ui.theme.ComposeCodelabFirstTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodelabFirstTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var shouldOnBoarding by rememberSaveable { mutableStateOf(true) }
    if (shouldOnBoarding) {
        OnBoardingScreen(onContinueClicked = { shouldOnBoarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to my first codelab on Compose")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            )
            {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(100) { "Ichigo" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expended by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expended) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(
                    text = "Hello",
                    color = Color.Black
                )
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expended = !expended }
            ) {
                Text(if (expended) "Show more" else "Show less")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCodelabFirstTheme {
        Greeting("Grimmjow")
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun PreviewOnBoardingScreen() {
    ComposeCodelabFirstTheme() {
        OnBoardingScreen(onContinueClicked = {})
    }
}