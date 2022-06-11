package com.example.firebase_playground_app.feature.counter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebase_playground_app.feature.Screen
import com.example.firebase_playground_app.model.Thing
import com.example.firebase_playground_app.utilities.dlog

object CounterScreen : Screen("counter") {
    @Composable
    override fun Content() {
        CounterScreen()
    }
}


@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    CounterScreenImpl(
        things = viewModel.things,
        onButtonClick = {
            viewModel.addThing(Thing("Test", 111))

        },
        onItemClick = { key ->
            //viewModel.removeThing(key)
        },
        onItemRightClick = { viewModel.increase(it) },
        onItemLeftClick = {}
    )
}

@Composable
private fun CounterScreenImpl(
    things: List<Pair<String, Thing>>,
    onButtonClick: () -> Unit,
    onItemClick: (key: String) -> Unit,
    onItemRightClick: (key: String) -> Unit,
    onItemLeftClick: (key: String) -> Unit
) {
    Box(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(things) { thingPair ->
                ThingItem(
                    thing = thingPair.second,
                    onItemClick = { onItemClick(thingPair.first) },
                    onRightClick = { onItemRightClick(thingPair.first) },
                    onLeftClick = { onItemLeftClick(thingPair.first) })
            }
        }

        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd)
                .size(50.dp),
            onClick = onButtonClick,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(imageVector = Icons.Rounded.Add, contentDescription = "")
        }
    }
}

@Composable
private fun ThingItem(
    thing: Thing,
    onItemClick: () -> Unit,
    onRightClick: () -> Unit,
    onLeftClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = CircleShape
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick.invoke() })

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { onLeftClick.invoke() }
                ),
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = ""
            )

            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = thing.name.toString())
                Text(text = thing.amount.toString())
            }

            Image(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = { onRightClick.invoke() }
                ),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}

private val mockThings = listOf(
    "1" to Thing("Thing 1", 1),
    "2" to Thing("Thing 2", 2),
    "3" to Thing("Thing 3", 3),
    "4" to Thing("Thing 4", 4)
)

@Preview(showBackground = true)
@Composable
private fun CounterScreenPreview() {
    CounterScreenImpl(mockThings, {}, {}, {}) {}
}