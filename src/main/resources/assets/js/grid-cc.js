function reloadElement(id) {
    $(id).load(location.href + ' ' + id);
}

function showToast(text) {
    $(".toast").text(text).fadeIn(200).delay(800).fadeOut(100);
}

function fixUrl() {
    var url = document.URL;
    history.replaceState(null, null, url.substr(0, url.indexOf('#')));
}

function startHub() {
    var xmlhttp;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            console.log("hub started");
            showToast("Hub started");
            reloadElement('#info');
            fixUrl();
        }
    };

    var form = document.getElementById("hubForm");
    var fields = {};
    for (var i = 0; i < form.length; i++) {
        var field = form[i];
        if (field.id == 'hubParamsInput') {
            fields[field.name] = field.value;
        }
    }
    xmlhttp.open("GET", "/hub/start?params=" + form[0].value, true);
    xmlhttp.send();
}

function stopHub() {
    var xmlhttp;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            showToast("Hub stopped");
            reloadElement('#info');
            fixUrl();
        }
    };
    xmlhttp.open("GET", "/hub/stop", true);
    xmlhttp.send();
}

function startNode(host, port, formId) {
    var xmlhttp;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            showToast("Node started");
            reloadElement('#info');
            fixUrl();
        }
    };

    var form = document.getElementById(formId);
    var fields = {};
    for (var i = 0; i < form.length; i++) {
        var field = form[i];
        fields[field.name] = field.value;
    }
    xmlhttp.open("GET", "/hub/startNode?host=" + host + "&port=" + port + "&params=" + form[0].value, true);
    xmlhttp.send();
}

function stopNode(host, port) {
    var xmlhttp;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            showToast("Node stopped");
            reloadElement('#info');
            fixUrl();
        }
    };
    xmlhttp.open("GET", "/hub/stopNode?host=" + host + "&port=" + port, true);
    xmlhttp.send();
}

$(document).ready(function () {
    $(".fancybox").fancybox({
        padding: 0
    });
});