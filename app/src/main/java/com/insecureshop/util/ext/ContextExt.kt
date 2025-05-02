package com.insecureshop.util.ext

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Build

@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun Context.registerReceiverCompat(
    receiver: BroadcastReceiver,
    intentFilter: IntentFilter,
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    registerReceiver(
        receiver,
        intentFilter,
        Context.RECEIVER_NOT_EXPORTED,
    )
} else {
    registerReceiver(receiver, intentFilter)
}
