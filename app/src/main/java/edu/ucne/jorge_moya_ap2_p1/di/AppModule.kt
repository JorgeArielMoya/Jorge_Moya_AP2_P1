package edu.ucne.jorge_moya_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jorge_moya_ap2_p1.data.local.dao.AmonestacionDao
import edu.ucne.jorge_moya_ap2_p1.data.local.db.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase (@ApplicationContext context: Context) : Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesAmonestacionDao (database: Database) : AmonestacionDao{
        return database.amonestacionDao()
    }
}