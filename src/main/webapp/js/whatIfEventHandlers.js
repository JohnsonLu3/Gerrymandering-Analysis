var areaInfoWindow;
var listOfSuperDistricts=[];//list of super-districts - each super-district is a list of features
var currentSuperDistrictIndex = null;
var startingNewSuperDistrict=false;
var clickHistory = [];
var previousColor = null;

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    console.log("Color: " + color);
    return color;
}

function selectDistrictByClickListener(map,data,areaInfoWindow) {
	// When the user clicks, set 'isColorful', and change color of district.
	var contentString,districtDemVotes=0,districtRepVotes=0;
	var demVotesArray = data.map((e) => {
        return typeof e.DemVotes === 'string' ? parseInt(e.DemVotes.replace(",", "")) : e.DemVotes;
    });            
    //repeat finding the sum for the republicans
    var repVotesArray = data.map((e) => {
        return typeof e.RepVotes === 'string' ? parseInt(e.RepVotes.replace(",", "")) : e.RepVotes;
    });   
	console.log("demVotesArray.length: " + demVotesArray.length);
	console.log("repVotesArray.length: " + repVotesArray.length);
	console.log("demVotesArray[26]: " + demVotesArray[26]);
	console.log("repVotesArray[26]: " + repVotesArray[26]);
	map.data.addListener('click', function(event) {
		selectDistrictByClickHandler(map,event,areaInfoWindow,demVotesArray,repVotesArray);	
	});
}
function selectDistrictByClickHandler(map,event,areaInfoWindow,demVotesArray,repVotesArray){
		if (areaInfoWindow) {
        	areaInfoWindow.close();
    	}
		event.feature.setProperty('isColorful', true);
    	map.setCenter(event.latLng);
		map.setZoom(7);
    	districtDemVotes = demVotesArray[event.feature.getProperty('CD115FP')-1];
    	districtRepVotes = repVotesArray[event.feature.getProperty('CD115FP')-1];
      	contentString = "District Number:"+event.feature.getProperty('CD115FP')+'<br/>'+"DemVotes: "+districtDemVotes+"<br/>"+"RepVotes: "+districtRepVotes;
		areaInfoWindow = new google.maps.InfoWindow({
        	content: contentString ,
        	position: event.latLng
    	});
    	areaInfoWindow.open(map);		
}
function superDistrictListener(map, selected){
    var features = selected.features;
    return map.data.addListener('click', function(event){
        features.some(feature => {
            if(feature.getProperty('DistrictNo') === event.feature.getProperty('DistrictNo') &&
                !feature.getProperty('isSuperDistrict')){
                superDistrictHandler(map, event.feature, false);
            }
        });
    });
}

function superDistrictHandler(map,eventFeature,undo){
    var selectedDistrictGeom = eventFeature.getGeometry();
    var boundaryPtInList=false;
    if(listOfSuperDistricts.length==0 || startingNewSuperDistrict == true){
        startingNewSuperDistrict = true;
    	addDistrictFeature(map,eventFeature,undo);
    }
    else{
    	var currentSuperDistrict = listOfSuperDistricts[currentSuperDistrictIndex];
    	if(currentSuperDistrict.length==0){
    		addDistrictFeature(map,eventFeature,undo);
    	}else{
    	    locatedDistrict = locateSelectedDistrict(eventFeature);
    	    if(locatedDistrict.found && locatedDistrict.superdistrictIndex == currentSuperDistrictIndex){
    	        removeDistrictFeature(map, eventFeature, undo);
    	        return;
			}
            //see if selected district's points are contained in the polygons of the districts that are already in the currentSuperDistrict
			currentSuperDistrict.forEach(districtFeature => {
				var geom = districtFeature.getGeometry();
				var polygons = [];
				if(geom.getType() === "MultiPolygon"){
				    for(i = 0;i < geom.getLength();i++){
				    	p = geom.getAt(i);
				    	polygons.push(new google.maps.Polygon({paths: p.getAt(0).getArray()}));
					}
				}
				else if(geom.getType() === "Polygon"){
                    polygons.push(new google.maps.Polygon({paths: geom.getAt(0).getArray()}));
				}
                selectedDistrictGeom.forEachLatLng(function(LatLng){
                	polygons.forEach(poly => {
                        if(google.maps.geometry.poly.containsLocation(LatLng, poly))
                            boundaryPtInList = true;
					});
				});
			});
            if(boundaryPtInList==true){
                addDistrictFeature(map,eventFeature,undo);
            }else{
                alert("Error: Superdistricts must be contiguous when being chosen.");
            }
      	}
	}
}

