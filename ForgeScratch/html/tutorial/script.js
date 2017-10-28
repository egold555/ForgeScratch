$(document).on("click", "a", function(event){
	event.preventDefault();
    setSelected($(this).attr("href"));
});

function setSelected(id){
	$("a").attr("href", function(i, oldHref) {
  		if(oldHref == id){
  			$('.active').removeClass('active');
    		$(this).addClass("active");
    		java_app.saveSelected(id);
  		}
	});
}