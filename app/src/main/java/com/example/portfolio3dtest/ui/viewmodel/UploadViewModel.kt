package com.example.portfolio3dtest.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.portfolio3dtest.data.repository.ProjectRepository
import com.example.portfolio3dtest.data.repository.ProjectRepositoryImpl
import com.example.portfolio3dtest.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UploadViewModel(
    private val repository: ProjectRepository = ProjectRepositoryImpl()
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val uploadState: StateFlow<UiState<Boolean>> = _uploadState.asStateFlow()

    fun publishProject(
        title: String,
        category: String,
        softwareList: List<String>,
        prompt: String,
        imageUri: Uri?
    ): Boolean {
        if (title.isBlank()) {
            _uploadState.value = UiState.Error("Por favor ingresa un título")
            return false
        }
        _uploadState.value = UiState.Loading
        repository.addProject(
            title = title,
            category = category,
            softwareList = softwareList,
            prompt = prompt,
            imageUri = imageUri?.toString()
        )
        _uploadState.value = UiState.Success(true)
        return true
    }

    fun resetState() {
        _uploadState.value = UiState.Idle
    }
}