function locateSelectedDistrict(feature){
    var superDistrictIndex = 0;
    var districtIndex = 0;
    var found = false;
    listOfSuperDistricts.some((superdistrict, si) => {
		superdistrict.some((district, di) => {
			if(district.getProperty('DistrictNo') === feature.getProperty('DistrictNo')){
				superDistrictIndex = si;
				districtIndex = di;
				found = true;
				return true;
            }
        });
	});
    return {found: found, superdistrictIndex: superDistrictIndex, districtIndex: districtIndex};
}

function locateSuperDistrict(feature){
	var districtNos = feature.getProperty("Districts");
    var superDistrictIndex = 0;
    var found = false;
    listOfSuperDistricts.some((superdistrict, si) => {
        superdistrict.some((district, di) => {
            if(district.getProperty('DistrictNo') === districtNos[0])
            {
                superDistrictIndex = si;
                found = true;
                return true;
            }
        });
    });
    return {found: found, superdistrictIndex: superDistrictIndex};
}

function addDistrictFeature(map,eventFeature,undo){
	console.log("in addDistrictFeature");
	console.log("@@@@@@ startingNewSuperDistrict value: "+startingNewSuperDistrict);
	if(startingNewSuperDistrict==true){
        var newSuperDistrict=[];
		listOfSuperDistricts.push(newSuperDistrict);
		currentSuperDistrictIndex = listOfSuperDistricts.length - 1;
        startingNewSuperDistrict=false;
	}
	console.log("@@@@@@@@ listOfSuperDistricts.length:"+listOfSuperDistricts.length);
	var currentSuperDistrict=listOfSuperDistricts[currentSuperDistrictIndex];
	currentSuperDistrict.push(eventFeature);
	console.log("number of features in current super-district after adding new feature:"+currentSuperDistrict.length);
	currentSuperDistrict.forEach(feature => {
        map.data.overrideStyle(feature, {fillColor: 'gold', strokeColor: 'red'});
	});
	if(!undo)
        clickHistory.push({type: 'single', feature: eventFeature});
}

function undoListener(map){
	document.getElementById("undoButton").addEventListener('click', function(event) {		
		undoHandler(map);
		//event.stopPropogation();
		$(document).on("click",".messagepop",function(event) {
      		event.stopPropagation();
  		    alert(5); 
  		});
	});
}

function undoHandler(map){
	console.log("Undo fired");
	if(clickHistory.length > 0){
        var history = clickHistory.pop();
        if(history.type === 'single')
            superDistrictHandler(map, history.feature, true);
        else if(history.type === 'multi'){
        	history.feature.forEach(feature => {
        		superDistrictHandler(map, feature, true);
			});
		}
		else if(history.type === 'super'){
        	createSuperDistrictHandler(map, true);
		}
		else if(history.type === 'show'){
		    showDistrictHandler(map, history.feature, true);
		}
	}
}

function removeDistrictFeature(map, eventFeature, undo){
	var location = locateSelectedDistrict(eventFeature);
	listOfSuperDistricts[location.superdistrictIndex].splice(location.districtIndex, 1);
	if(listOfSuperDistricts[location.superdistrictIndex].length == 0){
		listOfSuperDistricts.splice(location.superdistrictIndex, 1);
		startingNewSuperDistrict = true;
	}
	map.data.overrideStyle(eventFeature, {fillColor: 'grey', strokeColor: 'black'});
    console.log("Feature removed");
    console.log("startingNewSuperDistrict value: " + startingNewSuperDistrict);
    console.log("listOfSuperDistricts length: " + listOfSuperDistricts.length);
    if(!undo)
        clickHistory.push({type: 'single', feature: eventFeature});
}

