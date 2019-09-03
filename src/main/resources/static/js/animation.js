var $animation_elements = $('.animation-element');
	var $window = $(window);
	function check_if_in_view() {
		var window_height = $window.height();
		var window_top_position = $window.scrollTop();
		var window_bottom_position = (window_top_position + window_height);
		$.each($animation_elements, function() {
			var $element = $(this);
			var element_height = $element.outerHeight();
			var element_top_position = $element.offset().top;
			var element_bottom_position = (element_top_position + element_height);
			//check to see if this current container is within viewport
			if ((element_bottom_position >= window_top_position) &&
					(element_top_position <= window_bottom_position)) {
				$element.addClass('in-view');
			} else {
				$element.removeClass('in-view');
			}
		});
	}

	$window.on('scroll resize', check_if_in_view);
	$window.trigger('scroll');

	$(document).ready(function(){
		var $ratings = $('.rating');
		$.each($ratings, function () {
			var $element = $(this);
			var rating = $element[0].textContent;
			const highest_rating = 10.0;
			const mid_low_rating = 3.0;
			const mid_high_rating = 7.0;
			if((rating <= highest_rating) && (rating > mid_high_rating)){
				$element.addClass('has-text-success');
			}
			else if ((rating <= mid_high_rating) && (rating > mid_low_rating)){
				$element.addClass('has-text-warning');
			}
			else{
				$element.addClass('has-text-danger');
			}
		});

		$(window).load(function() {
			setTimeout(function () {
				$('.pageloader').fadeOut();
			}, 3000);
		});
	});
