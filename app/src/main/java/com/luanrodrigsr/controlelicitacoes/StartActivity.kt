package com.luanrodrigsr.controlelicitacoes

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.luanrodrigsr.controlelicitacoes.databinding.ActivityStartBinding
import com.luanrodrigsr.controlelicitacoes.ui.start.UserState
import com.luanrodrigsr.controlelicitacoes.ui.start.UserViewModel
import com.luanrodrigsr.controlelicitacoes.ui.start.UserViewModelFactory

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            database = (application as MainApplication).database
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userState.observe(this) { state ->
            updateScreen(state)
        }

        binding.apply {
            buttonLogin.setOnClickListener {
                login(
                    username = binding.edittextUsername.text.toString(),
                    password = binding.edittextPassword.text.toString()
                )
            }
        }

        // Verificar o estado do usuário.
        verifyUserState()
    }

    // Verificar o estado de conexão do usuário.
    private fun verifyUserState() {
        viewModel.verifyUserState()
    }

    private fun updateScreen(state: UserState) {
        when (state) {
            UserState.LOADING -> {
                binding.progressCircular.visibility = VISIBLE
                binding.buttonLogin.visibility = GONE
            }
            UserState.LOGGED_IN -> {
                binding.progressCircular.visibility = GONE
                binding.buttonLogin.visibility = GONE

                // Iniciar a atividade principal quando o usuário estiver logado.
                startMainActivity()
            }
            UserState.INVALID_USER -> {
                binding.progressCircular.visibility = GONE
                binding.buttonLogin.visibility = VISIBLE

                // Mostrar mensagem de erro ao usuário devido a credenciais inválidas.
                Toast.makeText(
                    this, getString(R.string.error_invalid_user_password), LENGTH_SHORT
                ).show()
            }
            else -> {
                binding.progressCircular.visibility = GONE
                binding.buttonLogin.visibility = VISIBLE
            }
        }
    }

    private fun login(username: String, password: String) {
        viewModel.login(username, password)
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}