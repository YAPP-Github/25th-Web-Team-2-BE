package com.dobby.backend.infrastructure.database.entity.enum.areaInfo

import java.security.InvalidParameterException

enum class Region(val displayName: String) {
    SEOUL("SEOUL"),
    GYEONGGI("GYEONGGI"),
    INCHEON("INCHEON"),
    GANGWON("GANGWON"),
    DAEJEON("DAEJEON"),
    SEJONG("SEJONG"),
    CHUNGNAM("CHUNGNAM"),
    CHUNGBUK("CHUNGBUK"),
    BUSAN("BUSAN"),
    ULSAN("ULSAN"),
    GYEONGNAM("GYEONGNAM"),
    GYEONGBUK("GYEONGBUK"),
    DAEGU("DAEGU"),
    GWANGJU("GWANGJU"),
    JEONNAM("JEONNAM"),
    JEONBUK("JEONBUK"),
    JEJU("JEJU");

    fun getAreas(): List<Area> {
        return Area.findByRegion(this)
    }

    companion object {
        private val displayNameMap = values().associateBy(Region::displayName)

        fun fromDisplayName(typeKey: String): Region {
            return displayNameMap[typeKey]
                ?: throw InvalidParameterException()
        }
    }
}
