package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormCadastro : AppCompatActivity() {
    private lateinit var edit_nome: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var btn_Cadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar?.hide()

        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btn_Cadastrar = findViewById(R.id.bt_cadastro)

        btn_Cadastrar.setOnClickListener { view ->
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente."
                Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(view, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(view: View, email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val mensagemOK = "Cadastro realizado com sucesso"
                    Snackbar.make(view, mensagemOK, Snackbar.LENGTH_LONG).show()

                    // Volta para o login após 2 segundos
                    view.postDelayed({
                        val telaLogin = Intent(this, FormLogin::class.java)
                        startActivity(telaLogin)
                        finish()
                    }, 2000)

                } else {
                    val mensagemErro = task.exception?.message ?: "Erro ao cadastrar"
                    Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show()
                }
            }
    }
}