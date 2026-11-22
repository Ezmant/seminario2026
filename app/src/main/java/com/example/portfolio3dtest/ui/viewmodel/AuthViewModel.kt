package com.example.portfolio3dtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.portfolio3dtest.data.repository.AuthRepository
import com.example.portfolio3dtest.data.repository.AuthRepositoryImpl
import com.example.portfolio3dtest.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _authState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val authState: StateFlow<UiState<Boolean>> = _authState.asStateFlow()

    fun login(email: String, pass: String): Boolean {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = UiState.Error("Todos los campos son obligatorios")
            return false
        }
        _authState.value = UiState.Loading
        val success = authRepository.login(email, pass)
        if (success) {
            _authState.value = UiState.Success(true)
        } else {
            _authState.value = UiState.Error("Credenciales inválidas")
        }
        return success
    }

    fun register(name: String, email: String, specialty: String, pass: String): Boolean {
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _authState.value = UiState.Error("Por favor completa todos los campos")
            return false
        }
        _authState.value = UiState.Loading
        val success = authRepository.register(name, email, specialty)
        if (success) {
            _authState.value = UiState.Success(true)
        } else {
            _authState.value = UiState.Error("Error al registrar cuenta")
        }
        return success
    }

    fun resetState() {
        _authState.value = UiState.Idle
    }
}
