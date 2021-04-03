package com.example.minutehandview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
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

fun Canvas.drawMinuteHand(scale : Float, w : Float, h : Float, paint : Paint) {
    val r : Float = Math.min(w, h) / arcFactor
    val m : Float = Math.min(w, h) / minuteFactor
    val h : Float = m / 2
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    save()
    translate(w / 2, h / 2)
    paint.style = Paint.Style.STROKE
    drawArc(RectF(-r, -r, r, r), 0f, 360f * sf1, false, paint)
    var curr : Float = 0f
    for (j in 0..(parts - 1)) {
        save()
        rotate(curr)
        drawLine(0f, 0f, 0f, -m * sf2, paint)
        restore()
        save()
        rotate(deg * sf.divideScale(2 + j, parts))
        drawLine(0f, 0f, 0f, -h * sf2, paint)
        restore()
        curr += hourDeg * sf.divideScale(2 + j, parts)
    }
    restore()
}

fun Canvas.drawMHNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawMinuteHand(scale, w, h, paint)
}

class MinuteHandView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}