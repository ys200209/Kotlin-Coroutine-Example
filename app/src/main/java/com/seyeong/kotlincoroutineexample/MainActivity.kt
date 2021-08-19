package com.seyeong.kotlincoroutineexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seyeong.kotlincoroutineexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonDownload.setOnClickListener { // 버튼이 클릭되면 가장 먼저 코루틴이 실행된다.
            CoroutineScope(Dispatchers.Main).launch { // 코루틴 스코프를 디스패처.Main 매개변수로 launch 한다.
                binding.progress.visibility = View.VISIBLE
            }
        }
    }
}

suspend fun loadImage(imageUrl: String) : Bitmap { // suspend 키워드를 사용하여 코루틴으로 함수 생성
    val url = URL(imageUrl) // URL 객체를 만들고
    val stream = url.openStream() // URL이 가지고 있는 openStream을
    return BitmapFactory.decodeStream(stream) // Bitmap 이미지로 저장한 후 반환한다.
}