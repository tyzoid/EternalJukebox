package org.abimon.eternalJukebox

import kotlinx.coroutines.delay
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.UnknownHostException
import kotlin.random.Random

/**
 * Snowflake

 * @author Maxim Khodanovich
 * *
 * @version 21.01.13 17:16
 * *
 *
 *
 * *          id is composed of:
 * *          time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years)
 * *          configured machine id - 10 bits - gives us up to 1024 machines
 * *          sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms)
 */
class LocalisedSnowstorm(private val twepoch: Long) {

    //   id format  =>
    //   timestamp |datacenter | sequence
    //   41        |10         |  12
    private val sequenceBits = 12
    private val datacenterIdBits = 10

    private val datacenterIdShift = sequenceBits
    private val timestampLeftShift = sequenceBits + datacenterIdBits
    private val datacenterId: Long = getDatacenterId()
    private val sequenceMax = 4096

    @Volatile private var lastTimestamp = -1L
    @Volatile private var sequence = 0

    suspend fun generateLongId(): Long {
        var timestamp = System.currentTimeMillis()

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0
        }
        lastTimestamp = timestamp
        val id = timestamp - twepoch shl timestampLeftShift or
                (datacenterId shl datacenterIdShift) or
                sequence.toLong()
        return id
    }

    private suspend fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = System.currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            delay(lastTimestamp - timestamp - 1)
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }

    private fun getDatacenterId(): Long {
        try {
            val addr = NetworkInterface.getNetworkInterfaces()
                .asSequence()
                .flatMap { network -> network.inetAddresses.asSequence() }
                .firstOrNull(InetAddress::isSiteLocalAddress)

            val id: Int
            when (addr) {
                null -> id = Random.nextInt(0, 1023)
                else -> {
                    val addrData = addr.address

                    id = (addrData[addrData.size - 2].toInt() and 0x3 shl 8) or (addrData[addrData.size - 1].toInt() and 0xFF)
                }
            }

            println("Snowstorm Datacenter ID: $id")

            return id.toLong()
        } catch (e: SocketException) {
            e.printStackTrace()
            return 0
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            return 0
        }

    }

    suspend fun get(): Long {
        return generateLongId()
    }

    companion object WeatherMap {
        private val snowstorms = HashMap<Long, LocalisedSnowstorm>()

        fun getInstance(epoch: Long): LocalisedSnowstorm {
            if (!snowstorms.containsKey(epoch))
                snowstorms[epoch] = LocalisedSnowstorm(epoch)
            return snowstorms.getValue(epoch)
        }
    }
}
