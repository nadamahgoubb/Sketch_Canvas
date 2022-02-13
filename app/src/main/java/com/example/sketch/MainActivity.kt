package com.example.sketch

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sketch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var canvasView: DrawingView
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        canvasView = DrawingView(this)
        binding.rootLayout.addView(canvasView)


        binding.btnArrow.setOnClickListener(this)
        binding.btnPen.setOnClickListener(this)
        binding.btnCircle.setOnClickListener(this)
        binding.btnColors.setOnClickListener(this)
        binding.btnRect.setOnClickListener(this)
        binding.colorBlack.setOnClickListener(this)
        binding.colorBlue.setOnClickListener(this)
        binding.colorRed.setOnClickListener(this)
        binding.colorGreen.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_arrow -> {
                canvasView.shape = 2
                canvasView.isDrawing = true
                canvasView!!.reset()

            }
            R.id.btn_circle -> {
                canvasView.shape = 4
                canvasView.isDrawing = true

            }
            R.id.btn_pen -> {
                canvasView.shape = 1
                canvasView.isDrawing = true
            }

            R.id.btn_colors -> showColorLens()
            R.id.btn_rect -> {
                canvasView.shape = 3
                canvasView.isDrawing = true
            }
            R.id.color_black -> {
                canvasView.currentcolor = resources.getColor(R.color.black)
                binding.cardColors.visibility = View.GONE

                canvasView.reset()
                canvasView.isDrawing = true
            }
            R.id.color_blue -> {
                canvasView.currentcolor = resources.getColor(R.color.blue)
                binding.cardColors.visibility = View.GONE

                canvasView.reset()
                canvasView.isDrawing = true
            }
            R.id.color_green -> {
                canvasView.currentcolor = resources.getColor(R.color.green)
                binding.cardColors.visibility = View.GONE

                canvasView.reset()
                canvasView.isDrawing = true
            }
            R.id.color_red -> {
                canvasView.currentcolor = resources.getColor(R.color.red)
                binding.cardColors.visibility = View.GONE

                canvasView.reset()
                canvasView.isDrawing = true
            }


        }
    }

    private fun showColorLens() {
        if (binding.cardColors.visibility == View.VISIBLE) {
            binding.cardColors.visibility = View.GONE
        } else {
            binding.cardColors.visibility = View.VISIBLE

        }
    }


}