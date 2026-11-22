package com.example.portfolio3dtest.data.repository

import com.example.portfolio3dtest.PortfolioApplication
import com.example.portfolio3dtest.data.local.LocalDatabaseManager
import com.example.portfolio3dtest.model.ProjectItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ProjectRepository {
    fun getProjectsFlow(): Flow<List<ProjectItem>>
    fun getProjectById(id: String): Flow<ProjectItem?>
    fun searchProjects(query: String, category: String): Flow<List<ProjectItem>>
    fun addProject(title: String, category: String, softwareList: List<String>, prompt: String, imageUri: String?)
    fun getFavoriteProjects(): Flow<List<ProjectItem>>
    fun getDraftProjects(): Flow<List<ProjectItem>>
}

class ProjectRepositoryImpl(
    private val localDb: LocalDatabaseManager = PortfolioApplication.instance.localDatabase
) : ProjectRepository {

    override fun getProjectsFlow(): Flow<List<ProjectItem>> = localDb.projectsFlow

    override fun getProjectById(id: String): Flow<ProjectItem?> {
        return localDb.projectsFlow.map { list -> list.find { it.id == id } }
    }

    override fun searchProjects(query: String, category: String): Flow<List<ProjectItem>> {
        return localDb.projectsFlow.map { list ->
            list.filter { item ->
                val matchesCategory = (category == "Todos" || item.category == category)
                val matchesQuery = query.isBlank() ||
                        item.title.contains(query, ignoreCase = true) ||
                        item.category.contains(query, ignoreCase = true)
                matchesCategory && matchesQuery
            }
        }
    }

    override fun addProject(
        title: String,
        category: String,
        softwareList: List<String>,
        prompt: String,
        imageUri: String?
    ) {
        localDb.addProject(
            title = title,
            category = category,
            softwareList = softwareList,
            prompt = prompt,
            imageUri = imageUri
        )
    }

    override fun getFavoriteProjects(): Flow<List<ProjectItem>> = localDb.getFavoriteProjects()

    override fun getDraftProjects(): Flow<List<ProjectItem>> = localDb.getDraftProjects()
}
