package com.example.portfolio3dtest.model

data class User(
    val id: String = "user_1",
    val name: String = "Alex Rivera",
    val handle: String = "@arivera_3d",
    val email: String = "alex.rivera@example.com",
    val specialty: String = "Modelador 3D Sr.",
    val levelBadge: String = "Nivel Intermedio",
    val avatarUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300",
    val coverUrl: String = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1000",
    val bio: String = "Especialista en renders fotorrealistas de arquitectura y mobiliario. Experiencia en Blender, Octane y Unreal Engine 5."
)
