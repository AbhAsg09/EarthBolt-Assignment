package com.example.a20mis0158

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos WHERE albumId = :albumId")
    fun getPhotosByAlbumId(albumId: Long): List<PhotoEntity>

    @Insert
    suspend fun insert(photo: PhotoEntity): Long

    @Query("DELETE FROM photos WHERE id = :photoId")
    suspend fun deletePhotoById(photoId: Long): Int
}
