package com.example.a20mis0158

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotoViewModel(private val photoDao: PhotoDao) : ViewModel() {

    private val _photos = MutableLiveData<List<PhotoEntity>>()
    val photos: LiveData<List<PhotoEntity>> get() = _photos

    fun getPhotosByAlbumId(albumId: Long) {
        viewModelScope.launch {
            _photos.value = photoDao.getPhotosByAlbumId(albumId)
        }
    }

    fun addPhoto(photo: PhotoEntity) {
        viewModelScope.launch {
            photoDao.insert(photo)
            // Refresh the list after insertion
            getPhotosByAlbumId(photo.albumId)
        }
    }

    fun deletePhoto(photoId: Long, albumId: Long) {
        viewModelScope.launch {
            photoDao.deletePhotoById(photoId)
            // Refresh the list after deletion
            getPhotosByAlbumId(albumId)
        }
    }
}
