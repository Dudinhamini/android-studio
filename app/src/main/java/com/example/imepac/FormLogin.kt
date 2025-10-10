package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imepac.ui.theme.TelaPrincipal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class FormLogin : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnEntrada: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_login)
        getSupportActionBar()?.hide();
        iniciarComponentes();

        val linkFormCadastro = findViewById<TextView>(R.id.text_tela_cadastro)
        linkFormCadastro.setOnClickListener {
            val telaCadastro = Intent(this, FormCadastro::class.java)
            startActivity(telaCadastro)
        }

        val linkTelaPerfil = findViewById<Button>(R.id.bt_entrada)
        linkTelaPerfil.setOnClickListener {
            val telaPerfil = Intent(this, Tela_Perfil::class.java)
            startActivity(telaPerfil)
        }

        btnEntrada.setOnClickListener {
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()
            if (email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                Snackbar.make(btnEntrada, mensagemErro, Snackbar.LENGTH_LONG).show()
            } else {
                AutenticarUsuario()
            }
        }
    }

    fun AutenticarUsuario() {
        val email = editEmail.text.toString()
        val senha = editSenha.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progressBar.visibility = View.GONE

                val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this@FormLogin, TelaPrincipal::class.java)
                startActivity(intent)
                finish()
            } else {
                val mensageErro = task.exception?.message
                Snackbar.make(findViewById(android.R.id.content), "erro ao autenticar...", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    fun iniciarComponentes(){
        progressBar = findViewById(R.id.progressbar)
        editEmail = findViewById(R.id.edit_email_login)
        editSenha = findViewById(R.id.edit_senha_login)
        btnEntrada = findViewById(R.id.bt_entrada)
    }
}