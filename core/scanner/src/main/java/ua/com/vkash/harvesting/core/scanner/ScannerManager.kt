package ua.com.vkash.harvesting.core.scanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.vkash.harvesting.core.common.AppInfo
import com.vkash.harvesting.core.common.di.ApplicationInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow

class ScannerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationInfo private val appInfo: AppInfo
) {
    val resultFlow =
        callbackFlow {
            val scanReceiver =
                object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        intent.takeIf { it?.action == ACTION_EVENT }?.run {
                            val decodeResult = getBooleanExtra(EXTRA_EVENT_DECODE_RESULT, false)
                            val decodeLength = getIntExtra(EXTRA_EVENT_DECODE_LENGTH, 0)
                            val decodeValue = getByteArrayExtra(EXTRA_EVENT_DECODE_VALUE)

                            if (decodeValue == null) {
                                trySend(decodeResult to "")
                            } else {
                                val scanResult = String(decodeValue, 0, decodeLength)
                                trySend(decodeResult to scanResult)
                            }
                        }
                    }
                }

            val filter = IntentFilter(ACTION_EVENT)
            ContextCompat.registerReceiver(
                context,
                scanReceiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            if (appInfo.isDevDevice) {
                delay(3_000L)
                trySend(true to DEBUG_USER_BARCODE)
            }
            awaitClose { context.unregisterReceiver(scanReceiver) }
        }

    init {
        val intent1 = Intent(ACTION_ENABLED_SCANNER)
        intent1.putExtra(EXTRA_ENABLED_SCANNER, 1) // global enable
        val intent2 = Intent(ACTION_ENABLED_TRIGGER)
        intent2.putExtra(EXTRA_ENABLED_TRIGGER, 1)
        val intent3 = Intent(ACTION_ENABLED_TOUCHSCAN)
        intent3.putExtra(EXTRA_ENABLED_TOUCHSCAN, 0) // disable touch button
        context.run {
            sendBroadcast(intent1)
            sendBroadcast(intent2)
            sendBroadcast(intent3)
        }
    }

    companion object {
        const val DEBUG_USER_BARCODE = "1234567890123"
        private const val ACTION_EVENT = "device.scanner.EVENT"
        private const val ACTION_ENABLED_SCANNER = "device.common.ENABLED_SCANNER"
        private const val ACTION_ENABLED_TRIGGER = "device.common.ENABLED_TRIGGER"
        private const val ACTION_ENABLED_TOUCHSCAN = "device.common.ENABLED_TOUCHSCAN"
        private const val EXTRA_EVENT_DECODE_RESULT = "EXTRA_EVENT_DECODE_RESULT"
        private const val EXTRA_EVENT_DECODE_LENGTH = "EXTRA_EVENT_DECODE_LENGTH"
        private const val EXTRA_EVENT_DECODE_VALUE = "EXTRA_EVENT_DECODE_VALUE"
        private const val EXTRA_ENABLED_SCANNER = "EXTRA_ENABLED_SCANNER"
        private const val EXTRA_ENABLED_TRIGGER = "EXTRA_ENABLED_TRIGGER"
        private const val EXTRA_ENABLED_TOUCHSCAN = "EXTRA_ENABLED_TOUCHSCAN"
    }
}
