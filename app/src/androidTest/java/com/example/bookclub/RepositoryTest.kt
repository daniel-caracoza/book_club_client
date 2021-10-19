package com.example.bookclub

import com.example.bookclub.repository.Repository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RepositoryTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject lateinit var repository: Repository

    @Before
    fun init(){
        hiltRule.inject()
    }

    @Test
    fun someTest(){
    }
}