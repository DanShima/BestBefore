package com.ichimaya.androidhackathon.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun Long.toLocalDateTime(): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())