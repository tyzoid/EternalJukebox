const timeouts = new Map();

onmessage = function(event) {
    const { command, id, delay } = event.data;

    if (command === 'setTimeout') {
        const timeoutId = setTimeout(() => {
            postMessage({ id });
            timeouts.delete(id);
        }, delay);
        timeouts.set(id, timeoutId);
    } else if (command === 'clearTimeout') {
        if (timeouts.has(id)) {
            clearTimeout(timeouts.get(id));
            timeouts.delete(id);
        }
    }
};
