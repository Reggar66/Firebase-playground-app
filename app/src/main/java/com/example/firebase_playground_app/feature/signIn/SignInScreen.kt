package com.example.firebase_playground_app.feature.signIn

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firebase_playground_app.feature.Screen
import com.example.firebase_playground_app.feature.counter.CounterScreen

object SignInScreen : Screen("signIn") {

    @Composable
    override fun Content(navController: NavController) {
        SignInScreen(navController)
    }
}

enum class SignInOption {
    PICKER, Anonymous, Mail, TEST_LOGOUT
}

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = viewModel()) {
    SignInScreenImpl(
        signInAnon = {
            viewModel.signInAnonymously(onSuccess = {
                navController.navigate(
                    CounterScreen.route
                )
            })
        },
        signInMail = { email, password ->
            viewModel.signInMail(
                email = email,
                password = password,
                onSuccess = {
                    navController.navigate(
                        CounterScreen.route
                    )
                }
            )
        },
        signOut = { viewModel.signOut() }
    )
}

@Composable
fun SignInScreenImpl(
    signInMail: (email: String, password: String) -> Unit,
    signInAnon: () -> Unit,
    signOut: () -> Unit
) {
    var loginOption by remember {
        mutableStateOf(SignInOption.PICKER)
    }

    SignInPicker(
        option = loginOption,
        onSignOptionPicked = { option -> loginOption = option },
        onSignInMail = signInMail,
        onSignInAnon = signInAnon,
        onSignOut = signOut
    )
}

@Composable
fun SignInPicker(
    option: SignInOption,
    onSignOptionPicked: (option: SignInOption) -> Unit,
    onSignInMail: (email: String, password: String) -> Unit,
    onSignInAnon: () -> Unit,
    onSignOut: () -> Unit
) =
    when (option) {
        SignInOption.Mail -> MailLogin(
            signIn = onSignInMail,
            onBack = { onSignOptionPicked(SignInOption.PICKER) })
        else -> SignOptions(
            onSignOptionPicked = onSignOptionPicked,
            signAnon = onSignInAnon,
            onSignOut = onSignOut
        )
    }

@Composable
fun SignOptions(
    onSignOptionPicked: (option: SignInOption) -> Unit, signAnon: () -> Unit, onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onSignOptionPicked(SignInOption.Mail) }) {
            Text(text = "With mail")
        }

        Button(onClick = signAnon) {
            Text(text = "Without login")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onSignOut) {
            Text(text = "Sign out (Test)")
        }
    }
}

@Composable
fun MailLogin(signIn: (email: String, password: String) -> Unit, onBack: () -> Unit) {
    var mail by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = mail,
            onValueChange = { mail = it.filterNot { it.isWhitespace() } },
            label = {
                Text(text = "E-mail")
            },
            singleLine = true,
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it.filterNot { it.isWhitespace() } },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { signIn.invoke(mail, password) }) {
                Text(text = "SignIn")
            }
            Button(onClick = { onBack.invoke() }) {
                Text(text = "Back")
            }
        }
        TestUsersButtons(onUserPicked = { email, pass ->
            mail = email
            password = pass
        })
    }
}

@Composable
private fun TestUsersButtons(onUserPicked: (email: String, pass: String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = { onUserPicked("test1@mail.com", "123456") }) {
            Text(text = "Test1 user")
        }
        Button(onClick = { onUserPicked("test2@mail.com", "123456") }) {
            Text(text = "Test2 user")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreenImpl({ _, _ -> }, {}, {})
}