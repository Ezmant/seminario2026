package com.example.portfolio3dtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio3dtest.data.repository.ProjectRepository
import com.example.portfolio3dtest.data.repository.ProjectRepositoryImpl
import com.example.portfolio3dtest.model.ProjectItem
import com.example.portfolio3dtest.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class GalleryViewModel(
    private val repository: ProjectRepository = ProjectRepositoryImpl()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val projectsUiState: StateFlow<UiState<List<ProjectItem>>> = combine(
        repository.getProjectsFlow(),
        _searchQuery,
        _selectedCategory
    ) { projects, query, category ->
        val filtered = projects.filter { item ->
            val matchesCategory = (category == "Todos" || item.category == category)
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
        UiState.Success(filtered)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}
