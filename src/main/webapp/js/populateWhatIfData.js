var selectedStateID=0;
var winnerArray=[];
function selectStateByDropDown(element){
  selectedState = element.options[element.selectedIndex].text;
}
function setSelectedStateID(){
  map.data.forEach(function(feature){
    if(feature.getProperty('Name')==selectedState){
      if(feature.getProperty('CD115FP')==null){
        selectedStateID=feature.getProperty('STATEFP');
      }
    }
  });
  console.log("-setSelectedStateID-selectedStateID "+selectedStateID);
  generateDistricts(selectedStateID);
}
function selectYearByDropDown(element){
  d3.json("/resources/js/test.json", function(filteredData) {
      selectedYear = element.options[element.selectedIndex].text;
        selectedPair=[
            ["State",selectedState],
            ["raceYear",selectedYear]
        ];
      /* displayStateWithDescription(selectedState); */ //not needed for the what if page - move zoom on state to another function    
       //get specific rows from data that pertain to the user's selection
       filteredData = filteredData.filter(function(row) {
            return row['State'] == selectedPair[0][1] && row['raceYear']==selectedPair[1][1];
        });
       setSelectedStateID();
       displayVoteSums(filteredData);
       //idByStateClickListener();
       zoomOnSelectedState(selectedState);
       selectDistrictByClickListener(map,filteredData);//selectDistrictByClickListener(map,filteredData,areaInfoWindow);
       selectStateByMarker1Click(filteredData);
       selectStateByMarker2Click(filteredData);
       selectStateByMarker3Click(filteredData);
       createSuperDistrictListener(map);
       addToSuperDistrictListener(map);
       undoListener(map);       
       saveSuperDistrictListener(map);
       cancelSuperDistrictListener(map);
       resetSuperDistrictListener(map); 
       //disableStatesAtLarge();
        /*
         * bring these methods back when ready to load the test results on the superdistricts

       // remove old results and add the new graph canvas to the body of the webpage
       var svg1_Removal = d3.select("#visual"); //lopsided wins chart 
       svg1_Removal.selectAll("*").remove();       
       var svg1 = d3.select("#visual").append("svg")
                 .attr("width", width + margin.left + margin.right)
                 .attr("height", height + margin.top + margin.bottom)
                 .append("g")
                 .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
        var svg_Removal = d3.select("#vis");//consistent advantage chart
        svg_Removal.selectAll("*").remove();                
        var svg = d3.select("#vis").append("svg")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom)
                    .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
        var svg2_Removal = d3.select("#visEfficiencyGap");
        svg2_Removal.selectAll("*").remove();
        var svg2 = d3.select("#visEfficiencyGap").append("svg")
                .attr("width", width3 + margin2.left2 + margin2.right2)
                .attr("height", height3 + margin2.bottom2)
                .append("g")
                .attr("transform", "translate(" + margin2.left2 + "," + margin2.top2 + ")");
       
        displayLopsidedTestResults(filteredData,svg1);
        displayConsistentAdvantageTestResults(filteredData,svg);
        displayEfficiencyGapTestResults(filteredData,svg2);
        */              
    });
}
function zoomOnSelectedState(selectedState){
  console.log("selectedState for zoom:"+selectedState);
  map.data.forEach(function (feature) {
    if(feature.getProperty('Name')==selectedState){
      console.log("successful match");
      var geom=feature.getGeometry();
      var latLngArray=[];
      geom.forEachLatLng(function(LatLng){
        latLngArray.push(LatLng);
      });
      console.log("latLngArray length:"+latLngArray.length);
      var element = parseInt(latLngArray.length/2) ;
      var centerLatLng=latLngArray[element];
      map.setCenter(centerLatLng);
      map.setZoom(6);
    }         
  });
}

