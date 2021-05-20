
        $(document).ready(function(){

        var elem = document.createElement('canvas');
        if(!(elem.getContext && elem.getContext('2d'))){ return };
          // Some simple loops to build up data arrays.
          var cosPoints = [];
          for (var i=0; i<2*Math.PI; i+=0.4){ 
            cosPoints.push([i, Math.cos(i)]); 
          }
            
          var sinPoints = []; 
          for (var i=0; i<2*Math.PI; i+=0.4){ 
             sinPoints.push([i, 2*Math.sin(i-.8)]); 
          }
            
          var powPoints1 = []; 
          for (var i=0; i<2*Math.PI; i+=0.4) { 
              powPoints1.push([i, 2.5 + Math.pow(i/4, 2)]); 
          }
            
          var powPoints2 = []; 
          for (var i=0; i<2*Math.PI; i+=0.4) { 
              powPoints2.push([i, -2.5 - Math.pow(i/4, 2)]); 
          } 
         
          var plot3 = $.jqplot('line-chart', [cosPoints, sinPoints, powPoints1, powPoints2], 
            { 
              // Series options are specified as an array of objects, one object
              // for each series.
              axesDefaults: {
                   showTickMarks:false,
                   show: false,
                   tickOptions: {
                       mark: 'outside',    // Where to put the tick mark on the axis
                                        // 'outside', 'inside' or 'cross',
                       showMark: false,
                       showGridline: true, // wether to draw a gridline (across the whole grid) at this tick,
                       markSize: 4,        // length the tick will extend beyond the grid in pixels.  For
                                   
                       show: true,     
                       showLabel: false,
                       formatString: '', 
                   }
              },
              grid: {
                drawGridLines: false,        // wether to draw lines across the grid or not.
                gridLineColor: '#cccccc',    // *Color of the grid lines.
                background: '#fff',      // CSS color spec for background color of grid.
                borderColor: '#999999',     // CSS color spec for border around grid.
                borderWidth: 1.0,           // pixel width of border around grid.
                shadow: false,               // draw a shadow for grid.
                shadowAngle: 45,            // angle of the shadow.  Clockwise from x axis.
                shadowOffset: 1.5,          // offset from the line of the shadow.
                shadowWidth: 3,             // width of the stroke for the shadow.
                shadowDepth: 3,             // Number of strokes to make when drawing shadow.
                                            // Each stroke offset by shadowOffset from the last.
                shadowAlpha: 0.07,           // Opacity of the shadow
                renderer: $.jqplot.CanvasGridRenderer,  // renderer to use to draw the grid.
                rendererOptions: {}         // options to pass to the renderer.  Note, the default
                                            // CanvasGridRenderer takes no additional options.
            },
              series:[ 
                  {
                    // Change our line width and use a diamond shaped marker.
                    lineWidth:2, 
                    markerOptions: { style:'dimaond' },
                              showTickMarks: false,
                  }, 
                  {
                    // Don't show a line, just show markers.
                    // Make the markers 7 pixels with an 'x' style
                    showLine:false, 
                    //markerOptions: { size: 7, style:"x" }
                  },
                  { 
                    // Use (open) circlular markers.
                    markerOptions: { style:"circle" },
                              showTickMarks: false,
                  }, 
                  {
                    // Use a thicker, 5 pixel line and 10 pixel
                    // filled square markers.
                    lineWidth:5, 
                    markerOptions: { style:"filledSquare", size:10 },
                              showTickMarks: false,
                  }
              ]
            }
          );
            
        });
