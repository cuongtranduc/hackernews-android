package com.cuongtd.hackernews.model.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Story(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name= "objectId") val objectID: String
)

@Dao
interface StoryDao {
    @Query("SELECT * FROM story ORDER BY uid DESC")
    fun getAll(): LiveData<List<Story>>

    @Query("SELECT * FROM story WHERE objectId=:objectId")
    fun getSingle(objectId: String): LiveData<Story>?

    @Insert
    fun insert(story: Story)

    @Delete
    fun delete(story: Story)
}

@Database(entities = arrayOf(Story::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}