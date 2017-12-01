var selectedStateID=0;
var winnerArray=[];
function selectStateByDropDown(element){
  selectedState = element.options[element.selectedIndex].text;
}

function selectYearByDropDown(element){
  d3.json("/resources/js/test.json", function(filteredData) {
      selectedYear = element.options[element.selectedIndex].text;
        selectedPair=[
            ["State",selectedState],
            ["raceYear",selectedYear]
        ];
       displayStateWithDescription(selectedState);       
       //get specific rows from data that pertain to the user's selection
       filteredData = filteredData.filter(function(row) {
            return row['State'] == selectedPair[0][1] && row['raceYear']==selectedPair[1][1];
        });
        winnerArray=filteredData.map((e)=>{
          return e['Winner'];
        });
       displayVoteSums(filteredData);
       selectDistrictByClickListener(map,filteredData,areaInfoWindow)
       selectStateByMarker1Click(filteredData);
       selectStateByMarker2Click(filteredData);
       selectStateByMarker3Click(filteredData); 
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
        idByStateClickListener();           
    });
}
function idByStateClickListener(){
  map.data.addListener('click',function(event){
    if(event.feature.getProperty('CD115FP')==null){
      idByStateClickHandler(map,event);
    }
  });
}
function idByStateClickHandler(map,event){
  map.setCenter(event.latLng);
  selectedStateID = event.feature.getProperty('STATEFP');
  console.log("ksdbciwlervliarybclvyblkfjhil");
  generateDistricts(selectedStateID);
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
}

function initPopulationDensityMap(){
 
  d3.json("/resources/js/northCarolinaPopulation.json",function(json){   //$.getJSON("/resources/js/northCarolinaPopulation.json",function(json){
    var densityArray=[];
    console.log("-In selectMinorityByDropDown-> selectedStateID:"+selectedStateID);
    if(selectedStateID==0){
      alert("Please click on a state before selecting the minority density map view");
      return;
    }
    colorByPopulationDistribution(json,densityArray,selectedStateID);
  });
}
function selectMinorityByDropDown(element){

  d3.json("/resources/js/northCarolinaPopulation.json",function(json){   
    var densityArray=[];
    console.log("-In selectMinorityByDropDown-> selectedStateID:"+selectedStateID);
    if(selectedStateID==0){
      alert("Please click on a state before selecting the minority density map view");
      return;
    }
    selectedMinority = element.options[element.selectedIndex].text;
    colorByMinorityNormalDistribution(json,densityArray,selectedMinority,selectedStateID);
  });
}
function colorByPopulationDistribution(json,densityArray,selectedStateID){
  var populatedDensityArray=populatePopulationDensityArray(json,densityArray); 
  //normalized values would be density /sum of all densities
  var normalizedDensityArray=normalizeDensityArray(populatedDensityArray);
  //color districts
  var districtID;
  map.data.forEach(function(feature){
    if( feature.getProperty('STATEFP')==selectedStateID){
      if(feature.getProperty('CD115FP')!=null){
        districtID=feature.getProperty('CD115FP');
        var districtIDNum=parseInt(districtID,10);
        var tensPlace=Math.floor(districtIDNum/10%10);
        if(tensPlace==0){//districtId is equal to 01 -> 09
          districtID=parseInt(districtID.replace("0",""));
        }
        console.log("normalizedDensityArray[districtID-1]*255:"+normalizedDensityArray[districtID-1]*255);
        var colorProduct = Math.round(normalizedDensityArray[districtID-1]*255);
        var densityColor= generateDensityColor(winnerArray[districtID-1],colorProduct);
        console.log("densityColor:"+densityColor);
        map.data.overrideStyle(feature,{fillColor:densityColor,strokeColor:'black',fillOpacity:1.0});
      }
    }
  });
}
function colorByMinorityNormalDistribution(json,densityArray,selectedMinority,selectedStateID){
  var populatedDensityArray=populateDensityArray(json,densityArray,selectedMinority); 
  //normalized values would be density /sum of all densities
  var normalizedDensityArray=normalizeDensityArray(populatedDensityArray);
  //color districts
  var districtID;
  map.data.forEach(function(feature){
    if( feature.getProperty('STATEFP')==selectedStateID){
      if(feature.getProperty('CD115FP')!=null){
        districtID=feature.getProperty('CD115FP');
        var districtIDNum=parseInt(districtID,10);
        var tensPlace=Math.floor(districtIDNum/10%10);
        if(tensPlace==0){//districtId is equal to 01 -> 09
          districtID=parseInt(districtID.replace("0",""));
        }
        console.log("normalizedDensityArray[districtID-1]*255:"+normalizedDensityArray[districtID-1]*255);
        var colorProduct = Math.round(normalizedDensityArray[districtID-1]*255);
        var densityColor= generateDensityColor(winnerArray[districtID-1],colorProduct);
        console.log("densityColor:"+densityColor);
        map.data.overrideStyle(feature,{fillColor:densityColor,strokeColor:'black',fillOpacity:1.0});
      }
    }
  });
}
function populatePopulationDensityArray(json,densityArray){
  var minorityPopulation;
  var totalPopulationDataArray=[];
  map.data.forEach(function(feature){
    if(feature.getProperty('STATEFP')==selectedStateID){
      if(feature.getProperty('CD115FP')!=null){
        minorityPopulation=getDistrictPopulationData(json,feature);
        totalPopulationDataArray.push(minorityPopulation);
      }
    }
  });
  var totalPopulationValue = totalPopulationDataArray.reduce(function(total,amount){
    return total+amount;
  },0);
  for(var i =0; i<totalPopulationDataArray.length;i++){
   densityArray.push(totalPopulationDataArray[i]/totalPopulationValue);
  }
  return densityArray;
}
function populateDensityArray(json,densityArray,selectedMinority){
  var minorityDensity;
  map.data.forEach(function(feature){
    if(feature.getProperty('STATEFP')==selectedStateID){
      if(feature.getProperty('CD115FP')!=null){
        minorityDensity=calculateMinorityPopulationPercentage(json,selectedMinority,feature);
        densityArray.push(minorityDensity);
      }
    }
  });
  return densityArray;
}

