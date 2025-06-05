package com.example.data.datasource

import com.example.data.model.Group

interface GroupDataSource {
    suspend fun createGroup(group: Group): Boolean
    suspend fun getGroups(): List<Group>
    suspend fun deleteGroup(groupId: String): Boolean
}