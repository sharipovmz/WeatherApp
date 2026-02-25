package com.example.weatherapp.temp

class SendMessageTgImpl : ISendMessage {
    override fun sendMessage(message: String) {
        // Telegram implementation
        println("Sending via Telegram: $message")
    }
}
