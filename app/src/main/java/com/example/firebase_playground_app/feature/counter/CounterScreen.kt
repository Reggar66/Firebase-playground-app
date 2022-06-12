package com.example.firebase_playground_app.feature.counter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebase_playground_app.feature.Screen
import com.example.firebase_playground_app.model.Thing
import kotlinx.coroutines.launch

object CounterScreen : Screen("counter") {
    @Composable
    override fun Content() {
        CounterScreen()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetContent = {
            BottomSheetContent(onClick = { name, value ->
                viewModel.addThing(
                    name,
                    value
                )
            })
        }

    ) {
        CounterScreenImpl(
            things = viewModel.things,
            onButtonClick = {
                scope.launch {
                    sheetState.show()
                }
            },
            onItemClick = { /*viewModel.removeThing(key)*/ },
            onItemLongClick = { viewModel.removeThing(it.first) },
            onItemRightClick = { viewModel.increase(it) },
            onItemLeftClick = { viewModel.decrease(it) }
        )
    }
}

@Composable
private fun CounterScreenImpl(
    things: List<Pair<String, Thing>>,
    onButtonClick: () -> Unit,
    onItemClick: (item: Pair<String, Thing>) -> Unit,
    onItemLongClick: (item: Pair<String, Thing>) -> Unit,
    onItemRightClick: (item: Pair<String, Thing>) -> Unit,
    onItemLeftClick: (item: Pair<String, Thing>) -> Unit
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
                    onItemClick = { onItemClick(thingPair) },
                    onItemLongClick = { onItemLongClick(thingPair) },
                    onRightClick = { onItemRightClick(thingPair) },
                    onLeftClick = { onItemLeftClick(thingPair) })
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ThingItem(
    thing: Thing,
    onItemClick: () -> Unit,
    onItemLongClick: () -> Unit,
    onRightClick: () -> Unit,
    onLeftClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(onLongClick = onItemLongClick, onClick = onItemClick)
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable(
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
                modifier = Modifier
                    .size(50.dp)
                    .clickable(
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

@Composable
private fun BottomSheetContent(onClick: (name: String, value: String) -> Unit) {

    var text by remember {
        mutableStateOf("")
    }

    var value by remember {
        mutableStateOf("")
    }

    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterValue = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequesterName)
                    .fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Name") })
            OutlinedTextField(
                modifier = Modifier
                    .focusRequester(focusRequesterValue)
                    .fillMaxWidth(),
                value = value,
                onValueChange = { value = it },
                label = { Text(text = "Value") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Button(onClick = {
            onClick(text, value)
            text = ""
            value = ""
            focusManager.clearFocus()
        }) {
            Text(text = "Ok")
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
    CounterScreenImpl(mockThings, {}, {}, {}, {}) {}
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetContentPreview() {
    BottomSheetContent() { _, _ -> }
}