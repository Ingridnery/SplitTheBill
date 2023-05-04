package br.edu.scl.ifsp.ads.splitthebill.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Person (

    @PrimaryKey(autoGenerate = true) val id: Int?,
    @NonNull var name: String,
    @NonNull var value: Double,
    var items: String,
): Parcelable
