var timeouts = {};

onmessage = function(event) {
    var data = event.data;
    var command = data.command;
    var id = data.id;
    var delay = data.delay;

    if (command === 'setTimeout') {
        var timeoutId = setTimeout(function() {
            postMessage({ id: id });
            delete timeouts[id];
        }, delay);
        timeouts[id] = timeoutId;
    } else if (command === 'clearTimeout') {
        if (timeouts.hasOwnProperty(id)) {
            clearTimeout(timeouts[id]);
            delete timeouts[id];
        }
    }
};
