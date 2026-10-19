package com.example.assignment2.ui
import retrofit2.HttpException
import java.io.IOException
fun errorMessage(error: Exception): String = when (error) {
 is HttpException -> when (error.code()) {
  400, 401, 403 -> "Request rejected. Check your student ID and case-sensitive first name, then try again."
  else -> "Server error (${error.code()}). Please try again."
 }
 is IOException -> "Unable to connect. Check your internet connection and retry."
 else -> "Unable to load data. Please try again."
}
