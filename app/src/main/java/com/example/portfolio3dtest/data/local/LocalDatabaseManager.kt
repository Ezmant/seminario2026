package com.example.portfolio3dtest.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.portfolio3dtest.model.MockData
import com.example.portfolio3dtest.model.ProjectItem
import com.example.portfolio3dtest.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

class LocalDatabaseManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("portfolio3d_local_db", Context.MODE_PRIVATE)

    private val _projectsFlow = MutableStateFlow<List<ProjectItem>>(emptyList())
    val projectsFlow: Flow<List<ProjectItem>> = _projectsFlow.asStateFlow()

    private val _userFlow = MutableStateFlow(User())
    val userFlow: Flow<User> = _userFlow.asStateFlow()

    private val _favoritesFlow = MutableStateFlow<Set<String>>(setOf("1", "3"))
    private val _draftsFlow = MutableStateFlow<List<ProjectItem>>(emptyList())

    init {
        loadUser()
        loadProjects()
        loadDrafts()
    }

    // --- USUARIO ---
    private fun loadUser() {
        val name = prefs.getString("user_name", "Alex Rivera") ?: "Alex Rivera"
        val handle = prefs.getString("user_handle", "@arivera_3d") ?: "@arivera_3d"
        val email = prefs.getString("user_email", "alex.rivera@example.com") ?: "alex.rivera@example.com"
        val specialty = prefs.getString("user_specialty", "Modelador 3D Sr.") ?: "Modelador 3D Sr."
        _userFlow.value = User(
            name = name,
            handle = handle,
            email = email,
            specialty = specialty
        )
    }

    fun saveUser(name: String, email: String, specialty: String) {
        prefs.edit()
            .putString("user_name", name)
            .putString("user_email", email)
            .putString("user_specialty", specialty)
            .apply()
        _userFlow.value = _userFlow.value.copy(
            name = name,
            email = email,
            specialty = specialty
        )
    }

    // --- PROYECTOS LOCALES ---
    private fun loadProjects() {
        val jsonStr = prefs.getString("local_projects", null)
        if (jsonStr == null) {
            // Primera ejecución: Cargar datos iniciales
            _projectsFlow.value = MockData.sampleProjects
            saveProjectsToDisk(MockData.sampleProjects)
        } else {
            try {
                val list = parseProjectsJson(jsonStr)
                _projectsFlow.value = if (list.isEmpty()) MockData.sampleProjects else list
            } catch (e: Exception) {
                _projectsFlow.value = MockData.sampleProjects
            }
        }
    }

    private fun saveProjectsToDisk(list: List<ProjectItem>) {
        val jsonArray = JSONArray()
        list.forEach { item ->
            val obj = JSONObject().apply {
                put("id", item.id)
                put("title", item.title)
                put("category", item.category)
                put("imageUrl", item.imageUrl)
                put("authorName", item.authorName)
                put("authorAvatarUrl", item.authorAvatarUrl)
                put("description", item.description)
                put("prompt", item.prompt)
                val swArray = JSONArray()
                item.softwareList.forEach { swArray.put(it) }
                put("softwareList", swArray)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("local_projects", jsonArray.toString()).apply()
    }

    fun addProject(
        title: String,
        category: String,
        softwareList: List<String>,
        prompt: String,
        imageUri: String?
    ) {
        val newId = System.currentTimeMillis().toString()
        val defaultImage = imageUri ?: "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600"
        val newProject = ProjectItem(
            id = newId,
            title = title,
            category = category,
            imageUrl = defaultImage,
            authorName = _userFlow.value.name,
            authorAvatarUrl = _userFlow.value.avatarUrl,
            softwareList = if (softwareList.isEmpty()) listOf("Blender") else softwareList,
            prompt = if (prompt.isBlank()) "Isometric render, detailed 3D, octane engine" else prompt
        )
        val updated = listOf(newProject) + _projectsFlow.value
        _projectsFlow.value = updated
        saveProjectsToDisk(updated)
    }

    // --- BORRADORES ---
    private fun loadDrafts() {
        val initialDrafts = listOf(
            ProjectItem(
                id = "draft_1",
                title = "Borrador: Escena Botánica",
                category = "3D Renders",
                imageUrl = "https://images.unsplash.com/photo-1518531933037-91b2f5f229cc?w=600",
                description = "En proceso de iluminación y materiales volumétricos."
            )
        )
        _draftsFlow.value = initialDrafts
    }

    fun getFavoriteProjects(): Flow<List<ProjectItem>> {
        return projectsFlow.map { list ->
            list.filter { _favoritesFlow.value.contains(it.id) }
        }
    }

    fun getDraftProjects(): Flow<List<ProjectItem>> = _draftsFlow.asStateFlow()

    private fun parseProjectsJson(jsonStr: String): List<ProjectItem> {
        val list = mutableListOf<ProjectItem>()
        val jsonArray = JSONArray(jsonStr)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val swList = mutableListOf<String>()
            val swArray = obj.optJSONArray("softwareList")
            if (swArray != null) {
                for (j in 0 until swArray.length()) {
                    swList.add(swArray.getString(j))
                }
            }
            list.add(
                ProjectItem(
                    id = obj.getString("id"),
                    title = obj.getString("title"),
                    category = obj.getString("category"),
                    imageUrl = obj.getString("imageUrl"),
                    authorName = obj.optString("authorName", "Alex Rivera"),
                    authorAvatarUrl = obj.optString("authorAvatarUrl", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300"),
                    description = obj.optString("description", "Render 3D fotorrealista para portafolio."),
                    softwareList = if (swList.isEmpty()) listOf("Blender") else swList,
                    prompt = obj.optString("prompt", "Cyberpunk isometric studio room render 8k")
                )
            )
        }
        return list
    }
}
