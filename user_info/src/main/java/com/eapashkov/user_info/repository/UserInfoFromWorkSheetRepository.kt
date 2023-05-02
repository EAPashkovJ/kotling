package com.eapashkov.user_info.repository

import com.eapashkov.user_info.model.UserInfoFromWorkSheet
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoFromWorkSheetRepository : JpaRepository<UserInfoFromWorkSheet?, Long?>
