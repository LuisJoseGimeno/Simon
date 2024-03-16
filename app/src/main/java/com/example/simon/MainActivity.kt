    package com.example.simon;
    import android.graphics.Color
    import android.graphics.Paint
    import android.graphics.PorterDuff
    import android.os.Bundle
    import android.os.Handler
    import android.os.Looper
    import android.util.Log
    import android.view.MotionEvent
    import android.view.SurfaceHolder
    import androidx.appcompat.app.AppCompatActivity
    import com.example.simon.databinding.ActivityMainBinding
    import kotlin.random.Random

    class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {
        private lateinit var binding: ActivityMainBinding

        private val glow = 255
        private val dark = 128
        private var red = Color.argb(dark, 255, 0, 0)
        private var yellow = Color.argb(dark, 255, 255, 0)
        private var green = Color.argb(dark, 0, 255, 0)
        private var blue = Color.argb(dark,0, 0, 255)

        val gameRandomList = mutableListOf<Int>()
        var gameSequence = 1;
        private var isClickEnabled = true
        private val clickDelayMillis: Long = 1000
        private var gameStart = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val surfaceView = binding.surfaceView
            surfaceView.holder.addCallback(this)

            surfaceView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val x = event.x
                        val y = event.y
                        detectTouch(x, y)
                        true
                    }
                    else -> false
                }
            }
        }
        override fun surfaceCreated(holder: SurfaceHolder) {
            drawCanvas(holder)
        }
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
        private fun drawCanvas(holder: SurfaceHolder){
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            val centerX = canvas.width / 2f
            val centerY = canvas.height / 2f
            val radius = (canvas.height.coerceAtMost(canvas.width) * 0.4).toFloat()
            val smallCircleRadius = 200f
            val paint = Paint().apply {
                isAntiAlias = true
            }
            //region draw colors
            paint.color = red
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                10f,
                70f,
                true,
                paint
            )
            paint.color = green
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                100f,
                70f,
                true,
                paint
            )
            paint.color = yellow
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                190f,
                70f,
                true,
                paint
            )
            paint.color = blue
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                280f,
                70f,
                true,
                paint
            )
            paint.color = Color.BLACK
            canvas.drawCircle(centerX, centerY, smallCircleRadius, paint)
            // endregion
            holder.unlockCanvasAndPost(canvas)
        }
        private fun changeColor(alpha: Int, selected: Int){
            when (selected){
                0 -> {
                    red = Color.argb(alpha, 255, 0, 0)
                }
                1 -> {
                    green = Color.argb(alpha, 0, 255, 0)
                }
                2 -> {
                    yellow = Color.argb(alpha, 255, 255, 0)
                }
                3 -> {
                    blue = Color.argb(alpha, 0, 0, 255)
                }
                else -> {
                    red = Color.argb(alpha, 255, 0, 0)
                    green = Color.argb(alpha, 0, 255, 0)
                    yellow = Color.argb(alpha, 255, 255, 0)
                    blue = Color.argb(alpha, 0, 0, 255)
                }
            }
        }
        private fun detectTouch(x: Float, y: Float) {

            var selected = -1;

            val centerX = binding.surfaceView.width / 2f
            val centerY = binding.surfaceView.height / 2f
            val radius = (binding.surfaceView.height.coerceAtMost(binding.surfaceView.width) * 0.4).toFloat()
            val smallCircleRadius = 200f

            val distance = Math.sqrt(Math.pow((x - centerX).toDouble(), 2.0) + Math.pow((y - centerY).toDouble(), 2.0)).toFloat()

            if (distance >= radius) {
                return
            }
            if (distance <= smallCircleRadius) {
                gameStart = !gameStart
                startGame()
                return
            }
            if(!gameStart){
                return
            }
            if (!isClickEnabled) {
                return
            }
            isClickEnabled = false

            val angle = Math.toDegrees(Math.atan2((y - centerY).toDouble(), (x - centerX).toDouble()))

            val adjustedAngle = if (angle < 0) angle + 360 else angle

             when {
                /*Red*/adjustedAngle >= 10 && adjustedAngle < 80 -> {
                    selected = 0
                    changeColor(glow,selected)
                }
                /*Green*/adjustedAngle >= 100 && adjustedAngle < 170 ->{
                    selected = 1
                    changeColor(glow,selected)
                }
                /*Yellow*/adjustedAngle >= 190 && adjustedAngle < 260 ->{
                    selected = 2
                    changeColor(glow,selected)
                }
                /*Blue*/adjustedAngle >= 280 && adjustedAngle < 350 -> {
                    selected = 3
                    changeColor(glow,selected)
                }
                else -> drawCanvas(binding.surfaceView.holder)
            }
            drawCanvas(binding.surfaceView.holder)
            Handler(Looper.getMainLooper()).postDelayed({
                changeColor(dark,selected)
                drawCanvas(binding.surfaceView.holder)
            }, clickDelayMillis)
        }
        private fun createGameArray(){
            repeat(50) {
                gameRandomList.add(Random.nextInt(4))
            }
        }
        private fun startGame(){
            if(!gameStart){
                return
            }
            gameSequence = 1;
            createGameArray();
            playArray();
        }
        private fun playArray(){
            isClickEnabled = false;
            for(i in 0 until gameSequence){
                changeColor(glow, gameRandomList[i])
                drawCanvas(binding.surfaceView.holder)
                Handler(Looper.getMainLooper()).postDelayed({
                    changeColor(dark, gameRandomList[i])
                    drawCanvas(binding.surfaceView.holder)
                    gameSequence++
                }, clickDelayMillis)
            }
            isClickEnabled = true;
        }
    }
