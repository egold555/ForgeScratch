var video;

function onLoad() {
    video = document.getElementById("video");
     $('#video').on('ended',function(){
      console.log('Video has ended!');
      pause();
    });
}

$(document).on("click", "a", function(event) {
    event.preventDefault();
    setSelected($(this).attr("href"));
});

function setSelected(id) {
    $("a").attr("href", function(i, oldHref) {
        if (oldHref == id) {
            $('.active').removeClass('active');
            $(this).addClass("active");

            video.src = "videos/" + id + ".mp4";
            video.currentTime = 0;
            play();

            java_app.saveSelected(id);
        }
    });
}

function playPause() {
    if (video.paused) {
        play();
    } else {
        pause();
    }
}

function play() {
    video.play();
    $("#btnPlay").text("❙❙");
}

function pause() {
    video.pause();
    $("#btnPlay").text("▶");
}

function restart() {
    var video = document.getElementById("video");
    video.currentTime = 0;
    pause();
}