function generateDistricts(stateID){
  if(stateID==36){
    map.data.forEach(function (feature) {
      map.data.remove(feature);
    });
    //load geojson from data.js file to recreate the new map with New York's districts         
    map.data.addGeoJson(data);   
    map.data.addGeoJson(tempObject26);    
    map.data.addGeoJson(tempObject27);    
    map.data.addGeoJson(tempObject28);    
    map.data.addGeoJson(tempObject29);    
    map.data.addGeoJson(tempObject30);    
    map.data.addGeoJson(tempObject31);    
    map.data.addGeoJson(tempObject32);    
    map.data.addGeoJson(tempObject33);    
    map.data.addGeoJson(tempObject34);    
    map.data.addGeoJson(tempObject35);    
    map.data.addGeoJson(tempObject36); 
    map.data.addGeoJson(tempObject37);    
    map.data.addGeoJson(tempObject38);
    map.data.addGeoJson(tempObject39);
    map.data.addGeoJson(tempObject40);
    map.data.addGeoJson(tempObject41);
    map.data.addGeoJson(tempObject42);
    map.data.addGeoJson(tempObject43);
    map.data.addGeoJson(tempObject44); 
    map.data.addGeoJson(tempObject45);  
    map.data.addGeoJson(tempObject46);
    map.data.addGeoJson(tempObject47);
    map.data.addGeoJson(tempObject48);
    map.data.addGeoJson(tempObject49);
    map.data.addGeoJson(tempObject50);
    map.data.addGeoJson(tempObject51);
  }else if(stateID==37){
       map.data.forEach(function (feature) {
      map.data.remove(feature);
    });
    //load geojson from data.js file to recreate the new map with New York's districts         
    map.data.addGeoJson(data);
  map.data.addGeoJson(tempObject2);
  map.data.addGeoJson(tempObject3);
  map.data.addGeoJson(tempObject4);
  map.data.addGeoJson(tempObject5);
  map.data.addGeoJson(tempObject6);
    map.data.addGeoJson(tempObject7);
    map.data.addGeoJson(tempObject8);
    map.data.addGeoJson(tempObject9);
    map.data.addGeoJson(tempObject10);
    map.data.addGeoJson(tempObject11);
    map.data.addGeoJson(tempObject12);
    map.data.addGeoJson(tempObject13);
    map.data.addGeoJson(tempObject14);
  }else if(stateID==38){
    map.data.forEach(function (feature) {
      map.data.remove(feature);
    });
    //load geojson from data.js file to recreate the new map with New York's districts         
    map.data.addGeoJson(data);
    map.data.addGeoJson(tempObject52);
  }else if(stateId==51){
        map.data.forEach(function (feature) {
      map.data.remove(feature);
    });
    //load geojson from data.js file to recreate the new map with New York's districts         
    map.data.addGeoJson(data);      
  map.data.addGeoJson(tempObject15);    
  map.data.addGeoJson(tempObject16);    
  map.data.addGeoJson(tempObject17);    
  map.data.addGeoJson(tempObject18);    
  map.data.addGeoJson(tempObject19);    
  map.data.addGeoJson(tempObject20);    
  map.data.addGeoJson(tempObject21);    
  map.data.addGeoJson(tempObject22);    
  map.data.addGeoJson(tempObject23);    
  map.data.addGeoJson(tempObject24);    
  map.data.addGeoJson(tempObject25);
  }
  disableStatesAtLarge();
}
function disableStatesAtLarge(){
  var stateFeatureArray=[];
  var disabledStatesArray=[];
  map.data.forEach(function (feature) {
    if(feature.getProperty('Name')==selectedState){
      if(feature.getProperty('CD115FP')==null){
        stateFeatureArray.push(feature);
      }
    }         
  }); 
  
  for(var i=0;i<stateFeatureArray.length;i++){
    var districtCounter=0;
    var stateFeatureID = stateFeatureArray[i].getProperty('STATEFP');
    map.data.forEach(function(feature){
      if(feature.getProperty('STATEFP')==stateFeatureID){
        if(feature.getProperty('CD115FP')!=null){
          districtCounter++;
        }
      }
    });
    if(districtCounter<5){ // the clicked state is a state at large
      map.data.overrideStyle(stateFeatureArray[i],{clickable: false,fillColor:'grey',strokeColor:'black'});
      document.getElementById("createButton").className = "btn btn-primary disabled";
      disabledStatesArray.push(stateFeatureArray[i]);
      alert("The state is elected at large, so no superdistricts will be built.");
    }else{
      document.getElementById("createButton").className = "btn btn-primary";
    }
  }  
}
//minority data
function displayMinorityData(element){
  d3.json("/resources/js/northCarolinaPopulation.json",function(json){   
    console.log("-In displayMinorityData-> selectedStateID:"+selectedStateID);
    if(selectedStateID==0){
      alert("Please click on a state before selecting the minority density map view");
      return;
    }
    selectedMinority = element.options[element.selectedIndex].text;
    displayMinorityPercentage(json,selectedMinority,selectedStateID);
  });
}
function displayMinorityPercentage(json,selectedMinority,selectedStateID){
  var minorityDensity;
  var densityArray=[];
  map.data.forEach(function(feature){
    if(feature.getProperty('STATEFP')==selectedStateID){
      if(feature.getProperty('CD115FP')!=null){
        minorityDensity=calculateMinorityPopulationPercentage(json,selectedMinority,feature);
        densityArray.push(minorityDensity);
      }
    }
  });
  var densityArraySumPercentage=densityArray.reduce(function(total,amount){
    return total+amount;
  },0);
  document.getElementById("minorityPercentage").innerHTML=densityArraySumPercentage+"%";
}
function calculateMinorityPopulationPercentage(json,selectedMinority,feature){
  var districtID=feature.getProperty('CD115FP');
  console.log("districtID: "+districtID);
  var selectedMinorityData = json.filter(function(row){
    return row['Type']==selectedMinority;
  });
  var totalPopulationData=json.filter(function(row){
    return row['Type']=='Total population';
  });
  var districtIDNum=parseInt(districtID,10);
  var tensPlace=Math.floor(districtIDNum/10%10);
  var minorityDataArray;
  var totalPopulationDataArray;
  if(tensPlace==0){//districtId is equal to 01 -> 09
    districtID=parseInt(districtID.replace("0",""));
  }
  minorityDataArray = selectedMinorityData.map((e)=>{
    return e[districtID];
  });
  totalPopulationDataArray = totalPopulationData.map((e)=>{
    return e[districtID];
  });
  var districtPopulationTotal = totalPopulationDataArray[0];
  var selectedMinorityDistrictAmount=minorityDataArray[0];      
  return selectedMinorityDistrictAmount/districtPopulationTotal;// returns density   
}