function normalizeDensityArray(populatedDensityArray){
  var normalizedArray=[];
  var totalMinorityDensity =populatedDensityArray.reduce(function(total,amount){
    return total + amount;
  }, 0);
  var tempDensity;
  for(var i=0;i<populatedDensityArray.length;i++){
    tempDensity=populatedDensityArray[i];
    tempDensity=tempDensity/totalMinorityDensity;
    normalizedArray.push(tempDensity);
  }
  return normalizedArray;
}
function getDistrictPopulationData(json,feature){
  var districtID=feature.getProperty('CD115FP');
  console.log("districtID: "+districtID);
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
  totalPopulationDataArray = totalPopulationData.map((e)=>{
    return e[districtID];
  });
  return totalPopulationDataArray[0];
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

function generateDensityColor(winner,colorProduct){
  var densityColor;
  var baseColor;
  var x=0;
  var y=0;
  var z=0;
  console.log("winner:"+winner);
  if(winner=='R'){
    baseColor='red';
    
    x = 255 ;//- colorProduct;
    y= 0 + (colorProduct*3);
    z= 0 + (colorProduct*3);
    
  }else if(winner=='D'){
    baseColor='blue';
    
    x = 0+(colorProduct*3);
    y=0+(colorProduct*3);
    z=255;//-colorProduct;
    
  }
  
  densityColor=rgbToHexidecimal(x,y,z);
  return densityColor;
}
function hexConversion(c){
  var hex = c.toString(16);
  console.log("c:"+c);
  console.log("hex:"+hex);
  return hex.length==1?"0"+hex:hex;
}
function rgbToHexidecimal(r,g,b){
  return "#"+hexConversion(r)+hexConversion(g)+hexConversion(b);
}

