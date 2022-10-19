package com.javiermendez.tp2_usuarios

import android.Manifest
import android.content.ClipDescription
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.bumptech.glide.Glide

class UserDetalle : AppCompatActivity() {
    private lateinit var lanzador: ActivityResultLauncher<String>
    private lateinit var telText: TextView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var addressText: TextView
    private lateinit var imageView: ImageView
    private lateinit var smsBtn: Button
    private lateinit var phoneBtn: Button
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var address: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detalle)
        var intent = getIntent()
        telText = findViewById(R.id.detail_phone)
        nameText = findViewById(R.id.detail_name)
        emailText = findViewById(R.id.detail_email)
        imageView = findViewById(R.id.detail_image)
        smsBtn = findViewById(R.id.btn_sms)
        phoneBtn = findViewById(R.id.btn_call)
        addressText = findViewById(R.id.detail_address)

        name = intent.getStringExtra("name")!!
        email = intent.getStringExtra("email")!!
        phone = intent.getStringExtra("phone")!!
        address = intent.getStringExtra("address")!!

        var image = intent.getStringExtra("image")

        Glide.with(this).load(image).circleCrop().into(imageView)
        nameText.text = name
        emailText.text = email
        telText.text = phone
        addressText.text = address
        setListeners()


        PermissionChecker.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
    }

    private fun setListeners() {
        smsBtn.setOnClickListener {
            sendMessage("Hola ${name}")
        }
        phoneBtn.setOnClickListener {
            callUser()
        }
        telText.setOnClickListener {
            callUser()
        }
        emailText.setOnClickListener {
            sendEmail()
        }
        addressText.setOnClickListener {
            openMap()
        }
    }

    private fun openMap() {
        val uri = Uri.parse("geo:0,0?q=${address}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(mapIntent)
    }

    private fun sendMessage(mensaje: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)
        intent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.MATCH_ALL
        } else {
            0
        }

        val resolveInfos = packageManager.queryIntentActivities(intent, flag)
        if (resolveInfos.size == 0) {
            Toast.makeText(this, "No tiene apps para mandar el mensaje", Toast.LENGTH_LONG).show()
        } else {
            startActivity(intent)
        }
    }

    private fun callUser() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${telText.text}")
        startActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")

        intent.putExtra(Intent.EXTRA_EMAIL, email)
        startActivity(intent);
    }
}