package com.example.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPerfil : AppCompatActivity() {

    private lateinit var emailUser: EditText
    private lateinit var usuarioUser: EditText
    private lateinit var db: FirebaseFirestore
    private lateinit var btnSair: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_perfil)
        getSupportActionBar()?.hide()

        // Inicialize o Firestore ANTES de qualquer outra coisa
        db = FirebaseFirestore.getInstance()

        IniciarComponentes()

        btnSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@TelaPerfil, FormLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        userEmail?.let {
            emailUser.setText(it)
            buscarNomeDoEmail(it)
        }

    }

    private fun buscarNomeDoEmail(email: String){
        val usuariosRef = db.collection("Usuarios")
        val query = usuariosRef.whereEqualTo("email", email)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documento = querySnapshot.documents[0]
                    val nome = documento.getString("nome")
                    if (nome != null) {
                        usuarioUser.setText(nome)
                    } else {
                        println("Nome não encontrado para o email $email")
                    }
                } else {
                    println("Nenhum documento encontrado para o email $email")
                }
            }
            .addOnFailureListener { e ->
                println("Erro ao buscar documento: ${e.message}")
            }
    }

    private fun IniciarComponentes(){
        emailUser = findViewById(R.id.textEmailUser)
        usuarioUser = findViewById(R.id.textNomeUser)
        btnSair = findViewById(R.id.bt_sair)
    }
}