package id.co.edtslib.data.source

import android.content.Context
import com.google.gson.Gson
import id.co.edtslib.data.source.local.TrackerLocalDataSource
import id.co.edtslib.data.source.remote.TrackerRemoteDataSource
import id.co.edtslib.domain.model.*
import id.co.edtslib.domain.repository.ITrackerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mypoin.indomaret.android.data.cache.tracker.*
import java.util.*

class TrackerRepository(
    private val remoteSource: TrackerRemoteDataSource,
    private val localSource: TrackerLocalDataSource,
//    private val customerLocalDataSource: CustomerLocalDataSource,
//    private val configurationLocalDataSource: ConfigurationLocalDataSource,
    val context: Context
) : ITrackerRepository {

    override fun sendTrackPage(screenName: String): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerPageViewCore.create(screenName)
        val trackerData = TrackerData(
            trackerCore,
            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer
        )

        val trackerDataList = TrackerList(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDataList)
        when (response.status) {
            Result.Status.SUCCESS -> emit(
                TrackerResponse(
                    Gson().toJson(trackerData),
                    response.data
                )
            )
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDataList)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {
            }
        }*/
    }

    override fun sendTrackClick(name: String): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerClickLinkCore.create(name)
        val trackerData = TrackerData(trackerCore,
            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDatas = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDatas)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDatas)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendTrackSubmission(
        screeName: String,
        status: Boolean,
        reason: String?
    ): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerSubmissionCore.create(screeName, status, reason)
        val trackerData = TrackerData(trackerCore,
            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDatas = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDatas)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDatas)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendPageResume(): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerActivityCore.createPageResume()
        val trackerData = TrackerData(trackerCore,
            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDataList = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDataList)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDataList)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendPageExit(): Flow<Boolean> = flow {
        /*val trackerCore = TrackerActivityCore.createPageExit()
        val trackerData = TrackerData(trackerCore
            , TrackerUser.create(localSource.sessionId, customerLocalDataSource)
            , localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDatas = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDatas)
        when(response.status) {
            Result.Status.SUCCESS -> {}
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDatas)
            }
            else -> {}
        }

        emit(true)*/
    }

    override fun sendTrackerFilter(pageName: String, list: List<String>): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerFilterCore.create(pageName, list)
        val trackerData = TrackerData(trackerCore
            , TrackerUser.create(localSource.sessionId, customerLocalDataSource)
            , localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDatas = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDatas)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDatas)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendTrackerSort(pageName: String, sortType: String): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerSortCore.create(pageName, sortType)
        val trackerData = TrackerData(trackerCore
            , TrackerUser.create(localSource.sessionId, customerLocalDataSource)
            , localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDatas = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDatas)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDatas)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendTrackPageDetail(screeName: String, details: Any?): Flow<TrackerResponse> = flow {
        /*val trackerCore = TrackerPageDetailCore.create(screeName, details)
        val trackerData = TrackerData(trackerCore,
            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

        val trackerDataList = TrackerDatas(mutableListOf(trackerData))

        val response = remoteSource.sendTracks(trackerDataList)
        when(response.status) {
            Result.Status.SUCCESS -> emit(TrackerResponse(Gson().toJson(trackerData), response.data))
            Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                localSource.add(trackerDataList)
                emit(
                    TrackerResponse(Gson().toJson(trackerData), null)
                )
            }
            else -> {}
        }*/
    }

    override fun sendTrackPageDetailImmediately(screeName: String, details: Any?) {
        /*try {
            CoroutineScope(Dispatchers.IO).launch {
                val trackerCore = TrackerPageDetailCore.create(screeName, details)
                val trackerData = TrackerData(trackerCore,
                    TrackerUser.create(localSource.sessionId, customerLocalDataSource),
                    localSource.apps, configurationLocalDataSource.getCached()?.installReferrer)

                val trackerDataList = TrackerDatas(mutableListOf(trackerData))

                val response = remoteSource.sendTracks(trackerDataList)
                when (response.status) {
                    Result.Status.SUCCESS -> {

                    }
                    Result.Status.ERROR, Result.Status.UNAUTHORIZED -> {
                        localSource.add(trackerDataList)
                    }
                    else -> {
                    }
                }
            }
        }
        catch (e: IllegalStateException) {
            // nothing to do
        }*/
    }

    override fun flush(): Flow<Boolean> = flow {
        /*val cached = localSource.getCached()
        if (cached != null && cached.isNotEmpty()) {
            val response = remoteSource.sendTracks(TrackerDatas(cached.toMutableList()))
            if (response.status == Result.Status.SUCCESS) {
                localSource.clear()
            }
        }

        emit(true)*/
    }

    override fun addImpression(screeName: String, name: String) {
        /*if (! impressions.containsKey(screeName)) {
            impressions[screeName] = mutableMapOf()
        }

        val map = impressions[screeName]
        if (map != null) {
            val time = Date().time
            if (! map.containsKey(time)) {
                map[time] = mutableListOf()
            }

            val list = map[time]
            list?.add(name)
        }*/
    }

    override fun sendImpressions(screeName: String): Flow<String?> = flow {
        /*if (impressions.containsKey(screeName)) {
            val map = impressions[screeName]
            runBlocking {
                impressions.remove(screeName)
            }
            if (map != null) {
                val tracks = mutableListOf<TrackerData>()
                val keys = map.keys
                for (key in keys) {
                    val value = map[key]
                    if (value != null) {
                        val core = TrackerImpressionCore.create(key, screeName, value)

                        val data = TrackerData(
                            core,
                            TrackerUser.create(localSource.sessionId, customerLocalDataSource),
                            localSource.apps, configurationLocalDataSource.getCached()?.installReferrer
                        )
                        tracks.add(data)
                    }
                }

                if (tracks.isEmpty()) {
                    emit("")
                } else {
                    val response = remoteSource.sendTracks(TrackerDatas(tracks))

                    when (response.status) {
                        Result.Status.SUCCESS -> emit(response.data)
                        Result.Status.ERROR, Result.Status.UNAUTHORIZED -> emit(null)
                        else -> {
                        }
                    }
                }
            }
        }*/
    }

}