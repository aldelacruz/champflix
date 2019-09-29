
$(document).ready(function() {
	isChildFriendly();
      });
function isChildFriendly() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/is-child-friendly",
        cache: false,
        timeout: 600000,
        success: function (data) {
			        	if (data == false) {
				$('#note').html("Some contents may not be child friendly. ")
						.append(
								$('<a>').attr('id', "close").attr("onclick",
										"closeNotes()").html("[close]"));
			}else{
				$('#note').hide;
			}
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}

function closeNotes(){
		var note = document.getElementById("note");
	   note.style.display = 'none';
}

