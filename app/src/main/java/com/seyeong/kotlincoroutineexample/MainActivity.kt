package com.seyeong.kotlincoroutineexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.seyeong.kotlincoroutineexample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // binding.run { } 메서드로 클릭리스너를 감싸면 반복되는 binding. 을 제거할 수 있다.

        binding.buttonDownload.setOnClickListener { // 버튼이 클릭되면 가장 먼저 코루틴이 실행된다.
            CoroutineScope(Dispatchers.Main).launch { // 코루틴 스코프를 디스패처.Main 매개변수로 launch 한다.
                binding.progress.visibility = View.VISIBLE // 버튼 클릭시 프로그래스바가 동작하도록 설정
                val url = binding.editUrl.text.toString() // 이미지 주소를 url 변수에 저장한다.

                val bitmap = withContext(Dispatchers.IO) {
                    loadImage(url)
                    // loadImage 함수로 url을 전송하면 인터넷을 통해 통신하기 때문에 백그라운드에서 처리해주어야 한다.
                    // 따라서 withContext(Dispatchers.IO)로 변환해준다.
                    // 그리고 해당 함수는 suspend 키워드로 선언했기 때문에 bitmap 변수에 저장되기 전까지는 다음 줄이 실행되지 않고 멈춰 있다.
                }

                Log.d("태그", "bitmap = " + bitmap)

                binding.imagePreview.setImageBitmap(bitmap) // 이미지 뷰에 bitmap을 입력하고
                binding.progress.visibility = View.GONE // VISIBLE 상태의 프로그래스바는 다시 GONE으로 바꿔서 화면에서 감춘다.
            }
        }
    }
}

suspend fun loadImage(imageUrl: String) : Bitmap { // suspend 키워드를 사용하여 코루틴으로 함수 생성
    val url = URL(imageUrl) // URL 객체를 만들고
    val stream = url.openStream() // URL이 가지고 있는 openStream을
    return BitmapFactory.decodeStream(stream) // Bitmap 이미지로 저장한 후 반환한다.
}