var areaInfoWindow;
var listOfSuperDistricts=[];//list of super-districts - each super-district is a list of features
var currentSuperDistrictIndex = null;
var startingNewSuperDistrict=false;
var clickHistory = [];
var previousColor = null;
var doneBuildingSuperdistrict=false;
var selectedStateForRedistricting;

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
    selectedStateForRedistricting=selected;
 
    return map.data.addListener('click', function(event){
        console.log("In superDistrictListener- eventFeature.getProperty('isSuperDistrict'): "+event.feature.getProperty('isSuperDistrict'));
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
            console.log("district.getProperty('DistrictNo'):"+district.getProperty('DistrictNo'));
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
    if(startingNewSuperDistrict==true){
        doneBuildingSuperdistrict=false;
        var newSuperDistrict=[];
        listOfSuperDistricts.push(newSuperDistrict);
        currentSuperDistrictIndex = listOfSuperDistricts.length - 1;
        startingNewSuperDistrict=false;
    }
    var currentSuperDistrict=listOfSuperDistricts[currentSuperDistrictIndex];
    currentSuperDistrict.push(eventFeature);
    console.log("number of features in current super-district after adding new feature:"+currentSuperDistrict.length);
    currentSuperDistrict.forEach(feature => {
        map.data.overrideStyle(feature, {fillColor: 'gold', strokeColor: 'red'});
    });
    if(!undo){
        clickHistory.push({type: 'single', feature: eventFeature});
    }
    $('#resetButton').removeAttr('disabled');
}

function undoListener(map){
    document.getElementById("undoButton").addEventListener('click', function(event) {       
        undoHandler(map);
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
        if(history.type === 'single'){
            console.log("type:single");
            superDistrictHandler(map, history.feature, true);
        }else if(history.type === 'multi'){
            console.log("type:multi");
            history.feature.forEach(feature => {
                superDistrictHandler(map, feature, true);
            });
        }
        else if(history.type === 'super'){
            console.log("type:super");
            doneBuildingSuperdistrict = true;
            showDistrictHandler(map, history.feature, true);
        }
        else if(history.type === 'show'){
            console.log("type:show");
            createSuperDistrictHandler(map, true);
        }
    }
}

function removeDistrictFeature(map, eventFeature, undo){
    var location = locateSelectedDistrict(eventFeature);
    //remove clicked feature so the neighbors of the feature are left in the superdistrict array
    //when only its neighbors are left, we can then compare each neighbor with the other neighbors to see if there are any cases
      // where the neighbors dont share a boundary since the clicked feature was removed.
    //if there is an instance where the neighbors don't share a boundary as a result of the clicked feature's removal, we throw an alert
      // and add the removed feature back into the superdistrict array where it once was positioned 
    //Otherwise, we continue removing the feature and coloring it grey and black.
    //This removal check will only be implented on map clicks, when "undo" is false. When clicking the undo button, the method
       // will skip the boundary check portion and simply pop the last element from the history array accordingly("undo" is true)
    listOfSuperDistricts[location.superdistrictIndex].splice(location.districtIndex, 1);
    var superDistrictLength = listOfSuperDistricts[location.superdistrictIndex].length;
    if(superDistrictLength==1){
        map.data.overrideStyle(eventFeature, {fillColor: 'grey', strokeColor: 'black'});
        console.log("Feature removed");
        if(!undo)
            clickHistory.push({type: 'single', feature: eventFeature});
    }
    else{
      var stillContiguous=false;
        if(!undo){
            var selectedFeatureSuperDistrict = listOfSuperDistricts[location.superdistrictIndex];
            for(var j=0;j<selectedFeatureSuperDistrict.length;j++){        
                var tempFeature=selectedFeatureSuperDistrict[j];
                var geom = tempFeature.getGeometry();
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
                for(var k=0;k<selectedFeatureSuperDistrict.length;k++){            
                    var tempFeature2=selectedFeatureSuperDistrict[k];
                    if(tempFeature.getProperty('DistrictNo')==tempFeature2.getProperty('DistrictNo')){
                        continue;
                    }else{
                        var geom2 = tempFeature2.getGeometry();            
                        geom2.forEachLatLng(function(LatLng){
                            polygons.forEach(poly => {
                                if(google.maps.geometry.poly.containsLocation(LatLng, poly)){
                                    stillContiguous=true;
                                }
                            });
                        });  
                    }
                                       
                }
                if(!stillContiguous){
                    alert("Error: Superdistricts must still be contiguous after removing a district.");
                    listOfSuperDistricts[location.superdistrictIndex].splice(location.districtIndex, 0,eventFeature);
                    return;
                }
                stillContiguous=false;      
            }
        
        }    
        if(listOfSuperDistricts[location.superdistrictIndex].length == 0){
            listOfSuperDistricts.splice(location.superdistrictIndex, 1);
            startingNewSuperDistrict = true;
            $('#resetButton').attr('disabled', 'disabled');
        }
        map.data.overrideStyle(eventFeature, {fillColor: 'grey', strokeColor: 'black'});
        console.log("Feature removed");
        console.log("startingNewSuperDistrict value: " + startingNewSuperDistrict);
        console.log("listOfSuperDistricts length: " + listOfSuperDistricts.length);
        if(!undo)
            clickHistory.push({type: 'single', feature: eventFeature});
    }
    
}

function removeSuperDistrictFeature(map, eventFeature, undo){
    var location = locateSuperDistrict(eventFeature);
    if(listOfSuperDistricts[location.superdistrictIndex].length == 0){
        
        startingNewSuperDistrict = true;
    }
    listOfSuperDistricts.splice(location.superdistrictIndex, 1);
    map.data.overrideStyle(eventFeature, {fillColor: 'grey', strokeColor: 'black'});
    console.log("Feature removed");
    console.log("startingNewSuperDistrict value: " + startingNewSuperDistrict);
    console.log("listOfSuperDistricts length: " + listOfSuperDistricts.length);
    if(!undo)
        clickHistory.push({type: 'super', feature: eventFeature});
}

function createSuperDistrictListener(map){
    document.getElementById("createButton").addEventListener('click', function(){
        createSuperDistrictHandler(map, false);
    });
    map.data.addListener('click',function(event){
        if(event.feature.getProperty('isSuperDistrict')){
            showDistrictHandler(map, event.feature, false);
        }
    });
}

function createSuperDistrictHandler(map, undo){
    console.log("Create super district fired!")
    console.log("createSuperDistrictHandler-listOfSuperDistricts[currentSuperDistrictIndex].length:"+listOfSuperDistricts[currentSuperDistrictIndex].length);
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
        labelSuperDistrict(combined, currentSuperdistrict);
        validateSuperdistrict(combined);

        features = map.data.addGeoJson(combined);
        features[0].setProperty("isSuperDistrict", true);
        if(!undo)
            previousColor = getRandomColor();
        map.data.overrideStyle(features[0],
            {fillColor: previousColor, strokeColor: 'black', zIndex: setting.superDistrictZoom, fillOpacity: 1.0});
        features[0].setProperty('fillColor', previousColor);
        startingNewSuperDistrict = true;
        if(!undo)
            clickHistory.push({type: 'super', feature: features[0]});  
        doneBuildingSuperdistrict=true; //////////////////////////////////////////////////////////////////////////////////////////     
    });
    $('#resetButton').attr('disabled', 'disabled');
}

