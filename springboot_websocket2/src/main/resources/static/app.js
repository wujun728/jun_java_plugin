function showNewCommand(message) {
  $("#new-progressbar").prepend("<tr><td>" + message + "</td></tr>");
}

function showOldCommand(message) {
  $("#attached-progressbar").prepend("<tr><td>" + message + "</td></tr>");
}


$(function () {

  $("#start-new").click(function () {
    startNew($("#title").val(), $("#total").val());
  });

  $("#attach-old").click(function () {
    attachOld($("#id").val());
  });


});


function startNew(title, total) {

  $.get("/progressbar/start", {"title": title, "total": total}, function (id) {

    var socket = new SockJS('/_stomp');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);

      $("#new-progressbar").empty();

      showNewCommand("Connected");

      var destination = '/queue/progressbar/' + id;
      console.log("Subscribe to " + destination);

      stompClient.subscribe(destination, function (message) {

        showNewCommand(id + " : " + JSON.stringify(message.body));

        var parse = JSON.parse(message.body);
        if (parse.finished) {
          stompClient.disconnect();
          showNewCommand("Disconnected");

        }

      });

    });

  })

}


function attachOld(id) {

  var socket = new SockJS('/_stomp');
  var stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    $("#attached-progressbar").empty();

    showOldCommand("Connected");

    var destination = '/queue/progressbar/' + id;
    console.log("Subscribe to " + destination);

    stompClient.subscribe(destination, function (data) {

      showOldCommand(id + " : " + JSON.stringify(data.body));

      var parse = JSON.parse(data.body);
      if (parse.finished) {
        stompClient.disconnect(function () {
          showOldCommand("Disconnected");
        });


      }

    });

  });


}
