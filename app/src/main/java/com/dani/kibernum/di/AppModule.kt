package com.dani.kibernum.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.dani.kibernum.data.db.AppDao
import com.dani.kibernum.data.db.AppDatabase
import com.dani.kibernum.data.db.ContactsDao
import com.dani.kibernum.data.db.FavDao
import com.dani.kibernum.data.repository.FavoriteRepository
import com.dani.kibernum.data.repository.HomeRepository
import com.dani.kibernum.data.repository.LoginRepository
import com.dani.kibernum.data.repository.source.MyPreference
import com.dani.kibernum.data.repository.source.RemoteApiSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRemoteApiSource(retrofit: Retrofit): RemoteApiSource =
        retrofit.create((RemoteApiSource::class.java))

    @Singleton
    @Provides
    fun provideRetrofit(gson : Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3100/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Singleton
    @Provides
    fun provideMyPreference(@ApplicationContext appContext: Context): MyPreference =
        MyPreference(appContext)

    @Provides
    @Singleton
    fun getAppDatabase(context : Application) : AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: AppDatabase): AppDao = appDatabase.getAppDao()

    @Provides
    fun getContactDao(appDatabase: AppDatabase): ContactsDao = appDatabase.getContactDao()

    @Provides
    @Singleton
    fun getFavDao(appDatabase: AppDatabase): FavDao = appDatabase.getFavDao()

    @Provides
    fun provideLoginRepository(
        remoteApiSource: RemoteApiSource,
        myPreference: MyPreference
    ): LoginRepository =
        LoginRepository(remoteApiSource, myPreference)

    @Provides
    fun provideHomeRepository(appDao: AppDao, contactsDao: ContactsDao, favDao: FavDao, remoteApiSource: RemoteApiSource,
                              myPreference: MyPreference): HomeRepository =
        HomeRepository(remoteApiSource, appDao, contactsDao, favDao, myPreference)


    @Provides
    fun provideFavoriteRepository(remoteApiSource: RemoteApiSource,
        sharedPreferences: MyPreference, favDao: FavDao, contactsDao: ContactsDao):
            FavoriteRepository =
            FavoriteRepository(remoteApiSource, sharedPreferences, favDao, contactsDao)

}