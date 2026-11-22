package com.example.portfolio3dtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio3dtest.data.repository.AuthRepository
import com.example.portfolio3dtest.data.repository.AuthRepositoryImpl
import com.example.portfolio3dtest.data.repository.ProjectRepository
import com.example.portfolio3dtest.data.repository.ProjectRepositoryImpl
import com.example.portfolio3dtest.model.ProjectItem
import com.example.portfolio3dtest.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) : ViewModel() {

    val user: StateFlow<User> = authRepository.getCurrentUser().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = User()
    )

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    val tabProjects: StateFlow<List<ProjectItem>> = _selectedTabIndex.flatMapLatest { tab ->
        when (tab) {
            0 -> projectRepository.getProjectsFlow()
            1 -> projectRepository.getDraftProjects()
            else -> projectRepository.getFavoriteProjects()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }
}
