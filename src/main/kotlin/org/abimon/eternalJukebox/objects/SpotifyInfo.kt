package org.abimon.eternalJukebox.objects

data class SpotifyAudioBar(
        val start: Double,
        val duration: Double,
        val confidence: Double
)

data class SpotifyAudioBeat(
        val start: Double,
        val duration: Double,
        val confidence: Double
)

data class SpotifyAudioTatum(
        val start: Double,
        val duration: Double,
        val confidence: Double
)

@Suppress("PropertyName")
data class SpotifyAudioSection(
        val start: Double,
        val duration: Double,
        val confidence: Double,
        val loudness: Double,
        val tempo: Double,
        val tempo_confidence: Double,
        val key: Int,
        val key_confidence: Double,
        val mode: Int,
        val mode_confidence: Double,
        val time_signature: Int,
        val time_signature_confidence: Double
)

@Suppress("ArrayInDataClass", "PropertyName")
data class SpotifyAudioSegment(
        val start: Double,
        var duration: Double,
        val confidence: Double,
        val loudness_start: Int,
        val loudness_max_time: Int,
        val loudness_max: Int,
        val pitches: DoubleArray,
        val timbre: DoubleArray
)
