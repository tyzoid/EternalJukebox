package org.abimon.eternalJukebox.data.audio

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import com.github.kittinunf.result.Result

class DownloaderImpl private constructor() : Downloader() {

    @Throws(IOException::class, ReCaptchaException::class)
    override fun execute(request: Request): Response {
        val httpMethod = request.httpMethod()
        val url = request.url()
        val headers = request.headers()
        val dataToSend = request.dataToSend()

        var fuelRequest = Fuel.request(Method.valueOf(httpMethod.uppercase()), url)
            .timeoutRead(TimeUnit.SECONDS.toMillis(30).toInt())
            .header("User-Agent" to USER_AGENT)
            .header(headers)
        if (dataToSend != null) {
            fuelRequest = fuelRequest.body(dataToSend)
        }

        val response = fuelRequest.responseString()

        if (response.second.statusCode == 429) {
            throw ReCaptchaException("reCaptcha Challenge requested", url)
        }

        var responseBodyToReturn: String? = null

        if (response.third is Result.Success) {
            responseBodyToReturn = response.third.get()
        }

        val latestUrl: String = response.first.url.toString()
        return Response(
            response.second.statusCode,
            response.second.responseMessage,
            response.second.headers.map { it.key to it.value.toList() }.toMap(),
            responseBodyToReturn, latestUrl
        )
    }

    companion object {
        const val USER_AGENT: String = "Mozilla/5.0 (Windows NT 10.0; rv:91.0) Gecko/20100101 Firefox/91.0"

        private var instance: DownloaderImpl? = null

        /**
         * It's recommended to call exactly once in the entire lifetime of the application.
         *
         * @param builder if null, default builder will be used
         * @return a new instance of [DownloaderImpl]
         */
        fun init(): DownloaderImpl? {
            instance = DownloaderImpl()
            return instance
        }
    }
}
