package com.chickson.eyepaxnews.activities.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chickson.eyepaxnews.activites.login.LoginView
import com.chickson.eyepaxnews.activites.login.LoginViewModel
import com.chickson.eyepaxnews.db.UserDao
import com.chickson.eyepaxnews.repositories.UserRepository
import com.chickson.eyepaxnews.system.EyePaxDatabase
import com.chickson.eyepaxnews.util.TestTags
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.*
import org.junit.runner.RunWith
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val mockViewModel = mockk<LoginViewModel>(relaxed = true)

    @Inject
    lateinit var testTags:TestTags

    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var  database: EyePaxDatabase
    private lateinit var dao: UserDao

    @Before
    fun init() {
        hiltRule.inject()
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EyePaxDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.userDao()
    }


    @Test
    fun testLoginScreenLoaded(){
        composeTestRule.setContent {
            LoginView(
                viewModel = LoginViewModel(userRepository = mockk<UserRepository>(relaxed = true)),
                testTags = testTags
            )
        }
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertDoesNotExist()
        composeTestRule.onNodeWithTag(testTags.usernameTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(testTags.passwordTag).assertDoesNotExist()
    }

    @Test
    fun onLoginClickFirstTime(){
        composeTestRule.setContent {
            LoginView(
                viewModel = LoginViewModel(userRepository = mockk<UserRepository>(relaxed = true)),
                testTags = testTags
            )
        }
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Register").assertDoesNotExist()
        composeTestRule.onNodeWithTag(testTags.usernameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTags.passwordTag).assertIsDisplayed()
    }

    @Test
    fun onCancelCLickAfterLoginFirstTime(){
        composeTestRule.setContent {
            LoginView(
                viewModel = LoginViewModel(userRepository = mockk<UserRepository>(relaxed = true)),
                testTags = testTags
            )
        }
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertDoesNotExist()
        composeTestRule.onNodeWithTag(testTags.usernameTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(testTags.passwordTag).assertDoesNotExist()
    }

    @Test
    fun onRegister(){
        val viewModel = LoginViewModel(userRepository = UserRepository(database))
        composeTestRule.setContent {
            LoginView(
                viewModel = viewModel,
                testTags = testTags
            )
        }
        composeTestRule.onNodeWithText("Register").performClick()
        composeTestRule.onNodeWithTag(testTags.usernameTag).performTextInput("kevin")
        composeTestRule.onNodeWithTag(testTags.passwordTag).performTextInput("asd")
        composeTestRule.onNodeWithText("Register").performClick()

        val user = dao.findByUserName(username = "kevin")
        Assert.assertEquals(user.username, "kevin")

    }



    @After
    fun teardown(){
        database.close()
    }
}


