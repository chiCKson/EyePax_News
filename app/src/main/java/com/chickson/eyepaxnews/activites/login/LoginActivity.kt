package com.chickson.eyepaxnews.activites.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chickson.eyepaxnews.activites.main.MainActivity
import com.chickson.eyepaxnews.activites.main.MainViewModel
import com.chickson.eyepaxnews.db.UserDao
import com.chickson.eyepaxnews.repositories.UserRepository
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.chickson.eyepaxnews.ui.theme.lightGrey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.error.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }

        viewModel.loginSuccess.observe(this){
           if (it){
               startActivity(Intent(this,MainActivity::class.java))
               this.finish()
           }
        }

        setContent {
            EyePaxNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginView(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun LoginView(viewModel: LoginViewModel) {
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
                placeholder = { Text(text = "Please enter your username")},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            OutlinedTextField(
                value = password,
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your Password")},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    var image = if (passwordVisible)
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
            border = BorderStroke(1.dp,MaterialTheme.colors.onPrimary),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).height(50.dp)
        ) {
            Text(text = if (viewModel.loginPressed.value || viewModel.registerPressed.value) "Cancel" else "Register", color =  MaterialTheme.colors.onPrimary )
        }
    }
}

