package com.example.portfolio3dtest.data.remote

import com.example.portfolio3dtest.model.ProjectItem
import com.example.portfolio3dtest.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseService(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    val currentUserId: String?
        get() = auth.currentUser?.uid

    suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, specialty: String, pass: String): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("No UID returned"))

            val newUser = User(
                id = uid,
                name = name,
                email = email,
                specialty = specialty
            )
            firestore.collection("users").document(uid).set(newUser).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getProjectsRealtime(): Flow<List<ProjectItem>> = callbackFlow {
        val listener = firestore.collection("projects")
            .orderBy("id", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val projects = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ProjectItem::class.java)
                    }
                    trySend(projects)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun addProject(project: ProjectItem): Result<Boolean> {
        return try {
            firestore.collection("projects").document(project.id).set(project).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): Result<User?> {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            Result.success(doc.toObject(User::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
