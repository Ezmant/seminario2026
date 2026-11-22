package com.example.portfolio3dtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio3dtest.data.repository.ProjectRepository
import com.example.portfolio3dtest.data.repository.ProjectRepositoryImpl
import com.example.portfolio3dtest.model.ProjectItem
import com.example.portfolio3dtest.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProjectRepository = ProjectRepositoryImpl()
) : ViewModel() {

    private val _projectUiState = MutableStateFlow<UiState<ProjectItem>>(UiState.Loading)
    val projectUiState: StateFlow<UiState<ProjectItem>> = _projectUiState.asStateFlow()

    fun loadProject(projectId: String) {
        viewModelScope.launch {
            _projectUiState.value = UiState.Loading
            repository.getProjectById(projectId).collect { project ->
                if (project != null) {
                    _projectUiState.value = UiState.Success(project)
                } else {
                    _projectUiState.value = UiState.Error("Proyecto no encontrado")
                }
            }
        }
    }
}
