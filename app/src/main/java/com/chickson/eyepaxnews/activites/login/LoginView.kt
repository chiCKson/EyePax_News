package com.chickson.eyepaxnews.activites.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.repositories.UserRepository
import com.chickson.eyepaxnews.system.EyePaxDatabase
import com.chickson.eyepaxnews.ui.theme.lightGrey
import com.chickson.eyepaxnews.util.TestTags
import javax.inject.Inject

@Composable
fun LoginView(viewModel: LoginViewModel, testTags: TestTags) {

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary).padding(bottom = 30.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        if (viewModel.loginPressed.value || viewModel.registerPressed.value){
            OutlinedTextField(
                value = username,
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Please enter your username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = lightGrey,
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                    unfocusedLabelColor = lightGrey,
                    placeholderColor = lightGrey
                ),
                onValueChange = { it ->
                    username = it
                    viewModel.username.value = it.text
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).testTag(testTags.usernameTag)
            )
            OutlinedTextField(
                value = password,
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = lightGrey,
                    focusedLabelColor = MaterialTheme.colors.onPrimary,
                    unfocusedLabelColor = lightGrey,
                    placeholderColor = lightGrey
                ),
                onValueChange = { it ->
                    password = it
                    viewModel.password.value = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).testTag(testTags.passwordTag),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff


                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
        }
        Button(onClick = {
            if (!viewModel.loginPressed.value){
                if(viewModel.registerPressed.value) {
                    viewModel.registerUser()
                } else {
                    viewModel.loginPressed.value = true
                    viewModel.registerPressed.value = false
                }
            } else {
                viewModel.login()
            }

        },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =  MaterialTheme.colors.onPrimary),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).height(50.dp)
        ) {
            Text(text =  if (viewModel.registerPressed.value) "Register" else "Login", color =  MaterialTheme.colors.primary )
        }
        Button(onClick = {

            if (!viewModel.registerPressed.value){
                if(viewModel.loginPressed.value){
                    viewModel.loginPressed.value = false
                    viewModel.registerPressed.value = false
                } else {
                    viewModel.loginPressed.value = false
                    viewModel.registerPressed.value = true
                }
            }  else {
                viewModel.registerPressed.value = false
            }
        },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =  MaterialTheme.colors.primary),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).height(50.dp)
        ) {
            Text(text = if (viewModel.loginPressed.value || viewModel.registerPressed.value) "Cancel" else "Register", color =  MaterialTheme.colors.onPrimary )
        }
    }
}