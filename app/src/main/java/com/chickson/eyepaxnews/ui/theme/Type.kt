package com.chickson.eyepaxnews.ui.theme


import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chickson.eyepaxnews.R

val nunito = FontFamily(
    Font(R.font.nunito_black),
    Font(R.font.nunito_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_light, weight = FontWeight.Light),
    Font(R.font.nunito_regular, weight = FontWeight.Normal),
    Font(R.font.nunito_semibold, weight = FontWeight.SemiBold),
    Font(R.font.nunito_extrabold, weight = FontWeight.ExtraBold),
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp
    ),
    body2 = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )

)

