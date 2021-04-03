package com.example.minutehandview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.app.Activity
import android.content.Context

val foreColor : Int = Color.parseColor("#64DD17")
val backColor : Int = Color.parseColor("#BDBDBD")
val strokeFactor : Float = 90f
val arcFactor : Float = 4.9f
val minuteFactor : Float = 7.8f
val delay : Long = 20
val deg : Float = 360f
val hours : Int = 12
val hourDeg : Float = deg / hours
