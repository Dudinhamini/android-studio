package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import kotlin.math.log

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_login)
        getSupportActionBar()?.hide();

        val linkFormCadastro=findViewById<TextView>(R.id.text_tela_cadastro)
        linkFormCadastro.setOnClickListener{
            val telaCadastro = Intent(this, FormCadastro::class.java)
            startActivity(telaCadastro)
            Log.d("FormCadastro", "onCreate iniciado")
        }

        val linkTelaPerfil=findViewById<Button>(R.id.bt_entada)
        linkTelaPerfil.setOnClickListener{
            val telaPerfil = Intent(this, Tela_Perfil::class.java)
            startActivity(telaPerfil)
        }
    }
}