package com.example.sakura.integrated.db.enum

enum class Grade(val description: String, val paid: Long) {
    GREEN("그린 등급", 0),
    SILVER("실버 등급", 100_000),
    GOLD("골드 등급", 500_000),
    BLACK("블랙 등급", 1_000_000),
}