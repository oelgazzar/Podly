package com.example.podly.data.sample

import com.example.podly.models.Track

val sampleTracks = mapOf(
    "Dramatic Cinematic Documentary" to "https://cdn.pixabay.com/download/audio/2026/09/23/audio_ca70d954dd.mp3?filename=musicdream-dramatic-cinematic-documentary-609202.mp3",
    "Sport - Sports Rock Music" to "https://cdn.pixabay.com/download/audio/2026/09/05/audio_75ff83e729.mp3?filename=lnplusmusic-sport-sports-rock-music-597971.mp3",
    "Suspense Tension - Suspenseful Tense" to "https://cdn.pixabay.com/download/audio/2025/04/05/audio_c3cb571f3b.mp3?filename=lnplusmusic-suspense-tension-suspenseful-tense-323181.mp3",
    "Football - Football Music" to "https://cdn.pixabay.com/download/audio/2026/06/21/audio_f7ff5b4757.mp3?filename=sigmamusicart-football-football-music-551346.mp3",
    "Dark" to "https://cdn.pixabay.com/download/audio/2026/07/21/audio_1d4d5e4ead.mp3?filename=audiocopper-dark-571483.mp3",
    "Wonders of the Earth" to "https://cdn.pixabay.com/download/audio/2026/06/13/audio_73aac427fe.mp3?filename=grand_project-wonders-of-the-earth-550792.mp3",
    "Standard Test Track" to "https://github.com/rafaelreis-hotmart/Audio-Sample-files/raw/master/sample.mp3",
    "Moment of Peace - MickeysCat" to "https://cdn.pixabay.com/download/audio/2026/06/19/audio_b51b8d68f8.mp3?filename=mickeyscat-moment-of-peace-mickeyscat-554494.mp3",
    "No Copyright Music" to "https://cdn.pixabay.com/download/audio/2026/06/04/audio_7985c1bbf8.mp3?filename=sigmamusicart-no-copyright-music-537751.mp3",
    "Funk & Breakbeat (Upbeat Advertising Happy Cook)" to "https://cdn.pixabay.com/download/audio/2026/06/09/audio_168302b854.mp3?filename=alexguz-funk-amp-breakbeat-upbeat-advertising-happy-cook-541097.mp3",
    "Escape Your Love (Upbeat Fashion Pop Dance)" to "https://cdn.pixabay.com/download/audio/2025/09/29/audio_77a36612dd.mp3?filename=fassounds-escape-your-love-upbeat-fashion-pop-dance-412230.mp3",
    "Water | Afro-pop Music" to "https://cdn.pixabay.com/download/audio/2025/12/11/audio_249561b3fc.mp3?filename=kontraa-water-afro-pop-music-445661.mp3",
    "Carnaval" to "https://cdn.pixabay.com/download/audio/2026/02/13/audio_358644a2e3.mp3?filename=alec_koff-carnaval-484622.mp3",
).map { (title, url) ->
    Track(title, url)
}