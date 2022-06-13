package com.chickson.eyepaxnews.activites.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.chickson.eyepaxnews.activites.main.MainActivity
import com.chickson.eyepaxnews.ui.theme.EyePaxNewsTheme
import com.chickson.eyepaxnews.util.TestTags
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var testTags: TestTags

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
                    LoginView(viewModel = viewModel,testTags = testTags)
                }
            }
        }
    }
}



