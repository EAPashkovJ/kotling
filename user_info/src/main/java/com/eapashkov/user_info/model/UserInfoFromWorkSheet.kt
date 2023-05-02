package com.eapashkov.user_info.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.xml.bind.annotation.XmlRootElement


@Entity
@XmlRootElement(name = "UserInfoFromWorkSheet")
@Table(name = "user_info")
data class UserInfoFromWorkSheet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var city: String? = null,
    var age: Int? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfoFromWorkSheet

        if (id != other.id) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (city != other.city) return false
        return age == other.age
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstname?.hashCode() ?: 0)
        result = 31 * result + (lastname?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + age!!
        return result
    }
}
