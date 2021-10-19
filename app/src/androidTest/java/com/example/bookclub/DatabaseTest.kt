package com.example.bookclub

import android.content.Context
import androidx.room.Room
import com.example.bookclub.database.Database
import com.example.bookclub.dependency_injection.DataModule
import com.example.bookclub.models.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DataModule::class)
@HiltAndroidTest
class DatabaseTest {

    @get: Rule val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var database: Database

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun testInsertUser(){
        val testNewUser = User("123", "bob")
        database.userDao.insertUser(testNewUser)
        val getUser = database.userDao.findUser(testNewUser.userId)
        assertEquals(getUser, testNewUser)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DataTestModule {

        @Provides
        fun provideDatabase(@ApplicationContext context: Context): Database {
            return Room.databaseBuilder(context, Database::class.java, "db").build()
        }
    }
}