package com.krykun.reduxmvi

import com.krykun.reduxmvi.global.Action


enum class Feature {
    GLOBAL,
    SELF_TEST,
    CALL,
    ROSTER,
    CALL_INVITES,
    MEETING_DETAILS,
    WAITING_ROOM,
    PTZ_CONTROLS,
    PRE_CALL,
    CHAT,
    SAVE_TO_EMR,
    CHAT_IMAGE_AUTO_PREVIEW,
    PSTN_INVITE,
    DIAL_IN,
}

data class SetupFeature(val feature: Feature) : Action

data class CleanupFeature(val feature: Feature) : Action
