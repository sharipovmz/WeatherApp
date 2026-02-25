package com.example.weatherapp.temp

class SendMessageTgGmail : ISendMessage {
    override fun sendMessage(message: String) {
        // Gmail implementation
        println("Sending via Gmail: $message")
    }
}