function validateSuperdistrict(superdistrict){
    $.ajax({
        url: "/superdistrict/validate?state=" + selectedState.name + "&year=" + selectedYear,
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(superdistrict),
        dataType: "json",
        success: function(result){
            result.response.forEach(testResult => {
                var type = testResult.testPerformed;
                var result = testResult.testResult;
                $('#' + type).attr("class", result ? "text-success" : "text-danger");
            });
        },
        error: function(err){
            console.log(err);
        }
    });
}

function showDistrictHandler(map, feature, undo){
    console.log("In showDistrictHandler function");
    if(doneBuildingSuperdistrict==false)// in the process of building a super district////////////////////////////////////////////////
        return;
    var location = locateSuperDistrict(feature);
    if(location.found == true)
        currentSuperDistrictIndex = location.superdistrictIndex;
    console.log("currentSuperDistrictIndex: " + currentSuperDistrictIndex);
    previousColor = feature.getProperty('fillColor');
    map.data.remove(feature);
    map.data.forEach(f => {
        if(f.getProperty('isSuperDistrict') &&
            f.getProperty('Districts').toString() === feature.getProperty('Districts').toString()){
            map.data.remove(f);
        }
    });
    startingNewSuperDistrict = false;
    doneBuildingSuperdistrict = false;
    if(!undo)
        clickHistory.push({type: 'show', feature: feature});
    $('#resetButton').removeAttr('disabled');
}

function labelSuperDistrict(geojson, districts){
    var districtNos = districts.map(d => {return d.getProperty('DistrictNo');});
    geojson.properties.Districts = districtNos;
}

