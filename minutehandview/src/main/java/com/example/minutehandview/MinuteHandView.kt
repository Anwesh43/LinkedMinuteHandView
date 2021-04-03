package com.example.minutehandview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
    "#64DD17",
    "#f44336",
    "#FF5722",
    "#004D40",
    "#2962FF"
).map {
    Color.parseColor(it)
}.toTypedArray()

val backColor : Int = Color.parseColor("#BDBDBD")
val strokeFactor : Float = 90f
val arcFactor : Float = 4.9f
val minuteFactor : Float = 7.8f
val delay : Long = 20
val deg : Float = 360f
val hours : Int = 12
val hourDeg : Float = deg / hours
val parts : Int = hours + 2
val scGap : Float = 0.02f / parts

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n)
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()
