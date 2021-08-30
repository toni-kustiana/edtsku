package id.co.edtslib.domain.model

data class TrackerData(
    val core: Any,
    val user: TrackerUser?,
    val application: TrackerApps?,
    val marketing: InstallReferer?
)
