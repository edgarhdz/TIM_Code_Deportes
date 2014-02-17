function loadLatLon (google,options) {
    var address = options.address;
    var geocoder = new google.maps.Geocoder();
         	geocoder.geocode( { 'address': address}, function(results, status) {
                 if (results[0]==undefined)  {
                     options.textarea.findParentByType('panel').getComponent(1).setValue(0);
                     options.textarea.findParentByType('panel').getComponent(2).setValue(0);
                 } else {
                     var location = results[0].geometry.location;
                     options.textarea.findParentByType('panel').getComponent(1).setValue(location.lat());
                     options.textarea.findParentByType('panel').getComponent(2).setValue(location.lng());
                 }

            });
} 