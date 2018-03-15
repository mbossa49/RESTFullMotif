/*
 * Basic jQuery Slider plug-in v.1.1
 * 
 * http://www.basic-slider.com
 *
 * Authored by John Cobb
 * Visit my blog at http://www.johncobb.name
 * Or say helo on twitter: @john0514
 *
 * Copyright 2011, John Cobb
 * Free for all to use, abuse and improve under the MIT license.
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * August 2011
 */
/* Adaptation : Hervé Juin*/
 
(function($){
	$.fn.bjqs = function(options) {
		
	var settings = {};	
		
	var defaults = {
		width: 224,					// Width + Height used to ensure consistency
		height: 140,				// Width + Height used to ensure consistency
		animation: 'fade',			// The type of animation (slide or fade)
		animationDuration: 1000, 	// The duration in ms of the transition between slides
		automatic: true,			// Automatically rotate through the slides
		rotationSpeed: 10000,		// Delay in ms between auto rotation of the slides
		hoverPause: true,			// Pause the slider when any elements receive a hover event
		showControls: true,			// Show the manual slider controls
		centerControls: false,		// Center the controls vertically
		nextText: '',			// Text to display in 'next' controller
		prevText: '',			// Text to display in 'previous' controller
		showMarkers: true,			// Show positional markers
		centerMarkers: false,		// Center the positional indicators
		keyboardNav: true,			// Allow navigation with arrow keys
		useCaptions: false			// Use image title text as caption
	}
	
	// Overwrite the defaults with the provided options (if any)
	settings = $.extend({}, defaults, options);
	
	// Variables
	var	$container = this;
		$slider = $('ul.bjqs'),
		slides = $slider.children('li'),
		slideCount = slides.length,
		animating = false,
		paused = false,
		current = 0,
		slidePosition = 1,
		next = 0,
		$active = slides.eq(current),
		forward = 'forward',
		back = 'backward'
	
	// Make everything consistent in size
	// TODO: move away from px and make slider responsive
	slides.css({'height':settings.height,'width':settings.width});
	$slider.css({'height':settings.height,'width':settings.width});
	$container.css({'height':175,'width':224});

	// Add unique class to slide list elements to differentiate from slide content list elements
	slides.addClass('bjqs-slide');
	
	// Phat Controller(s)
	if(settings.showControls && slideCount > 1){
		
		slides.children('img').css({
			'border-radius':'10px 10px 0 10px',
			'-moz-border-radius':'10px 10px 0 10px',
			'-webkit-border-radius':'10px 10px 0 10px',
			'position':'relative',
			'behavior':'url(PIE.htc)'
		});
		lienImage = slides.children('a')
		lienImage.children('img').css({
			'border-radius':'10px 10px 0 10px',
			'-moz-border-radius':'10px 10px 0 10px',
			'-webkit-border-radius':'10px 10px 0 10px',
			'position':'relative',
			'behavior':'url(PIE.htc)'
		});
		
		// Create the elements for the controls
		$controlContainer = $('<ul class="bjqs-controls"></ul>');
		$next = $('<li><a href="#" class="bjqs-next" class="controls"></a></li>');
		$pause = $('<li><a href="#" class="bjqs-pause" class="controls"></a></li>');
		$previous = $('<li><a href="#" class="bjqs-prev" class="controls"></a></li>');
		
		// Put 'em all together and what do you get? Ding dong. Hotdog
		$next.appendTo($controlContainer);
		$pause.appendTo($controlContainer);
		$previous.appendTo($controlContainer);
		$controlContainer.appendTo($container);
		
		// Vertically center the controllers
		if(settings.centerControls){
			$control = $next.children('a');
			offset = ($container.height() -$control.height()) / 2;
			$next.children('a').css('top', offset).show();
			$pause.children('a').css('top', offset).show();
			$previous.children('a').css('top', offset).show();
		}
		
		
		// Bind click events to the controllers
		$next.click(function(e){
			e.preventDefault();
			if(!animating)
				bjqsGo(forward,false);
				if(paused & settings.automatic){
					bjqsInterval = setInterval(function(){ bjqsGo(forward) }, settings.rotationSpeed);
					paused=false;
					$('a.bjqs-pause2').replaceWith('<a href="#" class="bjqs-pause" class="controls"></a>');
				}
		});
		
		$previous.click(function(e){
			e.preventDefault();
			if(!animating)
				bjqsGo(back, false);
				if(paused & settings.automatic){
					bjqsInterval = setInterval(function(){ bjqsGo(forward) }, settings.rotationSpeed);
					paused=false;
					$('a.bjqs-pause2').replaceWith('<a href="#" class="bjqs-pause" class="controls"></a>');
				}
		});
		
		$pause.click(function(event){
			if(!paused){
				clearInterval(bjqsInterval);
				paused=true;
				$('a.bjqs-pause').replaceWith('<a href="#" class="bjqs-pause2" class="controls"></a>');
			}
		});
		
	}
	
	// Let's put in some markers
	if(settings.showMarkers && slideCount > 1){
		
		$markerContainer = $('<ol class="bjqs-markers"></ul>');
		
		//Create a marker for each banner and add append it to the wrapper
		$.each(slides,function(key,value){
			if(settings.animType == 'slide'){
				if(key != 0 && key != slideCount-1)
					$marker = $('<li><a href="#"></a></li>');
			}
			else{
				key++
				$marker = $('<li><a href="#"></a></li>');
			}
			
			$marker.click(function(e){
				e.preventDefault();
				if(!$(this).hasClass('active-marker') && !animating)
					bjqsGo(false,key);
					if(paused & settings.automatic){
						bjqsInterval = setInterval(function(){ bjqsGo(forward) }, settings.rotationSpeed);
						paused=false;
						$pause.replaceWith('<li><a href="#" class="bjqs-pause" class="controls"></a></li>');
					}
			});
			
			$marker.appendTo($markerContainer);
			
		});
		
		markers = $markerContainer.children('li');
		markers.eq(current).addClass('active-marker');
		$markerContainer.appendTo($container);
		
		if(settings.centerMarkers){
			offset = (settings.width - $markerContainer.width() )/ 2;
			$markerContainer.css('left', offset);
		}
		
	}
	
	// Enable keyboard navigation
	if(settings.keyboardNav && slideCount > 1){
		
		$(document).keyup(function(event) {
			
			if(!paused){
				clearInterval(bjqsInterval);
				paused=true;
			}
			
			if (!animating) {
				if(event.keyCode == 39){
					event.preventDefault();
					bjqsGo(forward, false);
				}
				else if(event.keyCode == 37){
					event.preventDefault();
					bjqsGo(back,false);
				}
			}
			
			if(paused & settings.automatic){
				bjqsInterval = setInterval(function(){ bjqsGo(forward) }, settings.rotationSpeed);
				paused=false;
			}
			
		});
	}
	
	// Show captions
	if(settings.useCaptions){
		
		$.each(slides, function(key, value){
			
			var $slide = $(value);
			var $slideChild = $slide.children('img:first-child');
			var title = $slideChild.attr('title');
			
			if(title){
				var $caption = $('<p class="bjqs-caption">'+title+'</p>');
				$caption.appendTo($slide);
			}

		});
		
	}
	
	// Run a bubble-bath and float in that m'fkr like a hovercraft. (enable hover pause)
	if(settings.hoverPause && settings.automatic){
			
		$slider.hover(function(){
			if(!paused){
				clearInterval(bjqsInterval);
				paused=true;
				$('a.bjqs-pause').replaceWith('<a href="#" class="bjqs-pause2" class="controls"></a>');
			}
		},function(){
			if(paused){
				bjqsInterval = setInterval(function(){ bjqsGo(forward) }, settings.rotationSpeed);
				paused=false;
				$('a.bjqs-pause2').replaceWith('<a href="#" class="bjqs-pause" class="controls"></a>');
			}
		});
		
	}
	
	
	// We have to make a few tweaks if we're sliding instead of fading
	if(settings.animation == 'slide' && slideCount > 1){
		
		$first = slides.eq(0);
		$last = slides.eq(slideCount-1);
		
		$first.clone().addClass('clone').removeClass('slide').appendTo($slider);
		$last.clone().addClass('clone').removeClass('slide').prependTo($slider);
		
		slides = $slider.children('li');
		slideCount = slides.length;
		
		$wrapper = $('<div class="bjqs-wrapper"></div>').css({
			'width' : settings.width,
			'height' : settings.height,
			'overflow' : 'hidden',
			'position' : 'relative'
		});
		
		$slider.css({
			'width' : settings.width*slideCount,
			'left' : -settings.width
		});
		
		slides.css({
			'float': 'left',
			'position': 'relative',
			'display' : 'list-item'
		});
		
		$wrapper.prependTo($container);
		$slider.appendTo($wrapper);
		
	}
	
	// Check position to see if we're at the first or last slide and update 'next' accordingly
	var checkPosition = function(direction){
		
		if(settings.animation == 'fade'){
			
			if(direction == forward){
				!$active.next().length ? next = 0 : next++
			}
			else if(direction == back){
				!$active.prev().length ? next = slideCount-1 : next--
			}
			
		}
		
		if(settings.animation == 'slide'){
			
			if(direction == forward){
				next = slidePosition + 1;
			}
			
			if(direction == back){
				next = slidePosition - 1;
			}
		}
		
		return next;
	}
	
	// Kick off the rotation if we're on auto pilot, but only if we have more than 1 slide (thanks Efrain!)
	if(settings.automatic && slideCount > 1){
		var bjqsInterval = setInterval(function(){ bjqsGo(forward,false) }, settings.rotationSpeed);
	}
	
	// Show the first slide	
	slides.eq(current).show();
	$slider.show();
	
	// What comes next? Hey, Bust a move!
	var bjqsGo =  function(direction,position){
		
		if(!animating){
			
			if(direction){
				next = checkPosition(direction);
			}
			else if(position && settings.animation == 'fade'){
				next = position-1;
			}else{
				next = position;
			}
			
			animating = true;
			
			if(settings.animation == 'fade'){
				
				if(settings.showMarkers){
					markers.eq(current).removeClass('active-marker');
					markers.eq(next).addClass('active-marker');
				}
				
				$next = slides.eq(next);
				
				$active.fadeOut(settings.animationDuration);
				$next.fadeIn(settings.animationDuration, function() {
					$active.hide();
					current = next;
					$active = $next;
					animating = false;
				});
			}
			else if(settings.animation == 'slide'){
				
				if(settings.showMarkers){
					
					markers.eq(slidePosition-1).removeClass('active-marker');
					
					if(next==slideCount-1){
						markers.eq(0).addClass('active-marker');
					}else if(next==0){
						markers.eq(slideCount-3).addClass('active-marker');
					}else{
						markers.eq(next-1).addClass('active-marker');
					}
					
				}
				
				$slider.animate({'left': -next*settings.width}, settings.animationDuration, function(){
					
					if(next==0){
						slidePosition=slideCount-2;
						$slider.css({'left' : -slidePosition*settings.width});
					}else if(next==slideCount-1){
						slidePosition=1;
						$slider.css({'left' : -settings.width});
					}else{
						slidePosition=next;
					}
					
					animating=false;
					
				});
				
			}
			
		}
		
	}
		
	return this; // KTHXBYE
	
	}
})(jQuery);