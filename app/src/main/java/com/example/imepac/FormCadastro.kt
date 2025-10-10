package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FormCadastro : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar?.hide()

        // Ligando os elementos do XML com as variáveis
        editNome = findViewById(R.id.edit_nome)
        editEmail = findViewById(R.id.edit_email_login)
        editSenha = findViewById(R.id.edit_senha_login)
        btnCadastrar = findViewById(R.id.bt_cadastro)

        btnCadastrar.setOnClickListener { view ->
            val nome = editNome.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val senha = editSenha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente novamente"
                Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show()
            } else {
                cadastrarUsuario(
                    view,
                    nome = TODO(),
                    email = TODO(),
                    senha = TODO()
                )
            }
        }
    }

    private fun cadastrarUsuario(view: View, nome: String, email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    salvarDadosUsuario()

                    val mensagemOK = "Cadastro realizado com sucesso"
                    val snakbar = Snackbar.make(view, mensagemOK, Snackbar.LENGTH_LONG).show()

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

    fun salvarDadosUsuario() {
        val db = FirebaseFirestore.getInstance()
        val nome = editNome.text.toString().trim()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (usuarioID != null && email != null) {
            val usuarios = hashMapOf(
                "none" to nome,
                "email" to email,
                "uid" to usuarioID
            )
            db.collection("Usuarios")
                .add(usuarios)
                .addOnSuccessListener { documentReference ->// Add con sucesso
                    println("Docunento adicionado con ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Enrro ao adicioner documento: $e")
                }
        } else {// o usuério não está gutenticado
            println("Enrro na autenticação")
        }

    }

}