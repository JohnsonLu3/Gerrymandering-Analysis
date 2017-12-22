function displayVoteSums(data){
	//returns an array of the "DemVotes " values
            var demVotesArray = data.map((e) => {
                return typeof e.DemVotes === 'string' ? parseInt(e.DemVotes.replace(",", "")) : e.DemVotes;
            });
            //calculates the sum of all the elements in the array 
            var demVoteSum = demVotesArray.reduce(function(total,amount){
                return total + amount;
             }, 0);
            //repeat finding the sum for the republicans
            var repVotesArray = data.map((e) => {
                return typeof e.RepVotes === 'string' ? parseInt(e.RepVotes.replace(",", "")) : e.RepVotes;
            });
            var repVoteSum = repVotesArray.reduce(function(total,amount){
                return total + amount;
            }, 0);
            // add total votes for each party to the description for the state
            //document.getElementById("totalVotes").innerHTML = "Democrat Votes: "+demVoteSum+"  |   Republican Votes: "+repVoteSum;
            document.getElementById("votes").innerHTML="Democratic Votes: "+demVoteSum+"  |   Republican Votes: "+repVoteSum;
            document.getElementById("representatives").innerHTML=demVotesArray.length;
    }