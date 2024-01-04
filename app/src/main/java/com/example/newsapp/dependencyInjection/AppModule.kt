package com.example.newsapp.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.Dao
import com.example.newsapp.data.local.NewsDataBase
import com.example.newsapp.data.local.TypeConverter
import com.example.newsapp.data.manager.LocalUserManagerImplementation
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.repository.NewsRepositoryImplementation
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecases.appEntry.AppEntryUseCases
import com.example.newsapp.domain.usecases.appEntry.ReadAppEntry
import com.example.newsapp.domain.usecases.appEntry.SaveAppEntry
import com.example.newsapp.domain.usecases.news.DeleteArticle
import com.example.newsapp.domain.usecases.news.GetNews
import com.example.newsapp.domain.usecases.news.UpsertArticle
import com.example.newsapp.domain.usecases.news.NewsSearch
import com.example.newsapp.domain.usecases.news.NewsUseCases
import com.example.newsapp.domain.usecases.news.SelectArticle
import com.example.newsapp.domain.usecases.news.SelectArticles
import com.example.newsapp.utility.Constants.BASE_URL
import com.example.newsapp.utility.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun localUserManagerProvider(
        application: Application
    ): LocalUserManager = LocalUserManagerImplementation(application)

    @Provides
    @Singleton
    fun AppEntryUseCasesProvider(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun ApiProvider(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun NewsRepositoryProvider(
        newsApi: NewsApi,
        dao: Dao
    ): NewsRepository = NewsRepositoryImplementation(newsApi, dao)

    @Provides
    @Singleton
    fun NewsUseCasesProvider(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            newsSearch = NewsSearch(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun NewsDataBaseProvider(
        application: Application
    ): NewsDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = DATABASE_NAME
        ).addTypeConverter(TypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun DaoProvider(
        newsDataBase: NewsDataBase
    ): Dao = newsDataBase.newsDao
}