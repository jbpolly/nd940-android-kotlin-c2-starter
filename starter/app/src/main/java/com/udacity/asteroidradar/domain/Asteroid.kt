package com.udacity.asteroidradar.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Constants
import kotlinx.parcelize.Parcelize


@Entity(tableName = Constants.ASTEROID_TABLE)
@Parcelize
data class Asteroid(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Asteroid

        if (id != other.id) return false
        if (codename != other.codename) return false
        if (closeApproachDate != other.closeApproachDate) return false
        if (absoluteMagnitude != other.absoluteMagnitude) return false
        if (estimatedDiameter != other.estimatedDiameter) return false
        if (relativeVelocity != other.relativeVelocity) return false
        if (distanceFromEarth != other.distanceFromEarth) return false
        if (isPotentiallyHazardous != other.isPotentiallyHazardous) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + codename.hashCode()
        result = 31 * result + closeApproachDate.hashCode()
        result = 31 * result + absoluteMagnitude.hashCode()
        result = 31 * result + estimatedDiameter.hashCode()
        result = 31 * result + relativeVelocity.hashCode()
        result = 31 * result + distanceFromEarth.hashCode()
        result = 31 * result + isPotentiallyHazardous.hashCode()
        return result
    }
}