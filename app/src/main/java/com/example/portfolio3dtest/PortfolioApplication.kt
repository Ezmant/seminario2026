package com.example.portfolio3dtest

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.example.portfolio3dtest.data.local.LocalDatabaseManager

class PortfolioApplication : Application(), ImageLoaderFactory {

    lateinit var localDatabase: LocalDatabaseManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        localDatabase = LocalDatabaseManager(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(512L * 1024L * 1024L)
                    .build()
            }
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    companion object {
        lateinit var instance: PortfolioApplication
            private set
    }
}
