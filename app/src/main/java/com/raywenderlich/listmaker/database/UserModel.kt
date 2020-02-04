package com.example.sqlitecrudkotlin

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

@SuppressLint("ParcelCreator")
class UserModel() : Parcelable {

    var name: String? = null
    var hobby: String? = null
    var id: Int = 0

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        hobby = parcel.readString()
        id = parcel.readInt()
    }

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getHobbys(): String {
        return hobby.toString()
    }

    fun setHobbys(hobby: String) {
        this.hobby = hobby
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(hobby)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

}