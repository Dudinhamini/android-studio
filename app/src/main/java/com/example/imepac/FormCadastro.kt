package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
                // CORREÇÃO 1: Passar os valores reais, não TODO()
                cadastrarUsuario(view, nome, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(view: View, nome: String, email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // CORREÇÃO 2: Passar o nome para salvar
                    salvarDadosUsuario(nome)

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

    private fun salvarDadosUsuario(nome: String) {
        val db = FirebaseFirestore.getInstance()
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email

        if (usuarioID != null && email != null) {
            val usuarios = hashMapOf(
                "nome" to nome,  // CORREÇÃO 3: era "none", agora é "nome"
                "email" to email,
                "uid" to usuarioID
            )

            // CORREÇÃO 4: Usar .document(usuarioID).set() em vez de .add()
            db.collection("Usuarios")
                .document(usuarioID)  // Usa o UID como ID do documento
                .set(usuarios)
                .addOnSuccessListener {
                    println("✓ Usuário salvo com sucesso! ID: $usuarioID")
                }
                .addOnFailureListener { e ->
                    println("❌ Erro ao salvar usuário: ${e.message}")
                }
        } else {
            println("❌ Erro: Usuário não autenticado")
        }
    }
}