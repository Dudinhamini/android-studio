package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormCadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro)
        getSupportActionBar()?.hide();

        val linkTelaLogin=findViewById<Button>(R.id.bt_cadastro)
        linkTelaLogin.setOnClickListener{
            val telaLogin = Intent(this, FormLogin::class.java)
            startActivity(telaLogin)
        }
    }
}