package com.example.data.datasource

import com.example.data.model.Group
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList

class MongoGroupDataSource(db: MongoDatabase) : GroupDataSource {
    private val groups = db.getCollection<Group>("groups")

    override suspend fun createGroup(group: Group): Boolean {
        return groups.insertOne(group).wasAcknowledged()
    }

    override suspend fun getGroups(): List<Group> {
        return groups.find().toList()
    }

    override suspend fun deleteGroup(groupId: String): Boolean {
        val deleteResult = groups.deleteOne(eq("id",groupId))
        return deleteResult.deletedCount > 0
    }
}