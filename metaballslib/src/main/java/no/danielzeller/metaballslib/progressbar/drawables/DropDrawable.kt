package no.danielzeller.metaballslib.progressbar.drawables

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import no.danielzeller.metaballslib.progressbar.FrameRateCounter

/**
 * Draws the first drawable, then draws 3 dots decreasing in size which move eased towards
 * the first drawable, giving the effect of the drawable having a tail :P
 */
class DropDrawable(private val metaDrawable: Drawable, val idDropEnabled: Boolean) : Drawable() {

    var x = 0f
    var y = 0f
    private var x1 = 0f
    private  var y1 = 0f
    private  var x2 = 0f
    private  var y2 = 0f
    private  var x3 = 0f
    private  var y3 = 0f
    var ballSize = 0
    private val frameRate = FrameRateCounter()
    var easeSpeed = 25f
    var easeSpeedLast = 35f
    var pathPercent: Float = 0f
    var isDropDrawable = true

    init {
        isDropDrawable = idDropEnabled
    }

    override fun draw(canvas: Canvas) {
        val deltaTime = frameRate.timeStep()
        drawBall(canvas, x, y, 1f)
        if (idDropEnabled) {
            x1 += ((x - x1) * easeSpeed) * deltaTime
            y1 += ((y - y1) * easeSpeed) * deltaTime
            x2 += ((x1 - x2) * easeSpeed) * deltaTime
            y2 += ((y1 - y2) * easeSpeed) * deltaTime
            x3 += ((x2 - x3) * easeSpeedLast) * deltaTime
            y3 += ((y2 - y3) * easeSpeedLast) * deltaTime
            drawBall(canvas, x1, y1, 0.7f)
            drawBall(canvas, x2, y2, 0.5f)
            drawBall(canvas, x3, y3, 0.2f)
        }
    }

    private fun drawBall(canvas: Canvas, xPos: Float, yPos: Float, scale: Float) {
        val count = canvas.save()
        canvas.translate(xPos, yPos)
        setDrawableBounds(scale)
        metaDrawable.draw(canvas)
        canvas.restoreToCount(count)
    }

    private fun setDrawableBounds(sizePercent: Float) {
        metaDrawable.setBounds((-ballSize * sizePercent).toInt(), (-ballSize * sizePercent).toInt(), (ballSize * sizePercent).toInt(), (ballSize * sizePercent).toInt())
    }


    override fun setTint(tintColor: Int) {
        super.setTint(tintColor)
        metaDrawable.setTint(tintColor)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun setAlpha(alpha: Int) {

    }

    fun setAllCoordinatesToStart() {
        x1 += x
        y1 += y
        x2 += x
        y2 += y
        x3 += x
        y3 += y
    }
}