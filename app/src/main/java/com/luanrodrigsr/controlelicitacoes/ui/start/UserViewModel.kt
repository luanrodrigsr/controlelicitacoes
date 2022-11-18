package com.luanrodrigsr.controlelicitacoes.ui.start

import androidx.lifecycle.*
import com.luanrodrigsr.controlelicitacoes.data.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Classe que representa a ViewModel do usuário operador do aplicativo.
 */
class UserViewModel(
    private val database: Realm
) : ViewModel() {
    private var _userState = MutableLiveData<UserState>().apply {
        value = UserState.LOGGED_OUT
    }

    val userState: LiveData<UserState> = _userState

    /**
     * Entrar no aplicativo com as credenciais do usuário.
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _userState.value = UserState.LOADING
            // *******************************
            // Aguardar 1 segundo para simular a latência na conexão de internet.
            // *******************************
            delay(1000L)

            // *****************************************
            // ESTA PARTE DO CÓDIGO SERÁ ALTERADA EM CONFORMIDADE COM A POLÍTICA E REGRAS
            // DE SEGURANÇA DA EMPRESA.
            // AS CREDENCIAIS UTILIZADAS SÃO APENAS PARA SIMULAÇÃO DE TESTES.
            // *****************************************
            if (username == "admin" && password == "teste") {
                // Adicionar os dados do usuário logado no banco de dados.
                database.write {
                    // *****************************************
                    // IMPORTANTE: Por questões de segurança as senhas sempre devem ser salvas
                    // em formato criptografado para que não haja vazamento de informações sigilosas,
                    // por se tratar de um projeto de simulação do uso da aplicação a mesma foi
                    // armazenada de forma pura no banco de dados do Realm.
                    // *****************************************
                    copyToRealm(User().apply {
                        this.username = "admin"
                        this.password = "teste"
                    })
                }

                // Alterar o estado do usuário para "conectado".
                _userState.value = UserState.LOGGED_IN
            } else {
                // Alterar o estado do usuário para "inválido".
                _userState.value = UserState.INVALID_USER
            }
        }
    }

    // Verificar o estado do usuário.
    fun verifyUserState() {
        viewModelScope.launch {
            // Buscar o usuário no banco de dados.
            val user = database.query<User>().first().find()

            // Se o usuário existir significa que o mesmo está conectado.
            if (user != null) {
                // Alterar o estado do usuário para "conectado".
                _userState.value = UserState.LOGGED_IN
            } else {
                // Alterar o estado do usuário para "desconectado".
                _userState.value = UserState.LOGGED_OUT
            }
        }
    }
}

/**
 * Classe que representa os possíveis estados de um usuário.
 */
enum class UserState {
    LOADING,
    LOGGED_IN,
    LOGGED_OUT,
    INVALID_USER
}

class UserViewModelFactory(private val database: Realm) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(database) as T
        } else {
            throw IllegalArgumentException("Classe de ViewModel desconhecida.")
        }
    }

}