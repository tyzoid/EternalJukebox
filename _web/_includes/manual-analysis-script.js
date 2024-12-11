function downloadAudioAnalysis(trackId, playerSDK) {
    const bearerToken = playerSDK._client?._transport?._lastToken;
    bearerToken && fetch(`https://api.spotify.com/v1/audio-analysis/${trackId}`, {
        headers: { 'Authorization': `Bearer ${bearerToken}`, 'Content-Type': 'application/json' }
    }).then(r => r.ok && r.json().then(data => {
        const url = URL.createObjectURL(new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' }));
        const link = document.createElement('a');
        link.href = url;
        link.download = `${trackId}.json`;
        link.click();
        URL.revokeObjectURL(url);
    }));
}

function checkForSongChanges(playerSDK) {
    let currentTrackId = null;
    const updateTrack = (playerState) => {
        const currentTrack = playerState?.track_window?.current_track;
        if (currentTrack && currentTrack.content_type === 'music' && currentTrack.id !== currentTrackId) {
            currentTrackId = currentTrack.id;
            downloadAudioAnalysis(currentTrackId, playerSDK);
        }
    };
    updateTrack(playerSDK._controller?._state);
    playerSDK._listeners['state_changed'].push({ listener: (e) => updateTrack(e.data?.state), options: {} });
}

const waitForPlayerSDKInterval = setInterval(() => {
    const nowPlayingBar = document.querySelector('[data-testid=\'now-playing-bar\']');
    if (nowPlayingBar) {
        let fiberNode = null;
        for (const key in nowPlayingBar) {
            if (key.startsWith('__reactFiber$')) {
                fiberNode = nowPlayingBar[key];
                break;
            }
        }
        while (fiberNode && !fiberNode.memoizedProps?.value?._map) fiberNode = fiberNode?.return;
        if (fiberNode) for (const [k, v] of fiberNode.memoizedProps.value._map) {
            if (k.toString() === 'Symbol(PlayerSDK)') {
                const playerSDK = v?.instance?.harmony;
                if (playerSDK) {
                    clearInterval(waitForPlayerSDKInterval);
                    checkForSongChanges(playerSDK);
                    break;
                }
            }
        }
    }
});
