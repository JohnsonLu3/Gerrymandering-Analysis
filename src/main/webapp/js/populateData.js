function initPopulationDensityMap(){
    var densityArray=[];
    if(selectedState == null){
      alert("Please click on a state before selecting the minority density map view");
      return;
    }
    colorByPopulationDistribution(densityArray);
}

function selectMinorityByDropDown(element){
    var densityArray=[];
    if(selectedState == null){
      alert("Please click on a state before selecting the minority density map view");
      return;
    }
    selectedMinority = element.options[element.selectedIndex].text;
    colorByMinorityNormalDistribution(densityArray,selectedMinority);
}

function colorByPopulationDistribution(densityArray){
  var populatedDensityArray=populatePopulationDensityArray(densityArray);
  //normalized values would be density /sum of all densities
  var normalizedDensityArray=normalizeDensityArray(populatedDensityArray);
  //color districts
  var districtID;
  selectedState.features.forEach(feature => {
    districtID=feature.getProperty('DistrictNo');
    console.log("normalizedDensityArray[districtID-1]*255:"+normalizedDensityArray[districtID-1]*255);
    var colorProduct = Math.round(normalizedDensityArray[districtID-1]*255);
    var winner = feature.getProperty('ElectedParty');
    var densityColor= generateDensityColor(winner,colorProduct);
    console.log("densityColor:"+densityColor);
    map.data.overrideStyle(feature,{fillColor:densityColor,strokeColor:'black',fillOpacity:1.0});
  });
}

function colorByMinorityNormalDistribution(densityArray,selectedMinority){
  var populatedDensityArray=populateDensityArray(densityArray,selectedMinority);
  //normalized values would be density /sum of all densities
  var normalizedDensityArray=normalizeDensityArray(populatedDensityArray);
  //color districts
  var districtID;
  selectedState.features.forEach(feature => {
     districtID=feature.getProperty('DistrictNo');
     console.log("normalizedDensityArray[districtID-1]*255:"+normalizedDensityArray[districtID-1]*255);
     var colorProduct = Math.round(normalizedDensityArray[districtID-1]*255);
     var winner = feature.getProperty('ElectedParty');
     var densityColor= generateDensityColor(winner,colorProduct);
     console.log("densityColor:"+densityColor);
     map.data.overrideStyle(feature,{fillColor:densityColor,strokeColor:'black',fillOpacity:1.0});
  });
}

function populatePopulationDensityArray(densityArray){
  var totalPopulationDataArray=[];
  selectedState.features.forEach(feature => {
    totalPopulation = getDistrictPopulationData(feature);
    totalPopulationDataArray.push(totalPopulation);
  });

  var totalPopulationValue = totalPopulationDataArray.reduce(function(total,amount){
    return total+amount;
  },0);
  for(var i =0; i<totalPopulationDataArray.length;i++){
   densityArray.push(totalPopulationDataArray[i]/totalPopulationValue);
  }
  return densityArray;
}

function populateDensityArray(densityArray,selectedMinority) {
    var minorityDensity;
    selectedState.features.forEach(feature => {
        minorityDensity = calculateMinorityPopulationPercentage(selectedMinority, feature);
        densityArray.push(minorityDensity);
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
function getDistrictPopulationData(feature){
  var totalPopulationData= feature.getProperty("TotalPopulation");
  return totalPopulationData;
}

function calculateMinorityPopulationPercentage(selectedMinority,feature){
    return feature.getProperty("PercentPopulation")[selectedMinority];
}

function generateDensityColor(winner,colorProduct){
  var densityColor;
  var baseColor;
  var x=0;
  var y=0;
  var z=0;
  console.log("winner:"+winner);
  if(winner=='Republican'){
    baseColor='red';
    
    x = 255 //- colorProduct;
    y= 255 - colorProduct * 10//+ (colorProduct*20);
    z= 255 - colorProduct * 10//+ (colorProduct*20);
    
  }else if(winner=='Democrat'){
    baseColor='blue';
    
    x = 255 - colorProduct * 10 //+(colorProduct*20);
    y= 255 - colorProduct * 10//+(colorProduct*20);
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

