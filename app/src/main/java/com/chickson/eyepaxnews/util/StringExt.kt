package com.chickson.eyepaxnews.util

import java.security.MessageDigest


fun String.hash(): String{
        val bytes = this.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }

}