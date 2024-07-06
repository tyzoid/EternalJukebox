package org.abimon.eternalJukebox.data.database

import org.abimon.eternalJukebox.EternalJukebox
import org.abimon.eternalJukebox.objects.ClientInfo
import org.abimon.eternalJukebox.objects.JukeboxInfo

interface IDatabase {
    val databaseOptions
        get() = EternalJukebox.config.databaseOptions
    val databaseName
        get() = databaseOptions["databaseName"] ?: "eternal_jukebox"

    suspend fun provideAudioTrackOverride(id: String, clientInfo: ClientInfo?): String?

    suspend fun providePopularSongs(service: String, count: Int, clientInfo: ClientInfo?): List<JukeboxInfo>
    fun makeSongPopular(service: String, id: String, clientInfo: ClientInfo?)

    suspend fun provideShortURL(params: Array<String>, clientInfo: ClientInfo?): String
    suspend fun expandShortURL(id: String, clientInfo: ClientInfo?): Array<String>?

    suspend fun provideAudioLocation(id: String, clientInfo: ClientInfo?): String?
    suspend fun storeAudioLocation(id: String, location: String, clientInfo: ClientInfo?)
}