function isInListOfSuperDistricts(districtNo){
    for(var j=0;j<listOfSuperDistricts.length;j++){
        var tempSuperDistrict=listOfSuperDistricts[j];
        for(var k=0; k<tempSuperDistrict.length;k++){
            if(tempSuperDistrict[i].getProperty('DistrictNo') == districtNo)
                return true;
        }
    }
    return false;
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

    map.data.forEach(feature => {
        if(feature.getProperty('isSuperDistrict'))
            map.data.remove(feature);
    });

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

function initRandomGeneratedSuperDistrict(){
    //randomly picks 5 features that are added to 1 super district and displays that super district
    // features must come together to make a contiguous super district
    if(selectedStateForRedistricting==null){
        alert("Please select a state before randomly generating a super-district.");
        return;
    }else{
        selectedStateForRedistricting.features.forEach(feature => {
            map.data.overrideStyle(feature,{fillColor:'grey',strokeColor:'black',clickable:'true'}); 
        });
        var randomFeature = pickRandomFeature();
        createAndColorSuperdistrict(randomFeature);
    }    
}
function pickRandomFeature(){
    var maxDistrNo=0;
    
    selectedStateForRedistricting.features.forEach(feature => {
        maxDistrNo=Math.max(maxDistrNo,feature.getProperty('DistrictNo'));
    });
    console.log("maxDistrNo:"+maxDistrNo);
    var randomFeatureDistrictNo = Math.floor(Math.random() * maxDistrNo);
    console.log("randomFeatureDistrictNo:"+randomFeatureDistrictNo);
    var randomlyChosenFeatureArray;
    if(randomFeatureDistrictNo==0){
        randomlyChosenFeatureArray = selectedStateForRedistricting.features.filter(function(feature){
            return 1==feature.getProperty('DistrictNo');
        });
    }else{
        randomlyChosenFeatureArray = selectedStateForRedistricting.features.filter(function(feature){
            return randomFeatureDistrictNo==feature.getProperty('DistrictNo');
        });
    }
    
    return randomlyChosenFeatureArray[0];
}

function createAndColorSuperdistrict(randomFeature){    
    var superDistrict=[];
    superDistrict.push(randomFeature);
    var returnedSuperDistrict=getExtraFeatures(superDistrict);
    /*
    startingNewSuperDistrict=true;
    for(var j =0;j<returnedSuperDistrict.length;j++){
        addDistrictFeature(map,returnedSuperDistrict[j],false);
    }    
    createSuperDistrictHandler(map,false);
    */
    colorRandomSuperDistrict(returnedSuperDistrict); 
}
function getExtraFeatures(superDistrict){
    console.log("getExtraFeatures-superDistrict.length:"+superDistrict.length);
    var districtInList=false;
    var extraFeatureArray=[];
    var availableDistricts=[];
    selectedStateForRedistricting.features.forEach(districtFeature => {
        if(districtFeature.getProperty('DistrictNo')!=superDistrict[0].getProperty('DistrictNo')){
            availableDistricts.push(districtFeature);
        }
    });
    var matchedValue=0;
    var foundNeighbor=false;
    while(matchedValue!=4){
        for(var m=0;m<availableDistricts.length;m++){
            var availableFeature=availableDistricts[m];
            var selectedDistrictGeom = availableFeature.getGeometry();
            superDistrict.forEach(districtFeature => {
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
                        if(google.maps.geometry.poly.containsLocation(LatLng, poly)){
                            foundNeighbor=true;
                        }
                    });
                });            
            });
            if(foundNeighbor){
                superDistrict.push(availableFeature);
                matchedValue++;
                foundNeighbor=false;
            }        
            if(matchedValue==4){
                break;
            }        
        }
    }
    return superDistrict; 
}
function colorRandomSuperDistrict(returnedSuperDistrict){
    returnedSuperDistrict.forEach(feature => {
        map.data.overrideStyle(feature, {fillColor: 'green', strokeColor: 'black'});
    });
    
}
/*
function shuffle(array){
  var lastIndex = array.length;
  var tempFeature;
  var randomIndex;
  while (lastIndex != 0) {
    randomIndex = Math.floor(Math.random() * lastIndex);
    lastIndex = lastIndex-1;
    tempFeature = array[lastIndex];
    array[lastIndex] = array[randomIndex];
    array[randomIndex] = tempFeature;
  }
  return array;
}
*/