function createSuperDistrictListener(map){
	document.getElementById("createButton").addEventListener('click', function(){
		createSuperDistrictHandler(map, false);
	});

	map.data.addListener('click', function(event){
        if(event.feature.getProperty('isSuperDistrict'))
        	showDistrictHandler(map, event.feature, false);
	});
}

function createSuperDistrictHandler(map, undo){
	console.log("Create super district fired!")
    var currentSuperdistrict = listOfSuperDistricts[currentSuperDistrictIndex];
	map.data.toGeoJson(function(json){
        console.log(json);
        selected = json
				.features
				.filter(feature =>
                    feature.properties.DistrictNo != undefined &&
                    isSelected(feature.properties.DistrictNo, currentSuperdistrict)
                );
        var combined = combineDistricts(selected);
        features = map.data.addGeoJson(combined);
        features[0].setProperty("isSuperDistrict", true);
        if(!undo)
            previousColor = getRandomColor();
        map.data.overrideStyle(features[0],
            {fillColor: previousColor, strokeColor: 'black', zIndex: setting.superDistrictZoom, fillOpacity: 1.0});
        labelSuperDistrict(features[0], currentSuperdistrict);
        startingNewSuperDistrict = true;
        if(!undo)
            clickHistory.push({type: 'super', feature: features[0]});
	});
}

function showDistrictHandler(map, feature, undo){
    var location = locateSuperDistrict(feature);
    if(location.found == true)
    	currentSuperDistrictIndex = location.superdistrictIndex;
    console.log("currentSuperDistrictIndex: " + currentSuperDistrictIndex);
    map.data.remove(feature);
    startingNewSuperDistrict = false;
    if(!undo)
        clickHistory.push({type: 'show', feature: feature});
}

function labelSuperDistrict(feature, districts){
    var districtNos = districts.map(d => {return d.getProperty('DistrictNo');});
    feature.setProperty("Districts", districtNos);
}

function isSelected(districtNo, selected){
    for(i = 0;i < selected.length;i++){
    	if(selected[i].getProperty('DistrictNo') == districtNo)
    		return true;
	}
	return false;
}

function saveSuperDistrictListener(map){
	document.getElementById("saveButton").addEventListener('click', function() {
		saveSuperDistrictHandler(map);	
	});
}
function saveSuperDistrictHandler(map){
	
}
function resetSuperDistrictListener(map){
	document.getElementById("resetButton").addEventListener('click', function() {
		resetSuperDistrictHandler(map);
	});
}
function resetSuperDistrictHandler(map){
	if(listOfSuperDistricts.length==0){
		return;
	}else{
		var currentSuperDistrict=listOfSuperDistricts[currentSuperDistrictIndex];
        var resetFeatures = [];
        currentSuperDistrict.forEach(districtFeature => {
            resetFeatures.push(districtFeature);
        });
        resetFeatures.forEach((districtFeature) => {
        	removeDistrictFeature(map, districtFeature, true);
		});
        clickHistory.push({type: 'multi', feature: resetFeatures});
	}
}

function cancelSuperDistrictListener(map){
	document.getElementById("cancelButton").addEventListener('click', function() {
		cancelSuperDistrictHandler(map);
	});
}

function cancelSuperDistrictHandler(map){
    if(selectedState != null){
        selectedState.features.forEach(feature => {map.data.remove(feature)});
        selectedState.listener.remove();
        selectedState = null;
    }

    listOfSuperDistricts = [];
    currentSuperDistrictIndex = null;
    startingNewSuperDistrict = false;

    map.setCenter(setting.center);
    map.setZoom(setting.countryZoom);
}

function combineDistricts(superdistrict){
	var combined = turf.union(...superdistrict);
	combined.properties = {};
	return combined;
}