package com.app.androidassesment.util

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseApp : AppCompatActivity() {
    fun View.showView(isShow: Boolean){
        if(isShow){
            this.visibility=View.VISIBLE
        }else{
            this.visibility=View.GONE
        }
    }
    fun showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}