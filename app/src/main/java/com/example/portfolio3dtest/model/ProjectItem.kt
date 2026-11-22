package com.example.portfolio3dtest.model

data class ProjectItem(
    val id: String,
    val title: String,
    val category: String,
    val imageUrl: String,
    val authorName: String = "Alex Rivera",
    val authorAvatarUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300",
    val description: String = "Render 3D fotorrealista creado para portafolio profesional con detalles de iluminación global y texturas 4K.",
    val softwareList: List<String> = listOf("Blender", "Octane Render"),
    val prompt: String = "Cyberpunk isometric studio room, neon lighting, octane render 8k, detailed textures, highly realistic, ray tracing --ar 16:9"
)

object MockData {
    val sampleProjects = listOf(
        ProjectItem(
            id = "1",
            title = "Silla Eames Modern 3D",
            category = "Mobiliario",
            imageUrl = "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=600",
            authorName = "Alex Rivera",
            description = "Modelado 3D de alta precisión con texturas de madera y cuero natural.",
            softwareList = listOf("Blender", "Substance Painter"),
            prompt = "Minimalist Scandinavian chair render, studio soft light, 8k textures, photorealistic --v 5"
        ),
        ProjectItem(
            id = "2",
            title = "Concept Art Sci-Fi Station",
            category = "3D Renders",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600",
            authorName = "Alex Rivera",
            description = "Escena conceptual sci-fi futurista con luces de neón y atmósferas cinemáticas.",
            softwareList = listOf("Unreal Engine 5", "Maya"),
            prompt = "Futuristic corridor, volumetric fog, blue and magenta neon, unreal engine 5 render --ar 16:9"
        ),
        ProjectItem(
            id = "3",
            title = "Lámpara Minimalista Velvet",
            category = "Mobiliario",
            imageUrl = "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=600",
            authorName = "Alex Rivera",
            description = "Diseño de iluminación interior con acabados metálicos y luz cálida.",
            softwareList = listOf("Blender", "Cycles"),
            prompt = "Modern brass floor lamp in architectural interior, moody lighting, archviz render --v 6"
        ),
        ProjectItem(
            id = "4",
            title = "Cyber City Street 2077",
            category = "3D Renders",
            imageUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=600",
            authorName = "Alex Rivera",
            description = "Render urbano nocturno centrado en materiales reflectantes y lluvia.",
            softwareList = listOf("3ds Max", "V-Ray"),
            prompt = "Rainy cyberpunk street, glossy reflections, wet pavement, octane render, 35mm lens"
        ),
        ProjectItem(
            id = "5",
            title = "Sofá Modular Nordic",
            category = "Mobiliario",
            imageUrl = "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=600",
            authorName = "Alex Rivera",
            description = "Conjunto de muebles nórdicos para salas de estar modernas.",
            softwareList = listOf("Blender", "Marvelous Designer"),
            prompt = "Fabric texture couch render, soft daylight from big window, interior archviz --ar 4:3"
        ),
        ProjectItem(
            id = "6",
            title = "Planeta Alienígena Low-Poly",
            category = "3D Renders",
            imageUrl = "https://images.unsplash.com/photo-1634017839464-5c339ebe3cb4?w=600",
            authorName = "Alex Rivera",
            description = "Modelado estilo low-poly estilizado para videojuego indie.",
            softwareList = listOf("Cinema 4D", "Octane"),
            prompt = "Low poly fantasy island floating in space, gradient background, vibrant isometric 3d"
        )
    )
}
